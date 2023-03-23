package com.lec.spring.repository;

import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void init(){
        System.out.println("init() 생성");

        //User 생성
        User user1 = User.builder()
                .username("USER1")
                .password(passwordEncoder.encode("1234"))
                .phone("01012345678")
                .name("멤버1")
                .build();

        user1 = userRepository.save(user1);

        userRepository.findAll().forEach(System.out::println);
    }
}