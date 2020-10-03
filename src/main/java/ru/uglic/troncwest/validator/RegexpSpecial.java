package ru.uglic.troncwest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {RegexpSpecialValidator.class}
)
public @interface RegexpSpecial {
    String message() default "{javax.validation.constraints.regexp-special.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long value() default 13L;

    String pattern() default ".*";
}
