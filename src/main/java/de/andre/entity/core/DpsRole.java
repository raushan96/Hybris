package de.andre.entity.core;

import de.andre.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by andreika on 2/28/2015.
 */
@Entity
@Table(name = "DPS_ROLE", schema = "ANDRE")
public class DpsRole extends BaseEntity {
    private String name;
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DpsRole dpsRole = (DpsRole) o;

        if (description != null ? !description.equals(dpsRole.description) : dpsRole.description != null) return false;
        if (name != null ? !name.equals(dpsRole.name) : dpsRole.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.getId();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
