package de.andre.service.commerce.order.processor.order;

import de.andre.entity.order.Order;
import de.andre.multisite.Site;
import de.andre.service.commerce.order.processor.BaseContext;
import de.andre.service.commerce.order.processor.PipelineConstants;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OrderContext extends BaseContext {
    private final Order order;

    public OrderContext(final Order order, final Locale locale, final Site site) {
        super(locale, site);
        Assert.notNull(order);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderContext{" +
                "order=" + order +
                ", locale=" + getLocale() +
                ", site=" + getSite() +
                '}';
    }
}
