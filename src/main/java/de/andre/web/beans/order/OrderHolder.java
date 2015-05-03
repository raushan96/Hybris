package de.andre.web.beans.order;

import de.andre.entity.order.DcsppOrder;
import de.andre.service.commerce.OrderManager;
import de.andre.web.beans.account.ProfileHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by andreika on 4/29/2015.
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderHolder {
	private DcsppOrder order;

	private final OrderManager orderManager;
	private final ProfileHolder profileHolder;

	@Autowired
	public OrderHolder(final OrderManager orderManager, final ProfileHolder profileHolder) {
		this.orderManager = orderManager;
		this.profileHolder = profileHolder;
	}

	public DcsppOrder getOrder() {
		if (this.order == null) {
			order = orderManager.getUserCurrentOrder(profileHolder.getProfile());
			if (null == order) {
				order = orderManager.createOrder(profileHolder.getProfile());
			}
		}
		return order;
	}

	public void setOrder(DcsppOrder order) {
		this.order = order;
	}
}
