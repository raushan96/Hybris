package de.andre.service.commerce.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.enums.PaymentState;
import de.andre.entity.order.*;
import de.andre.entity.profile.Profile;
import de.andre.multisite.SiteManager;
import de.andre.repository.order.OrderRepository;
import de.andre.service.account.ProfileTools;
import de.andre.service.price.PriceTools;
import de.andre.utils.idgen.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderTools {
    private static final Logger logger = LoggerFactory.getLogger(OrderTools.class);

    private final OrderRepository orderRepository;
    private final ProfileTools profileTools;
    private final PriceTools priceTools;
    private final ShippingGroupsTools shippingGroupsTools;
    private final IdGenerator idGenerator;

    public OrderTools(final OrderRepository orderRepository, final ProfileTools profileTools, final PriceTools priceTools,
            final ShippingGroupsTools shippingGroupsTools, final IdGenerator idGenerator) {
        this.orderRepository = orderRepository;
        this.profileTools = profileTools;
        this.priceTools = priceTools;
        this.shippingGroupsTools = shippingGroupsTools;
        this.idGenerator = idGenerator;
    }

    @Transactional
    public Order createOrder(final Long profileId) {
        return createOrder(profileTools.profileById(profileId));
    }

    @Transactional
    public Order createOrder(final Profile profile) {
        final Order order = getOrderInstance();
        order.setNumber(idGenerator.generateOrderStringNumber());
        order.setProfile(profile);

        addDefaultShippingGroup(order);
        addDefaultPaymentGroup(order);
        addOrderPriceInfo(order);

        if (logger.isDebugEnabled()) {
            logger.debug("Created order instance {}", order);
        }

        persistOrder(order);
        return order;
    }

    protected void addDefaultShippingGroup(final Order pOrder) {
        Assert.notNull(pOrder);
        final HardgoodShippingGroup shipGroup = shippingGroupsTools.createHardgoodShippingGroup();
        shipGroup.setOrder(pOrder);
        pOrder.addHgShippingGroup(shipGroup);
    }

    protected void addDefaultPaymentGroup(final Order pOrder) {
        Assert.notNull(pOrder);
        final PaymentGroup payGroup = initDefaultPaymentGroup();
        payGroup.setState(PaymentState.INITIAL);
        payGroup.setCurrency(priceTools.getCurrency(null));
        payGroup.setOrder(pOrder);
        pOrder.addPaymentGroup(payGroup);
    }

    protected void addOrderPriceInfo(final Order pOrder) {
        Assert.notNull(pOrder);
        final OrderPriceInfo priceInfo = new OrderPriceInfo();
        priceInfo.setOrder(pOrder);
        pOrder.setPriceInfo(priceInfo);
        priceInfo.fillAmounts(BigDecimal.ZERO);
        priceInfo.setTax(BigDecimal.ZERO);
        priceInfo.setShipping(BigDecimal.ZERO);
    }

    private Order getOrderInstance() {
        final Order order = initOrder();

        final LocalDateTime ts = LocalDateTime.now();
        order.setCreationDate(ts);
        order.setLastModifiedDate(ts);

        order.setState(OrderState.INCOMPLETE);
        order.setSiteId(SiteManager.getSiteId());

        final HttpServletRequest req =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        order.setServerInfo(req.getServerName() + ":" + req.getServerPort());

        return order;
    }

    @Transactional(readOnly = true)
    public Order loadOrder(final Long orderId) {
        if (orderId == null) {
            return null;
        }
        logger.debug("Fetching order with {} id", orderId);
        return orderRepository.fetchOrder(orderId);
    }

    @Transactional(readOnly = true)
    public List<Order> getProfileCurrentOrders(final Long profileId) {
        // todo : by criteria?
        return orderRepository.currentOrders(profileId, SiteManager.getSiteId());
    }

    public void mergeOrders(final Order srcOrder, final Order destOrder) {
        if (!ordersMergeable(srcOrder, destOrder)) {
            logger.debug("Not merging orders");
            return;
        }

        final List<CommerceItem> cisDest = destOrder.getCommerceItems();
        for (final CommerceItem ci : srcOrder.getCommerceItems()) {
            final CommerceItem existingItem = cisDest.stream()
                    .filter(item -> item.equals(ci))
                    .findFirst()
                    .orElse(null);
            if (existingItem == null) {
                cisDest.add(ci);
            }
            else {
                final Long quantity = existingItem.getQuantity() + ci.getQuantity();
                existingItem.setQuantity(quantity);
            }
        }
    }

    private boolean ordersMergeable(final Order srcOrder, final Order destOrder) {
        if (srcOrder == null || destOrder == null || srcOrder.getId().equals(destOrder.getId())) {
            return false;
        }

        if (OrderState.INCOMPLETE != srcOrder.getState() || OrderState.INCOMPLETE != destOrder.getState()) {
            return false;
        }

        final String siteId = SiteManager.getSiteId();
        if (!siteId.equals(srcOrder.getSiteId()) || !siteId.equals(destOrder.getSiteId())) {
            return false;
        }

        return true;
    }

    @Transactional
    public void removeOrder(final Order order) {
        orderRepository.delete(order);
    }

    @Transactional
    public void persistOrder(final Order pOrder) {
        orderRepository.save(pOrder);
    }

    protected Order initOrder() {
        return new Order();
    }

    protected HardgoodShippingGroup initDefaultHardgoodShippingGroup() {
        return new HardgoodShippingGroup();
    }

    protected PaymentGroup initDefaultPaymentGroup() {
        return new CreditCard();
    }
}
