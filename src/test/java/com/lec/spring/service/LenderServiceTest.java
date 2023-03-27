package com.lec.spring.service;

import com.lec.spring.domain.City;
import com.lec.spring.domain.Lender;
import com.lec.spring.domain.User;
import com.lec.spring.repository.CityRepository;
import com.lec.spring.repository.LenderRepository;
import com.lec.spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LenderServiceTest {

    @Autowired
    private LenderService lenderService;

    @Autowired
    private LenderRepository lenderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;

    @Test
    void cityList() {
        lenderService.cityList().forEach(System.out::println);
    }

    @Test
    void addCity() {
        City city = City.builder()
                .city("고양시")
                .build();
        System.out.println(lenderService.addCity(city));
    }

    @Test
    void delCity() {
        lenderService.delCity(4L);
    }

    @Test
    void lenderUpdate() {
        Lender lender = lenderRepository.findById(3L).orElse(null);
        User user = userRepository.findById(4L).orElse(null);
        lender = lender.builder()
                .id(3L)
                .user(user)
                .address("스타필드")
                .lenderName("morefun캠프")
                .build();
        lenderService.lenderUpdate(lender, "서울시");
        System.out.println(lender);
    }

    @Test
    void deleteLender() {
        System.out.println(cityRepository.findByCity("서울시"));
    }

    @Test
    void addItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void myItemList() {
    }

    @Test
    void itemList() {
    }

    @Test
    void itemDetail() {
    }

    @Test
    void addRental() {
    }

    @Test
    void checkDays() {
    }

    @Test
    void delRental() {
    }

    @Test
    void myRental() {
    }
}