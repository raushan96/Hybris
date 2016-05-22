package de.andre.entity.order;

import de.andre.entity.enums.OrderState;
import de.andre.entity.profile.Profile;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "hcm_order", schema = "hybris")
public class Order extends BaseCommerceEntity {
    private String number;
    private String siteId;
    private String serverInfo;
    private OrderState state;
    private Long version;
    private LocalDateTime creationDate;
    private LocalDateTime submittedDate;
    private LocalDateTime lastModifiedDate;

    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    @Version
    @Column(name = "version")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    @NotNull
    @Column(name = "last_modified_date")
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
}
