package jp.co.axa.apidemo.dtos.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SalaryConstraint.Validator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SalaryConstraint {
    String message() default "Invalid 'salary' value! Must be a positive integer.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<SalaryConstraint, Integer> {
        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value != null && value >= 0;
        }
    }
}
