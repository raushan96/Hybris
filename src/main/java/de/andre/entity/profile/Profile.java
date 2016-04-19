package de.andre.entity.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.andre.entity.enums.Gender;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "hp_profile", schema = "hybris")
public class Profile extends ProfileBaseEntity {
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private boolean acceptEmails;
    private LocalDateTime created;

    private Map<String, Address> addresses;
    private Set<Interest> interests;
    private WishList wishList;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "addressName")
    public Map<String, Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            this.addresses = new HashMap<>(4);
        }
        address.setProfile(this);
        this.addresses.put(address.getAddressName(), address);
    }

    public void setAddresses(Map<String, Address> addresses) {
        this.addresses = addresses;
    }

    @ManyToMany
    @JoinTable(
            name = "hp_profile_interests",
            joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id", referencedColumnName = "id")
    )
    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true,
             fetch = FetchType.LAZY)
    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    @JsonIgnore
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    @Length(min = 7, max = 255)
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @NotNull
    @Column(name = "date_of_birth")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Type(type = "boolean")
    @Column(name = "accept_emails")
    public boolean isAcceptEmails() {
        return acceptEmails;
    }

    public void setAcceptEmails(boolean acceptEmails) {
        this.acceptEmails = acceptEmails;
    }

    @Generated(GenerationTime.INSERT)
    @Column(name = "created", insertable = false, updatable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile hpProfile = (Profile) o;

        if (password != null ? !password.equals(hpProfile.password) : hpProfile.password != null) return false;
        if (email != null ? !email.equals(hpProfile.email) : hpProfile.email != null) return false;
        if (firstName != null ? !firstName.equals(hpProfile.firstName) : hpProfile.firstName != null) return false;
        if (lastName != null ? !lastName.equals(hpProfile.lastName) : hpProfile.lastName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(hpProfile.dateOfBirth) : hpProfile.dateOfBirth != null)
            return false;
        if (created != null ? !created.equals(hpProfile.created) : hpProfile.created != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
