package de.andre.entity.order;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "hcm_price_adjust", schema = "hybris")
public class PriceAdjustment extends CommerceIdentifier {
    private String adjDescription;
    private BigDecimal adjustment;
    private Long qtyAdjusted;

    private PriceInfo priceInfo;

    @ManyToOne
    @JoinColumn(name = "price_info_id")
    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @NotEmpty
    @Column(name = "adj_description")
    public String getAdjDescription() {
        return adjDescription;
    }

    public void setAdjDescription(String adjDescription) {
        this.adjDescription = adjDescription;
    }

    @NotNull
    @Column(name = "adjustment")
    public BigDecimal getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(BigDecimal adjustment) {
        this.adjustment = adjustment;
    }

    @NotNull
    @Column(name = "qty_adjusted")
    public Long getQtyAdjusted() {
        return qtyAdjusted;
    }

    public void setQtyAdjusted(Long qtyAdjusted) {
        this.qtyAdjusted = qtyAdjusted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceAdjustment that = (PriceAdjustment) o;

        if (adjDescription != null ? !adjDescription.equals(that.adjDescription) : that.adjDescription != null)
            return false;
        if (adjustment != null ? !adjustment.equals(that.adjustment) : that.adjustment != null) return false;
        return qtyAdjusted != null ? qtyAdjusted.equals(that.qtyAdjusted) : that.qtyAdjusted == null;

    }

    @Override
    public int hashCode() {
        int result = adjDescription != null ? adjDescription.hashCode() : 0;
        result = 31 * result + (adjustment != null ? adjustment.hashCode() : 0);
        result = 31 * result + (qtyAdjusted != null ? qtyAdjusted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PriceAdjustment{" +
                "adjDescription='" + adjDescription + '\'' +
                ", adjustment=" + adjustment +
                ", qtyAdjusted=" + qtyAdjusted +
                '}';
    }
}
