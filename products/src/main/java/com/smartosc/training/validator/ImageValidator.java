package com.smartosc.training.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 09/06/2020 - 9:53 AM
 */
public class ImageValidator  implements ConstraintValidator<ImageConstraint, String> {
    @Override
    public void initialize(ImageConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String fileName, ConstraintValidatorContext constraintValidatorContext) {
        List<String> last = new ArrayList<>();
        last.add("png");
        last.add("jpg");
        last.add("jpeg");

        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            fileName.substring(fileName.lastIndexOf(".")+1);

        for (String s: last) {
            if(fileName.substring(fileName.lastIndexOf(".")+1).equals(s)){
                return true;
            }
        }
        return false;
    }


}
