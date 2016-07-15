package de.andre.entity.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.Month;
import java.time.Year;

public class PaymentForm {
    @NotEmpty
    private String cardNumber;
    @NotEmpty
    private String cardType;
    @NotEmpty
    private String cvv;
    @NotEmpty
    private String owner;
    @NotNull
    private Year expiryYear;
    @NotNull
    private Month expiryMonth;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Year getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Year expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Month getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Month expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    @Override
    public String toString() {
        return "PaymentForm{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cvv='" + cvv + '\'' +
                ", owner='" + owner + '\'' +
                ", expiryYear=" + expiryYear +
                ", expiryMonth=" + expiryMonth +
                '}';
    }
}
