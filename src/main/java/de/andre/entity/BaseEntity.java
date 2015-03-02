package de.andre.entity;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalSeq")
    @SequenceGenerator(name = "globalSeq", sequenceName = "global_seq")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private boolean isNew() {
        return (this.id == null);
    }
}
