package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Pattern;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("supports(" + clazz.getName() + ")");
        // ↓ 검증할 객체의 클래스 타입인지 확인 : WriteDTO = clazz; 가능 여부
        boolean result = User.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        String username = user.getUsername();
        if(username == null || username.trim().isEmpty()) {
            errors.rejectValue("username", "아이디는 필수입니다");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "※ 비밀번호는 필수입니다");

        String password = user.getPassword();
        String p_regex = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,}$";
        if(!Pattern.matches(p_regex, password)){
            errors.rejectValue("password", "비밀번호는 8-20자리 이상, 영문/숫자/특수문자 조합입니다.");
        }

        if(!user.getPassword().equals(user.getRe_password())){
            errors.rejectValue("re_password", "비밀번호가 같지 않습니다.");
        }


    }
}
