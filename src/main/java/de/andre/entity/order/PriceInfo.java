package de.andre.entity.order;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "hcm_price_info", schema = "hybris")
public class PriceInfo extends CommerceIdentifier {
    private BigDecimal amount;
    private BigDecimal rawAmount;
    private Currency currency = Currency.getInstance("USD");
    private boolean discounted;
    private boolean amountIsFinal;

    private List<PriceAdjustment> priceAdjustments;

    @OneToMany(mappedBy = "priceInfo", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void resetAdjustments(List<PriceAdjustment> priceAdjustments) {
        this.priceAdjustmentsInternal().clear();
        this.priceAdjustments.addAll(priceAdjustments);
    }

    @NotNull
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void increaseAmount(BigDecimal amount) {
        setAmount(this.getAmount().add(amount));
    }

    @NotNull
    @Column(name = "raw_amount")
    public BigDecimal getRawAmount() {
        return rawAmount;
    }

    public void setRawAmount(BigDecimal rawAmount) {
        this.rawAmount = rawAmount;
    }

    public void increaseRawAmount(BigDecimal rawAmount) {
        setRawAmount(this.getRawAmount().add(rawAmount));
    }

    @Type(type = "currency")
    @Column(name = "currency")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        return currency.equals(priceInfo.currency);

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (rawAmount != null ? rawAmount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (discounted ? 1 : 0);
        result = 31 * result + (amountIsFinal ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return " amount=" + amount +
                ", rawAmount=" + rawAmount +
                ", currencyCode='" + currency + '\'' +
                ", discounted=" + discounted +
                ", amountIsFinal=" + amountIsFinal +
                '}';
    }
}
