package com.codepolishing.engineer.validAnnotation;

import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {

   @Autowired
   UserRepository userRepository;

   public void initialize(UniqueEmail constraint) { }

   public boolean isValid(String obj, ConstraintValidatorContext context) {

      if(userRepository == null)
         return true;

      if(obj != null)
      {
         if(userRepository.findByEmail(obj) == null)
         {
            return true;
         }
      }
      return false;
   }
}
