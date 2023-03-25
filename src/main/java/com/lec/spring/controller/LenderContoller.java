package com.lec.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lender")
public class LenderContoller {
    public LenderContoller() {System.out.println(getClass().getName() + "() 생성");}


}
