package de.andre.entity.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.profile.Profile;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hcm_order", schema = "hybris")
public class Order extends CommerceIdentifier {
    private String number;
    private String siteId;
    private String serverInfo;
    private OrderState state;
    private LocalDateTime creationDate;
    private LocalDateTime submittedDate;
    private LocalDateTime lastModifiedDate;

    private Profile profile;
    private OrderPriceInfo priceInfo;
    private List<CommerceItem> commerceItems = new ArrayList<>(0);
    private List<HardgoodShippingGroup> hgShippingGroups = new ArrayList<>(0);
    private List<PaymentGroup> paymentGroups = new ArrayList<>(0);

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id")
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "index_col")
    public List<CommerceItem> getCommerceItems() {
        return commerceItems;
    }

    public void addCommerceItem(CommerceItem ci) {
        if (ci != null) {
            getCommerceItems().add(ci);
            ci.setOrder(this);
        }
    }

    public void setCommerceItems(List<CommerceItem> commerceItems) {
        this.commerceItems = commerceItems;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "index_col")
    public List<HardgoodShippingGroup> getHgShippingGroups() {
        return hgShippingGroups;
    }

    public void addHgShippingGroup(HardgoodShippingGroup hg) {
        if (hg != null) {
            getHgShippingGroups().add(hg);
            hg.setOrder(this);
        }
    }

    public void setHgShippingGroups(List<HardgoodShippingGroup> hgShippingGroups) {
        this.hgShippingGroups = hgShippingGroups;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "index_col")
    public List<PaymentGroup> getPaymentGroups() {
        return paymentGroups;
    }

    public void addPaymentGroup(PaymentGroup pg) {
        if (pg != null) {
            getPaymentGroups().add(pg);
            pg.setOrder(this);
        }
    }

    public void setPaymentGroups(List<PaymentGroup> paymentGroups) {
        this.paymentGroups = paymentGroups;
    }

    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_info_id")
    public OrderPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(OrderPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @NotEmpty
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotEmpty
    @Column(name = "site_id")
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @NotEmpty
    @Column(name = "server_info")
    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @NotNull
    @Column(name = "creation_date")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "submitted_date")
    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    @LastModifiedDate
    @NotNull
    @Column(name = "last_modified_date")
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean hasMultipleShipping() {
        return !CollectionUtils.isEmpty(getHgShippingGroups()) && getHgShippingGroups().size() > 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order hcmOrder = (Order) o;

        if (number != null ? !number.equals(hcmOrder.number) : hcmOrder.number != null) return false;
        if (siteId != null ? !siteId.equals(hcmOrder.siteId) : hcmOrder.siteId != null) return false;
        if (serverInfo != null ? !serverInfo.equals(hcmOrder.serverInfo) : hcmOrder.serverInfo != null) return false;
        if (state != null ? !state.equals(hcmOrder.state) : hcmOrder.state != null) return false;
        if (creationDate != null ? !creationDate.equals(hcmOrder.creationDate) : hcmOrder.creationDate != null)
            return false;
        if (submittedDate != null ? !submittedDate.equals(hcmOrder.submittedDate) : hcmOrder.submittedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (siteId != null ? siteId.hashCode() : 0);
        result = 31 * result + (serverInfo != null ? serverInfo.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (submittedDate != null ? submittedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "lastModifiedDate=" + lastModifiedDate +
                ", siteId='" + siteId + '\'' +
                ", serverInfo='" + serverInfo + '\'' +
                ", state=" + state +
                ", creationDate=" + creationDate +
                ", submittedDate=" + submittedDate +
                ", number='" + number + '\'' +
                '}';
    }
}
