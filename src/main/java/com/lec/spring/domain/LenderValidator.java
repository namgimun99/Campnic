package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LenderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        boolean result = Lender.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Lender lender = (Lender) target;

        Long cityId = lender.getCity().getId();
        if(cityId == null){
            errors.rejectValue("cityId", "도시를 골라주세요");
        }

        String lenderName = lender.getLenderName();
        if(lenderName == null || lenderName.isEmpty()){
            errors.rejectValue("lenderName", "사업장명은 필수입니다");
        }

        String address = lender.getAddress();
        if(address == null || address.isEmpty()){
            errors.rejectValue("address", "주소는 필수입니다");
        }
    }
}
