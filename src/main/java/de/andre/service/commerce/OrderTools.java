package de.andre.service.commerce;

import de.andre.entity.core.DpsUser;
import de.andre.entity.enums.AmountType;
import de.andre.entity.enums.OrderState;
import de.andre.entity.enums.PaymentStatus;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.order.DcsppAmountInfo;
import de.andre.entity.order.DcsppOrder;
import de.andre.entity.order.DcsppPayGroup;
import de.andre.entity.order.DcsppShipGroup;
import de.andre.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by andreika on 5/3/2015.
 */
@Service
public class OrderTools {
	private static final Logger log = LoggerFactory.getLogger(OrderTools.class);

	private final OrderRepository orderRepository;

	@Autowired
	public OrderTools(final OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public DcsppOrder getOrderInstance(DpsUser profile) {
		try {
			DcsppOrder order = new DcsppOrder();
			order.setCreationDate(Calendar.getInstance().getTime());
			order.setLastModifiedDate(Calendar.getInstance().getTime());
			order.setOrderNumber("hyb" + String.valueOf(new Random().nextInt() * 100));
			order.setState(OrderState.INCOMPLETE);
			order.setDpsUser(profile);

			DcsppAmountInfo amountInfo = new DcsppAmountInfo();
			amountInfo.setCurrencyCode("EUR");
			amountInfo.setType(AmountType.ORDER_PRICE_INFO);
			order.setAmountInfo(amountInfo);

			DcsppShipGroup shipGroup = new DcsppShipGroup();
			shipGroup.setState(ShippingState.INITIAL);
			order.addShippingGroup(shipGroup);

			DcsppPayGroup payGroup = new DcsppPayGroup();
			payGroup.setCurrencyCode("EUR");
			payGroup.setState(PaymentStatus.INITIAL);
			order.addPaymentGroup(payGroup);

			orderRepository.save(order);

			return order;
		} catch (Exception e) {
			log.error(e.toString());
		}
		return null;
	}

	@Transactional
	public void persistOrder(final DcsppOrder pOrder) {
		orderRepository.saveAndFlush(pOrder);
	}
}
