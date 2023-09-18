package jp.co.axa.apidemo.dtos.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DepartmentConstraint.Validator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DepartmentConstraint {

    String message() default "Invalid 'department' value! Must be a non-empty string.";
    Class<?>[] groups() default {};
    Class<? extends javax.validation.Payload>[] payload() default {};

    class Validator implements ConstraintValidator<DepartmentConstraint, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && !value.isEmpty();
        }
    }
}
