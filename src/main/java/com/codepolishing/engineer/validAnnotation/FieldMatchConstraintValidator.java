package com.codepolishing.engineer.validAnnotation;

import org.apache.commons.beanutils.BeanUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {

   private String firstFieldName;
   private String secondFieldName;
   private String message;

   public void initialize(FieldMatch constraint) {
      firstFieldName = constraint.first();
      secondFieldName = constraint.second();
      message = constraint.message();
   }

   public boolean isValid(final Object value, ConstraintValidatorContext context) {

      boolean valid = true;

      try{
         final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
         final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

         valid = firstObj.equals(secondObj);
      } catch (Exception e) {

      }
      if (!valid){
         context.buildConstraintViolationWithTemplate(message)
                 .addPropertyNode(firstFieldName)
                 .addConstraintViolation()
                 .disableDefaultConstraintViolation();
      }

      return valid;

   }
}
