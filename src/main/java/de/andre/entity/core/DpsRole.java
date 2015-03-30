package de.andre.entity.core;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_ROLE", schema = "HYBRIS")
public class DpsRole {
	private Integer roleId;
	private String name;
	private String description;

	@Id
	@GeneratedValue
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Basic
	@NotBlank
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
