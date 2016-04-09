package de.andre.entity.order;

import javax.persistence.*;

/**
 * Created by andreika on 5/2/2015.
 */
@Entity
@Table(name = "DCSPP_PRICE_ADJUST", schema = "HYBRIS")
public class DcsppPriceAdjust {
    private Integer adjustmentId;
    private Long version;
    private String adjDescription;
    private Double adjustment;
    private Integer qtyAdjusted;
    private Integer sequence;
    private DcsppAmountInfo amountInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
    @SequenceGenerator(name = "orderSeq", sequenceName = "order_seq", allocationSize = 10)
    @Column(name = "ADJUSTMENT_ID")
    public Integer getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Integer adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    @Version
    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @ManyToOne
    @JoinColumn(name = "PRICE_INFO")
    public DcsppAmountInfo getAmountInfo() {
        return amountInfo;
    }

    public void setAmountInfo(DcsppAmountInfo amountInfo) {
        this.amountInfo = amountInfo;
    }

    @Column(name = "ADJ_DESCRIPTION")
    public String getAdjDescription() {
        return adjDescription;
    }

    public void setAdjDescription(String adjDescription) {
        this.adjDescription = adjDescription;
    }


    @Column(name = "ADJUSTMENT")
    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }


    @Column(name = "QTY_ADJUSTED")
    public Integer getQtyAdjusted() {
        return qtyAdjusted;
    }

    public void setQtyAdjusted(Integer qtyAdjusted) {
        this.qtyAdjusted = qtyAdjusted;
    }


    @Column(name = "SEQUENCE")
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DcsppPriceAdjust that = (DcsppPriceAdjust) o;

        if (!sequence.equals(that.sequence)) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (adjDescription != null ? !adjDescription.equals(that.adjDescription) : that.adjDescription != null)
            return false;
        if (adjustment != null ? !adjustment.equals(that.adjustment) : that.adjustment != null) return false;
        if (qtyAdjusted != null ? !qtyAdjusted.equals(that.qtyAdjusted) : that.qtyAdjusted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (adjDescription != null ? adjDescription.hashCode() : 0);
        result = 31 * result + (adjustment != null ? adjustment.hashCode() : 0);
        result = 31 * result + (qtyAdjusted != null ? qtyAdjusted.hashCode() : 0);
        result = 31 * result + (sequence != null ? sequence * 17 : 0);
        return result;
    }
}
