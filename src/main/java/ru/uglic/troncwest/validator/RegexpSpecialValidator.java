package ru.uglic.troncwest.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpSpecialValidator implements ConstraintValidator<RegexpSpecial, String> {
    private static final Logger logger = LoggerFactory.getLogger(RegexpSpecialValidator.class);
    private RegexpSpecial annotation;
    private Pattern pattern;

    @Override
    public void initialize(RegexpSpecial constraintAnnotation) {
        if (this.annotation != null) {
            logger.error("Multiple call of initialize");
        }
        annotation = constraintAnnotation;
        pattern = Pattern.compile(constraintAnnotation.pattern());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("1. defaultConstraintMessageTemplate: {}", context.getDefaultConstraintMessageTemplate());
        logger.info("2. clockProvider {}", context.getClockProvider());
        if (value == null) {
            return true;
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
