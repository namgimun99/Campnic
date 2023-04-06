package com.lec.spring.controller;

import com.lec.spring.domain.Weather;
import com.lec.spring.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private WeatherService weatherService;

    public MainController() {
        System.out.println(getClass().getName() + "() 생성");
    }

    // /main
    @RequestMapping("/main")
    public void main() {}

    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth() { // org.springframework.security.core.Authentication
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // 날씨
    @GetMapping("/weather")
    @ResponseBody
    public Weather getWeather() {
        return weatherService.getWeather("Seoul");
    }
}
