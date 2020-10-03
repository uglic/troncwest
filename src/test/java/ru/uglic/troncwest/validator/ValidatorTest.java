package ru.uglic.troncwest.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.ConstraintViolationException;

@SpringBootTest
@ComponentScan(basePackages = {"ru.uglic.troncwest"})
public class ValidatorTest {
    private final TestValidatedEntityService service;

    @Autowired
    public ValidatorTest(TestValidatedEntityService service) {
        this.service = service;
    }

    @Test
    public void testValidName() {
        TestValidatedEntity entity = new TestValidatedEntity();
        entity.setValidName("lsac");
        service.save(entity);
        service.delete(entity);
    }

    @Test
    public void testInValidName() {
        TestValidatedEntity entity = new TestValidatedEntity();
        entity.setValidName("lsca");
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                service.save(entity);
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }

    private Throwable getRootCause(Throwable root) {
        Throwable last = root;
        while (last != null && last.getCause() != null && !last.equals(last.getCause())) {
            last = last.getCause();
        }
        return last;
    }
}
