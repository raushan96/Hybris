package de.andre.entity.core;

import de.andre.entity.BaseEntity;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_CREDIT_CARD", schema = "ANDRE")
public class DpsCreditCard extends BaseEntity {
    private String creditCardNumber;
    private Date expirationDate;

    @Basic
    @NotBlank
    @CreditCardNumber
    @Column(name = "CREDIT_CARD_NUMBER")
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Basic
    @NotBlank
    @Column(name = "EXPIRATION_DATE")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DpsCreditCard that = (DpsCreditCard) o;

        if (creditCardNumber != null ? !creditCardNumber.equals(that.creditCardNumber) : that.creditCardNumber != null)
            return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = creditCardNumber != null ? creditCardNumber.hashCode() : 0;
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
