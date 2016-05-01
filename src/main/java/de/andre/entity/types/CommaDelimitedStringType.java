package de.andre.entity.types;

import org.hibernate.annotations.TypeDef;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import javax.persistence.MappedSuperclass;
import java.util.Set;

@MappedSuperclass
@TypeDef(name = "comma_delimited_set", typeClass = CommaDelimitedStringType.class)
public class CommaDelimitedStringType extends AbstractSingleColumnStandardBasicType<Set> {
    public CommaDelimitedStringType() {
        super(VarcharTypeDescriptor.INSTANCE, DelimitedStringSetDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "comma_delimited_set";
    }
}
