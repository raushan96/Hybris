package de.andre.entity.core;

import de.andre.entity.core.enums.Gender;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
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
	private String lastName;
	private Gender gender;
	private String email;
	private Date dateOfBirth;
	private Set<DpsCreditCard> dpsCreditCard;
	private Set<DpsAddress> dpsAddresses;
	private DpsGiftlist dpsGiftlist;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeq")
	@SequenceGenerator(name = "profileSeq", sequenceName = "profile_seq", allocationSize = 10)
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@OneToMany(mappedBy = "dpsUser")
	public Set<DpsCreditCard> getDpsCreditCard() {
		return dpsCreditCard;
	}

	public void setDpsCreditCard(Set<DpsCreditCard> dpsCreditCard) {
		this.dpsCreditCard = dpsCreditCard;
	}

	@OneToMany(mappedBy = "dpsUser")
	public Set<DpsAddress> getDpsAddresses() {
		return dpsAddresses;
	}

	public void setDpsAddresses(Set<DpsAddress> dpsAddresses) {
		this.dpsAddresses = dpsAddresses;
	}

	@OneToOne(mappedBy = "dpsUser")
	public DpsGiftlist getDpsGiftlist() {
		return dpsGiftlist;
	}

	public void setDpsGiftlist(DpsGiftlist dcsGiftlist) {
		this.dpsGiftlist = dcsGiftlist;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 3, max = 20)
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Length(min = 3, max = 20)
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "GENDER")
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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
	@Temporal(TemporalType.DATE)
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

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, gender, email, dateOfBirth);
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("userId", this.getUserId())
				.append("firstName", this.getFirstName())
				.append("lastName", this.getLastName())
				.append("gender", this.getGender())
				.append("email", this.getEmail())
				.append("dateOfBirth", this.getDateOfBirth())
				.toString();
	}
}