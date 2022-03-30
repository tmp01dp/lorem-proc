package com.example.lorem.proc.controller.validator;

import com.example.lorem.proc.model.ParagraphType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ParagraphTypeValidator implements ConstraintValidator<ParagraphTypeValidation, String>
{
    public boolean isValid(String s, ConstraintValidatorContext cxt) {
        try {
            ParagraphType type = ParagraphType.valueOf(s.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
