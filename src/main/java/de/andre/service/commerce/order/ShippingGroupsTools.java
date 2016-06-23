package de.andre.service.commerce.order;

import de.andre.entity.enums.RelationshipType;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.order.*;
import de.andre.entity.profile.Address;
import de.andre.entity.profile.Profile;
import de.andre.service.account.ProfileTools;
import de.andre.utils.HybrisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShippingGroupsTools {
    private static final Logger logger = LoggerFactory.getLogger(ShippingGroupsTools.class);

    private final ProfileTools profileTools;

    public ShippingGroupsTools(final ProfileTools profileTools) {
        this.profileTools = profileTools;
    }

    public ShippingGroup createShippingGroup(final String pGroupClass, final ShippingState pState) {
        final ShippingGroup sg;
        if (StringUtils.isEmpty(pGroupClass)) {
            sg = defaultShippingGroup();
        }
        else {
            try {
                sg = (ShippingGroup) BeanUtils.instantiate(
                        ClassUtils.forName(pGroupClass, ShippingGroupsTools.class.getClassLoader()));
            } catch (ClassNotFoundException ex) {
                throw new IllegalArgumentException("Cannot find class [" + pGroupClass + "]", ex);
            }
        }
        sg.setShippingState(pState);

        if (logger.isDebugEnabled()) {
            logger.debug("Shipping group created: {}", sg);
        }

        return sg;
    }

    public HardgoodShippingGroup modifyShippingGroupQuantity(final CommerceItem pItem, final Long pQuantity, final HardgoodShippingGroup pShippingGroup) {
        Assert.notNull(pItem);
        Assert.notNull(pQuantity);
        final Order order = pItem.getOrder();
        final HardgoodShippingGroup shippingGroup = pShippingGroup != null ? pShippingGroup : defaultShippingGroup(order);
        Assert.notNull(shippingGroup, "Shipping invariant");

        if (!shippingGroup.hasItemsRels()) {
            logger.debug("Order {} does not contain shipping relations", order.getId());
            return shippingGroup;
        }

        for (final ShippingItemRelationship sir : shippingGroup.getShippingItemRelationships()) {
            final CommerceItem existingCi = sir.getCommerceItem();
            if (Objects.equals(existingCi.getId(), pItem.getId())) {
                final Long mQuantity = existingCi.getQuantity() + pQuantity;
                if (mQuantity > 0) {
                    logger.debug("Setting quantity to {} in existing shipping relationship", mQuantity);
                    sir.setQuantity(mQuantity);
                }
                else {
                    logger.debug("Quantity is {}, removing {} ci from relationship", mQuantity, existingCi.getId());
                    removeShippingRelationship(existingCi, shippingGroup, sir);
                    if (!shippingGroup.hasItemsRels() && order.hasMultipleShipping()) {
                        changeDefaultShippingGroup(order, shippingGroup);
                    }
                }
            }
        }

        return shippingGroup;
    }

    public HardgoodShippingGroup defaultShippingGroup(final Order pOrder) {
        Assert.notNull(pOrder);

        final List<HardgoodShippingGroup> hgs = pOrder.getHgShippingGroups();
        if (!CollectionUtils.isEmpty(hgs)) {
            if (hgs.size() == 1) {
                return hgs.get(0);
            }
            else {
                for (final HardgoodShippingGroup hg : hgs) {
                    if (hg.hasItemsRels()) {
                        for (final ShippingItemRelationship sir : hg.getShippingItemRelationships()) {
                            if (RelationshipType.REMAINING.equals(sir.getType())) {
                                return hg;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public HardgoodShippingGroup splitItemQuantity(final CommerceItem pItem, final Long pQuantity, final HardgoodShippingGroup oldGroup,
            final HardgoodShippingGroup newGroup) {
        Assert.notNull(pItem);
        Assert.notNull(oldGroup);
        Assert.notNull(newGroup);
        Assert.isTrue(pQuantity != null && pQuantity > 0, "Quantity must be greater than zero, got: " + pQuantity);

        modifyShippingGroupQuantity(pItem, -pQuantity, oldGroup);
        addShippingRel(newGroup, pItem);
        modifyShippingGroupQuantity(pItem, pQuantity, newGroup);

        return newGroup;
    }

    public void copyUserAddress(final HardgoodShippingGroup pShippingGroup, final String pAddressName) {
        final Profile profile = profileTools.currentProfile();
        if (pShippingGroup != null && profile != null) {
            final Map<String, Address> profileAddresses = profile.getAddresses();
            if (!CollectionUtils.isEmpty(profileAddresses)) {
                final String addrName = StringUtils.hasLength(pAddressName) ? pAddressName : HybrisConstants.DEFAULT_SHIPPING_NAME;
                final Address candidate = profileAddresses.get(addrName);
                if (candidate != null) {
                    pShippingGroup.setContactInfo(candidate.getContactInfo().clone());
                }
            }
        }
    }

    private ShippingItemRelationship addShippingRel(final HardgoodShippingGroup pShippingGroup, final CommerceItem pItem) {
        final ShippingItemRelationship rel = new ShippingItemRelationship();
        rel.setShippingGroup(pShippingGroup);
        rel.setCommerceItem(pItem);
        rel.setType(RelationshipType.QUANTITY);

        pShippingGroup.itemsRelsInternal().add(rel);
        pItem.shipRelsInternal().add(rel);
        return rel;
    }

    private void removeShippingRelationship(final CommerceItem pItem, final HardgoodShippingGroup pHg, final ShippingItemRelationship pRel) {
        if (pItem.hasShippingRels()) {
            pItem.getShippingItemRelationships().remove(pRel);
        }
        if (pHg.hasItemsRels()) {
            pHg.getShippingItemRelationships().remove(pRel);
        }
    }

    private void changeDefaultShippingGroup(final Order pOrder, final HardgoodShippingGroup pOldShipGroup) {
        final List<HardgoodShippingGroup> shippingGroups = pOrder.getHgShippingGroups();
        if (shippingGroups.remove(pOldShipGroup)) {
            for (final HardgoodShippingGroup hg : shippingGroups) {
                if (hg != null ) {
                    for (final ShippingItemRelationship sir : hg.itemsRelsInternal()) {
                        sir.setType(RelationshipType.REMAINING);
                    }
                }
            }
        }
    }

    public ShippingGroup createShippingGroup() {
        return createShippingGroup(null, ShippingState.INITIAL);
    }

    protected ShippingGroup defaultShippingGroup() {
        return new HardgoodShippingGroup();
    }
}
