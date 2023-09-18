package jp.co.axa.apidemo.dtos.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameConstraint.Validator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {

    String message() default "Invalid 'name' value! Must be a non-empty string.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<NameConstraint, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && !value.isEmpty();
        }
    }
}
