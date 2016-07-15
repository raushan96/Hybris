package de.andre.service.commerce.order;

import de.andre.entity.enums.RelationshipType;
import de.andre.entity.enums.ShippingState;
import de.andre.entity.order.*;
import de.andre.entity.profile.Address;
import de.andre.entity.profile.ContactInfo;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShippingGroupsTools {
    private static final Logger logger = LoggerFactory.getLogger(ShippingGroupsTools.class);

    private final ProfileTools profileTools;

    public ShippingGroupsTools(final ProfileTools profileTools) {
        this.profileTools = profileTools;
    }

    public HardgoodShippingGroup createHardgoodShippingGroup(final String pGroupClass, final ShippingState pState) {
        final HardgoodShippingGroup sg;
        if (StringUtils.isEmpty(pGroupClass)) {
            sg = defaultShippingGroup();
        }
        else {
            try {
                sg = (HardgoodShippingGroup) BeanUtils.instantiate(
                        ClassUtils.forName(pGroupClass, ShippingGroupsTools.class.getClassLoader()));
            } catch (ClassNotFoundException ex) {
                throw new IllegalArgumentException("Cannot find class [" + pGroupClass + "]", ex);
            }
        }
        sg.setShippingState(pState);
        sg.setContactInfo(profileContact());
        addShippingPriceInfo(sg);

        if (logger.isDebugEnabled()) {
            logger.debug("Shipping group created: {}", sg);
        }

        return sg;
    }

    protected ShippingPriceInfo addShippingPriceInfo(final HardgoodShippingGroup pGroup) {
        Assert.notNull(pGroup);

        final ShippingPriceInfo priceInfo = new ShippingPriceInfo();
        priceInfo.setShippingGroup(pGroup);
        pGroup.setPriceInfo(priceInfo);
        priceInfo.fillAmounts(BigDecimal.ZERO);

        return priceInfo;
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

    public void modifyContactInfo(final HardgoodShippingGroup pShippingGroup, final Long addressId) {
        Assert.notNull(addressId);
        Assert.notNull(pShippingGroup);

        final ContactInfo mContactInfo = profileContact(addressId);
        Assert.notNull(mContactInfo);

        if (!mContactInfo.equals(pShippingGroup.getContactInfo())) {
            logger.debug("Setting new contact info for the {} shipping group", pShippingGroup.getId());
            pShippingGroup.setContactInfo(mContactInfo);
        }
    }

    public ContactInfo profileContact(final Long addressId) {
        Assert.notNull(addressId);
        final Profile profile = profileTools.currentProfile();

        if (!CollectionUtils.isEmpty(profile.getAddresses())) {
            for (final Map.Entry<String, Address> address : profile.getAddresses().entrySet()) {
                if (Objects.equals(address.getValue().getId(), addressId)) {
                    return address.getValue().getContactInfo();
                }
            }
        }
        return null;
    }

    public HardgoodShippingGroup createHardgoodShippingGroup() {
        return createHardgoodShippingGroup(null, ShippingState.INITIAL);
    }

    protected HardgoodShippingGroup defaultShippingGroup() {
        return new HardgoodShippingGroup();
    }
}
