package com.smartosc.training.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 08/06/2020 - 12:40 PM
 * @created_by Hieupv
 * @since 08/06/2020
 */
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {

    String field();
    String fieldMatch();
    String message() default "fields value dont match";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        ConfirmPassword[] value();
    }
}
