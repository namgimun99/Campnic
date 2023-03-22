package com.lec.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    public MainController() {
        System.out.println(getClass().getName() + "() 생성");
    }

    // /main
    @RequestMapping("/main")
    public void main() {}


}
