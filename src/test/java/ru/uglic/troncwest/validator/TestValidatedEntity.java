package ru.uglic.troncwest.validator;

import ru.uglic.troncwest.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test_validated_entity")
public class TestValidatedEntity extends AbstractBaseEntity {
    @Column(name = "validName")
    @RegexpSpecial(pattern = "^ls([a,b].+)(.*)")
    private String validName;

    public String getValidName() {
        return validName;
    }

    public void setValidName(String validName) {
        this.validName = validName;
    }
}
