package de.andre.entity.order;

import de.andre.entity.profile.ContactInfo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hcm_hg_shipping_group", schema = "hybris")
public class HardgoodShippingGroup extends ShippingGroup {
    private ContactInfo contactInfo;

    @Embedded
    @NotNull
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
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
}
