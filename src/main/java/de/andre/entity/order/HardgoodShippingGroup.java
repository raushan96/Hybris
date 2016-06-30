package de.andre.entity.order;

import de.andre.entity.profile.ContactInfo;
import org.springframework.util.CollectionUtils;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hcm_hg_shipping_group", schema = "hybris")
public class HardgoodShippingGroup extends ShippingGroup {
    private ContactInfo contactInfo;
    private Set<ShippingItemRelationship> shippingItemRelationships;

    @Embedded
    @NotNull
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @OneToMany(mappedBy = "shippingGroup")
    public Set<ShippingItemRelationship> getShippingItemRelationships() {
        return shippingItemRelationships;
    }

    public void setShippingItemRelationships(Set<ShippingItemRelationship> shippingItemRelationship) {
        this.shippingItemRelationships = shippingItemRelationship;
    }

    public Set<ShippingItemRelationship> itemsRelsInternal() {
        if (this.shippingItemRelationships == null) {
            this.shippingItemRelationships = new HashSet<>();
        }
        return this.shippingItemRelationships;
    }

    public boolean hasItemsRels() {
        return !CollectionUtils.isEmpty(getShippingItemRelationships());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HardgoodShippingGroup that = (HardgoodShippingGroup) o;

        return contactInfo != null ? contactInfo.equals(that.contactInfo) : that.contactInfo == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HardgoodShippingGroup{" + "contactInfo=" + contactInfo +
                "shippingState=" + getShippingState() +
                '}';
    }
}
