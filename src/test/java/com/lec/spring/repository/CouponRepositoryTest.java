package com.lec.spring.repository;

import com.lec.spring.domain.Camping;
import com.lec.spring.domain.Coupon;
import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void init(){
        System.out.println("init() 생성");

        User user1 = userRepository.findByUsername("CAMPING1");

        //User 생성
        Coupon coupon = Coupon.builder()
                .user(user1)
                .cpNum("12345678")
                .build();


        coupon = couponRepository.save(coupon);


        couponRepository.findAll().forEach(System.out::println);
    }


}