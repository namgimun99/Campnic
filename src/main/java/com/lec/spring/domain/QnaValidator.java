package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class QnaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        boolean result = Qna.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Qna qna = (Qna) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"subject","제목은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"content","내용입력은 필수입니다.");

    }
}
