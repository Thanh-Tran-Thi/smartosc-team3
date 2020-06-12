package com.smartosc.training.validators;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 08/06/2020 - 12:41 PM
 * @created_by Hieupv
 * @since 08/06/2020
 */
public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {
    private String field;
    private String fieldMatch;
    private String message;
    @Override
    public void initialize(ConfirmPassword constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }
    @Override
    public boolean isValid(Object str, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(str)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(str)
                .getPropertyValue(fieldMatch);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return false;
        }
    }
}
