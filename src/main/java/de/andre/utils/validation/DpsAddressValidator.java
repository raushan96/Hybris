package de.andre.utils.validation;

import de.andre.entity.catalog.DcsProduct;
import de.andre.entity.core.DpsAddress;
import de.andre.entity.core.DpsUser;
import de.andre.entity.enums.OrderState;
import de.andre.entity.enums.ShippingMethod;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.enums.ShippingType;
import de.andre.entity.order.DcsppAmountInfo;
import de.andre.entity.order.DcsppItem;
import de.andre.entity.order.DcsppOrder;
import de.andre.entity.order.DcsppShipGroup;
import de.andre.repository.OrderRepository;
import de.andre.repository.ProductRepository;
import de.andre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andrei on 4/14/2015.
 */

@Component
@Scope(value = "singleton")
public class DpsAddressValidator implements Validator {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Autowired
	public DpsAddressValidator(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		DpsUser user = userRepository.getOne(1);
		DcsProduct product = productRepository.getOne(103);

		DcsppOrder order = new DcsppOrder();
		order.setCreationDate(Calendar.getInstance().getTime());
		order.setLastModifiedDate(Calendar.getInstance().getTime());
		order.setOrderNumber("test123");
		order.setState(OrderState.INCOMPLETE);
		order.setDpsUser(user);

		DcsppAmountInfo amountInfo = new DcsppAmountInfo();
		amountInfo.setCurrencyCode("EUR");
		amountInfo.setAmount(23.0);
		amountInfo.setQuantity(1);
		order.setAmountInfo(amountInfo);

		DcsppShipGroup shipGroup = new DcsppShipGroup();
		shipGroup.setShipOnDate(Calendar.getInstance().getTime());
		shipGroup.setShippingMethod(ShippingMethod.GROUND);
		shipGroup.setShippingType(ShippingType.NORMAL);
		shipGroup.setState(ShippingState.INITIAL);
		DcsppAmountInfo amountInfoShihpping = new DcsppAmountInfo();
		amountInfo.setCurrencyCode("EUR");
		amountInfo.setAmount(23.0);
		amountInfo.setQuantity(1);
		shipGroup.setAmountInfo(amountInfoShihpping);
		order.addShippingGroup(shipGroup);

		DcsppItem item = new DcsppItem();
		DcsppAmountInfo amountInfoItem = new DcsppAmountInfo();
		amountInfo.setCurrencyCode("EUR");
		amountInfo.setAmount(23.0);
		amountInfo.setQuantity(1);
		shipGroup.setAmountInfo(amountInfoItem);
		item.setProduct(product);
		item.setQuantity(2);
		order.addCommerceItem(item);

		orderRepository.save(order);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return DpsAddress.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DpsAddress dpsAddress = (DpsAddress) target;
	}
}
