package de.andre.entity.core;

import de.andre.entity.BaseEntity;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_USER", schema = "ANDRE")
public class DpsUser extends BaseEntity {
    private String login;
    private String password;
    private String firstName;
    private Byte gender;
    private String email;
    private Date dateOfBirth;

    @Basic
    @NotNull
    @Column(name = "LOGIN")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @NotNull
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @NotNull
    @Column(name = "GENDER")
    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    @Basic
    @Email
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
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
        if (o == null || getClass() != o.getClass()) return false;

        DpsUser dpsUser = (DpsUser) o;

        if (dateOfBirth != null ? !dateOfBirth.equals(dpsUser.dateOfBirth) : dpsUser.dateOfBirth != null) return false;
        if (email != null ? !email.equals(dpsUser.email) : dpsUser.email != null) return false;
        if (firstName != null ? !firstName.equals(dpsUser.firstName) : dpsUser.firstName != null) return false;
        if (gender != null ? !gender.equals(dpsUser.gender) : dpsUser.gender != null) return false;
        if (login != null ? !login.equals(dpsUser.login) : dpsUser.login != null) return false;
        if (password != null ? !password.equals(dpsUser.password) : dpsUser.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.getId();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }
}
