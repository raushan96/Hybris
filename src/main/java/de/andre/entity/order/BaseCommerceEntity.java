package de.andre.entity.order;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseCommerceEntity {
    protected Long id;

    @Id
    @GenericGenerator(name = "commerceGenerator", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "hcom_identity_generator"),
            @Parameter(name = "segment_column_name", value = "sequence_name"),
            @Parameter(name = "value_column_name", value = "sequence_value"),
            @Parameter(name = "prefer_entity_table_as_segment_value", value = "true"),
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "10")
    })
    @GeneratedValue(generator = "commerceGenerator")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public boolean isNew() {
        return this.id == null;
    }
}
