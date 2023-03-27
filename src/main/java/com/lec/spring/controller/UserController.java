package com.lec.spring.controller;

import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/login")
    public void login(){}

    @PostMapping("/loginError")
    public String loginError(){ return "user/login"; }

    @RequestMapping("/rejectAuth")
    public String rejectAuth(){
        return "common/rejectAuth";
    }

    @GetMapping("/join")
    public void join(){}

    @GetMapping("/mypage")
    public String mypage(Model model){
        model.addAttribute("user", U.getLoggedUser());
        return "/user/mypage";
    }

    @PostMapping("/adminreq")
    public String adminReq( String auth
                            , String username
                            , Model model
    ){
        userService.registerAuthReq(auth, username);
        return "redirect:mypage";
    }

    @GetMapping("/admin/authCheck")
    public String authcheck(Model model){
        model.addAttribute("list", userService.getAllAuthreq());
        return "/user/admin/authCheck";
    }

    @PostMapping("/admin/authAccept")
    public String authAccept(String authreqId, String userId, String authId){
        userService.acceptAuth(authreqId, userId, authId);
        return "redirect:/main";
    }

    @PostMapping("/admin/authRefuse")
    public String authRefuse(String authreqId){
        userService.refuseAuth(authreqId);
        return "redirect:/main";
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        binder.setValidator(new UserValidator());
//    }

}
