package com.lec.spring.controller;

import com.lec.spring.domain.City;
import com.lec.spring.service.LenderService;
import com.lec.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private LenderService lenderService;

    @Autowired
    public void setLenderService(LenderService lenderService) {
        this.lenderService = lenderService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public AdminController() {
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/city")
    public void city(Model model){
        model.addAttribute("list", lenderService.cityList());
    }

    @PostMapping("/city")
    public String cityOk(@ModelAttribute("dto") City city, Model model){
        model.addAttribute("result", lenderService.addCity(city));
        return "/admin/cityOk";
    }

    @PostMapping("/cityDel")
    public String deleteOk(long id, Model model){
        model.addAttribute("result", lenderService.delCity(id));
        return "/admin/cityDelOk";
    }

    @GetMapping("/authCheck")
    public String authcheck(Model model){
        model.addAttribute("list", userService.getAllAuthreq());
        return "/admin/authCheck";
    }

    @PostMapping("/authAccept")
    public String authAccept(String authreqId, String userId, String authId){
        userService.acceptAuth(authreqId, userId, authId);
        return "redirect:/admin/authCheck";
    }

    @PostMapping("/authRefuse")
    public String authRefuse(String authreqId){
        userService.refuseAuth(authreqId);
        return "redirect:/admin/authCheck";
    }

}
