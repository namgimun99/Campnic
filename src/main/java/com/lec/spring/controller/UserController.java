package com.lec.spring.controller;

import com.lec.spring.domain.City;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserValidator;
import com.lec.spring.service.LenderService;
import com.lec.spring.service.QnaService;
import com.lec.spring.service.UserService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private LenderService lenderService;
    private QnaService qnaService;

    @Autowired
    public void setQnaService(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @Autowired
    public void setLenderService(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/login")
    public void login(){}

    @PostMapping("/apiLogin")
    public String apiLogin(String id, Model model){
        model.addAttribute("username", id);

        if(userService.isExist(id)){
            return "/user/apiLogin";
        }else{
            return "/user/apiJoin";
        }
    }

    @PostMapping("/loginError")
    public String loginError(){ return "user/login"; }

    @RequestMapping("/rejectAuth")
    public String rejectAuth(){
        return "common/rejectAuth";
    }

    @GetMapping("/join")
    public void join(){}

    @PostMapping("/join")
    public String joinOk(@Valid User user
            , BindingResult result   // UserValidagtor 가 유효성 검증한 결과가 담긴 객체
            , Model model
            , RedirectAttributes redirectAttrs
    ){
        // 이미 등록된 중복된 아이디(username) 이 들어오면
        if(!result.hasFieldErrors("username") && userService.isExist(user.getUsername())){
            result.rejectValue("username", "이미 존재하는 아이디 입니다");
        }

        // 검증 에러가 있었다면 redirect 한다
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("username", user.getUsername());
            redirectAttrs.addFlashAttribute("password", user.getPassword());
            redirectAttrs.addFlashAttribute("name", user.getName());
            redirectAttrs.addFlashAttribute("phone", user.getPhone());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }

            return "redirect:/user/join";
        }
        // valid 통과되면 수행
        int cnt = userService.register(user);

        // api로그인이라면 바로 로그인까지 실행
        if (user.getProvider().equals("api")) {
            model.addAttribute("username", user.getUsername());
            System.out.println("----------------------------");
            System.out.println(user.getUsername());
            System.out.println("----------------------------");
            return "/user/apiLogin";
        }

        // 에러 없었으면 회원 등록 진행
        model.addAttribute("result", cnt);
        return "/user/joinOk";
    }

    @GetMapping("/mypage")
    public void mypage(){}


    @PostMapping("/adminreq")
    public String adminReq( String auth
                            , String username
                            , Model model
    ){
        userService.registerAuthReq(auth, username);
        return "redirect:mypage";
    }

    @PostMapping("/delete")
    public String deleteOk(Model model){
        model.addAttribute("result", userService.deleteLoggedUser());
        return "user/deleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }

}
