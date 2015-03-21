package de.andre.entity.core;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_USER", schema = "HYBRIS")
public class DpsUser {
    private Integer userId;
    private String password;
    private String firstName;
    private Byte gender;
    private String email;
    private Date dateOfBirth;
    private DpsCreditCard dpsCreditCard;
    private Set<DpsAddress> dpsAddresses;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeq")
    @SequenceGenerator(name = "profileSeq", sequenceName = "profile_seq")
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "dpsUser")
    public DpsCreditCard getDpsCreditCard() {
        return dpsCreditCard;
    }

    public void setDpsCreditCard(DpsCreditCard dpsCreditCard) {
        this.dpsCreditCard = dpsCreditCard;
    }

    @OneToMany(mappedBy = "dpsUser", fetch = FetchType.EAGER)
    public Set<DpsAddress> getDpsAddresses() {
        return dpsAddresses;
    }

    public void setDpsAddresses(Set<DpsAddress> dpsAddresses) {
        this.dpsAddresses = dpsAddresses;
    }

    @NotBlank
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Column(name = "GENDER")
    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    @Email
    @NotBlank
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "DATE_OF_BIRTH")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DpsUser)) return false;

        DpsUser dpsUser = (DpsUser) o;

        if (dateOfBirth != null ? !dateOfBirth.equals(dpsUser.dateOfBirth) : dpsUser.dateOfBirth != null) return false;
        if (!email.equals(dpsUser.email)) return false;
        if (firstName != null ? !firstName.equals(dpsUser.firstName) : dpsUser.firstName != null) return false;
        if (gender != null ? !gender.equals(dpsUser.gender) : dpsUser.gender != null) return false;
        if (!password.equals(dpsUser.password)) return false;
        if (!userId.equals(dpsUser.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }
}
