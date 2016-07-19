package de.andre.service.commerce.order.processor.order;

import de.andre.entity.order.CommerceItem;
import de.andre.entity.order.HardgoodShippingGroup;
import de.andre.entity.order.Order;
import de.andre.entity.order.ShippingItemRelationship;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.List;

public class ProcValidateShippingGroups extends SkeletalOrderProcessor {
    protected ProcValidateShippingGroups(final String name, final MessageSource messageSource) {
        super(name, messageSource);
    }

    @Override
    protected void processInternal(final OrderContext orderContext, final Errors result) {
        final Order order = orderContext.getOrder();
        final List<HardgoodShippingGroup> hgs = order.getHgShippingGroups();

        if (hgs.size() > 1) {
            // need to check for correct ci assignments
            for (final CommerceItem ci : order.getCommerceItems()) {
                if (!validateCommercetItem(ci, hgs)) {
//                    result.addError(PipelineError.of("unassigned.ci"));
                }
            }
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
