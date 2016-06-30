package de.andre.entity.order;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "hcm_price_info", schema = "hybris")
public class PriceInfo extends CommerceIdentifier {
    private BigDecimal amount;
    private BigDecimal rawAmount;
    private String currencyCode = "EUR";
    private boolean discounted;
    private boolean amountIsFinal;

    private List<PriceAdjustment> priceAdjustments;

    @OneToMany(mappedBy = "priceInfo", cascade = CascadeType.ALL)
    @OrderColumn(name = "index_col")
    public List<PriceAdjustment> getPriceAdjustments() {
        return priceAdjustments;
    }

    public List<PriceAdjustment> priceAdjustmentsInternal() {
        if (this.priceAdjustments == null) this.priceAdjustments = new ArrayList<>(4);
        return this.priceAdjustments;
    }

    public void setPriceAdjustments(List<PriceAdjustment> priceAdjustments) {
        this.priceAdjustments = priceAdjustments;
    }

    @NotNull
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @NotNull
    @Column(name = "raw_amount")
    public BigDecimal getRawAmount() {
        return rawAmount;
    }

    public void setRawAmount(BigDecimal rawAmount) {
        this.rawAmount = rawAmount;
    }

    @Column(name = "currency_code")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Type(type = "boolean")
    @Column(name = "discounted")
    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    @Type(type = "boolean")
    @Column(name = "amount_is_final")
    public boolean isAmountIsFinal() {
        return amountIsFinal;
    }

    public void setAmountIsFinal(boolean amountIsFinal) {
        this.amountIsFinal = amountIsFinal;
    }

    public void fillAmounts(BigDecimal pAmount) {
        setAmount(pAmount);
        setRawAmount(pAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceInfo priceInfo = (PriceInfo) o;

        if (discounted != priceInfo.discounted) return false;
        if (amountIsFinal != priceInfo.amountIsFinal) return false;
        if (amount != null ? !amount.equals(priceInfo.amount) : priceInfo.amount != null) return false;
        if (rawAmount != null ? !rawAmount.equals(priceInfo.rawAmount) : priceInfo.rawAmount != null) return false;
        return currencyCode.equals(priceInfo.currencyCode);

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (rawAmount != null ? rawAmount.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (discounted ? 1 : 0);
        result = 31 * result + (amountIsFinal ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return " amount=" + amount +
                ", rawAmount=" + rawAmount +
                ", currencyCode='" + currencyCode + '\'' +
                ", discounted=" + discounted +
                ", amountIsFinal=" + amountIsFinal +
                '}';
    }
}
