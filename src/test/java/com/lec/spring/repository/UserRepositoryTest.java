package com.lec.spring.repository;

import com.lec.spring.domain.Authority;
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
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void init(){
        System.out.println("init() 생성");

        // Authority 생성
        Authority auth_member = Authority.builder()
                .name("ROLE_MEMBER")
                .build();

        Authority auth_lender = Authority.builder()
                .name("ROLE_LENDER")
                .build();

        Authority auth_camping = Authority.builder()
                .name("ROLE_CAMPING")
                .build();

        Authority auth_admin = Authority.builder()
                .name("ROLE_ADMIN")
                .build();

        auth_member = authorityRepository.saveAndFlush(auth_member);
        auth_lender = authorityRepository.saveAndFlush(auth_lender);
        auth_camping = authorityRepository.saveAndFlush(auth_camping);
        auth_admin = authorityRepository.saveAndFlush(auth_admin);

        authorityRepository.findAll().forEach(System.out::println);

        // User 생성
        User user1 = User.builder()
                .username("USER1")
                .password(passwordEncoder.encode("user1111!"))
                .phone("01012345678")
                .name("멤버1")
                .build();

        user1 = userRepository.save(user1);

        User user2 = User.builder()
                .username("USER2")
                .password(passwordEncoder.encode("user2222!"))
                .phone("01023456789")
                .name("멤버2")
                .build();

        user2 = userRepository.save(user2);

        User user3 = User.builder()
                .username("USER3")
                .password(passwordEncoder.encode("user3333!"))
                .phone("01034567890")
                .name("멤버3")
                .build();

        user3 = userRepository.save(user3);

        user1.addAuthority(auth_member);
        user2.addAuthority(auth_member);
        user3.addAuthority(auth_member);

        User lender1 = User.builder()
                .username("LENDER1")
                .password(passwordEncoder.encode("lender11!"))
                .phone("01045678912")
                .name("렌더1")
                .build();

        lender1 = userRepository.save(lender1);

        User lender2 = User.builder()
                .username("LENDER2")
                .password(passwordEncoder.encode("lender22!"))
                .phone("01078912345")
                .name("렌더2")
                .build();

        lender2 = userRepository.save(lender2);

        User camping1 = User.builder()
                .username("CAMP1")
                .password(passwordEncoder.encode("camp1111!"))
                .phone("01056789123")
                .name("캠핑1")
                .build();

        camping1 = userRepository.save(camping1);

        User camping2 = User.builder()
                .username("CAMP2")
                .password(passwordEncoder.encode("camp2222!"))
                .phone("01089123456")
                .name("캠핑2")
                .build();

        camping2 = userRepository.save(camping2);

        User admin1 = User.builder()
                .username("ADMIN1")
                .password(passwordEncoder.encode("admin111!"))
                .phone("01067891234")
                .name("관리자1")
                .build();

        admin1 = userRepository.save(admin1);

        lender1.addAuthority(auth_lender);
        lender2.addAuthority(auth_lender);
        camping1.addAuthority(auth_camping);
        camping2.addAuthority(auth_camping);
        admin1.addAuthority(auth_admin);

        userRepository.findAll().forEach(System.out::println);
    }
}