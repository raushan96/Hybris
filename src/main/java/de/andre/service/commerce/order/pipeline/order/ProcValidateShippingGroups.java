package de.andre.service.commerce.order.pipeline.order;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import de.andre.entity.order.ShippingItemRelationship;
import de.andre.service.commerce.order.pipeline.ProcessContext;
import org.springframework.validation.Errors;

import java.util.List;

public class ProcValidateShippingGroups extends SkeletalOrderProcessor {
    protected ProcValidateShippingGroups(final String name) {
        super(name);
    }

    @Override
    protected void processInternal(final ProcessContext<Order> orderContext, final Errors result) {
        final Order order = orderContext.getTarget();
        final List<HardgoodShippingGroup> hgs = order.getHgShippingGroups();

        if (hgs.size() > 1) {
            // need to check for correct ci assignments
            order.getCommerceItems()
                    .stream()
                    .filter(ci -> !validateCommercetItem(ci, hgs))
                    .forEach(ci -> result.reject("unassigned.ci"));
        }
    }

    private boolean validateCommercetItem(final CommerceItem ci, final List<HardgoodShippingGroup> hgs) {
        final long ciQty = ci.getQuantity();

        long totalQty = 0;
        for (final HardgoodShippingGroup hg : hgs) {
            final ShippingItemRelationship sRel = hg.findCommerceItemRel(ci.getId());
            if (sRel != null) {
                totalQty += sRel.getQuantity();
            }
        }
        return ciQty == totalQty;
    }
}
