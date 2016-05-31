package de.andre.entity.order;

import de.andre.entity.enums.PaymentState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "hcm_pay_group")
public abstract class PaymentGroup extends CommerceIdentifier {
    private BigDecimal amount;
    private BigDecimal amountAuthorized;
    private Currency currency;
    private PaymentState state;
    private LocalDateTime submittedDate;

    private Order order;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "amount_authorized")
    public BigDecimal getAmountAuthorized() {
        return amountAuthorized;
    }

    public void setAmountAuthorized(BigDecimal amountAuthorized) {
        this.amountAuthorized = amountAuthorized;
    }

    @Type(type = "currency")
    @Column(name = "currency_code")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    @Column(name = "submitted_date")
    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentGroup that = (PaymentGroup) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (amountAuthorized != null ? !amountAuthorized.equals(that.amountAuthorized) : that.amountAuthorized != null)
            return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (submittedDate != null ? !submittedDate.equals(that.submittedDate) : that.submittedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (amountAuthorized != null ? amountAuthorized.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (submittedDate != null ? submittedDate.hashCode() : 0);
        return result;
    }
}
