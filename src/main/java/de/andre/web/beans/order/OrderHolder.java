package de.andre.web.beans.order;

import de.andre.entity.order.DcsppOrder;
import de.andre.service.account.AccountTools;
import de.andre.service.commerce.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderHolder {
	private DcsppOrder order;

	private final OrderManager orderManager;
	private final AccountTools accountTools;

	@Autowired
	public OrderHolder(final OrderManager orderManager, final AccountTools accountTools) {
		this.orderManager = orderManager;
		this.accountTools = accountTools;
	}

	public DcsppOrder getOrder() {
		if (this.order == null) {
			order = orderManager.getUserCurrentOrder(accountTools.getCommerceUser());
			if (null == order) {
				order = orderManager.createOrder(accountTools.getCommerceUser());
			}
		}
		return order;
	}

	public void setOrder(DcsppOrder order) {
		this.order = order;
	}
}
