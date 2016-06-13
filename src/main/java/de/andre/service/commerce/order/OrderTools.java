package de.andre.service.commerce.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.enums.PaymentState;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.order.*;
import de.andre.entity.profile.Address;
import de.andre.entity.profile.ContactInfo;
import de.andre.entity.profile.Profile;
import de.andre.multisite.SiteManager;
import de.andre.repository.order.OrderRepository;
import de.andre.service.account.ProfileTools;
import de.andre.service.price.PriceTools;
import de.andre.utils.HybrisConstants;
import de.andre.utils.ProfileUtils;
import de.andre.utils.idgen.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderTools {
    private static final Logger logger = LoggerFactory.getLogger(OrderTools.class);

    private final OrderRepository orderRepository;
    private final ProfileTools profileTools;
    private final PriceTools priceTools;
    private final IdGenerator idGenerator;

    public OrderTools(final OrderRepository orderRepository, final ProfileTools profileTools, final PriceTools priceTools,
            final IdGenerator idGenerator) {
        this.orderRepository = orderRepository;
        this.profileTools = profileTools;
        this.priceTools = priceTools;
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

//            DcsppAmountInfo amountInfo = new DcsppAmountInfo();
//            amountInfo.setCurrencyCode("EUR");
//            amountInfo.setType(AmountType.ORDER_PRICE_INFO);
//            order.setAmountInfo(amountInfo);
//
        final HardgoodShippingGroup shipGroup = initDefaultHardgoodShippingGroup();
        shipGroup.setShippingState(ShippingState.INITIAL);
        shipGroup.setContactInfo(profileContact());
        order.addHgShippingGroup(shipGroup);

        final PaymentGroup payGroup = initDefaultPaymentGroup();
        payGroup.setState(PaymentState.INITIAL);
        payGroup.setCurrency(priceTools.getCurrency(null));
        order.addPaymentGroup(payGroup);

        if (logger.isDebugEnabled()) {
            logger.debug("Created order instance {}", order);
        }

        persistOrder(order);
        return order;
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
        return orderRepository.findOne(orderId);
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

    private ContactInfo profileContact() {
        final Profile profile = profileTools.currentProfile();

        if (!CollectionUtils.isEmpty(profile.getAddresses())) {
            final Address shipAddress = profile.getAddresses().get(HybrisConstants.DEFAULT_SHIPPING_NAME);
            if (shipAddress != null) {
                return shipAddress.getContactInfo();
            }

            for (final Map.Entry<String, Address> address : profile.getAddresses().entrySet()) {
                return address.getValue().getContactInfo();
            }
        }

        return new ContactInfo();
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
