package com.lec.spring.repository;

import com.lec.spring.domain.Camping;
import com.lec.spring.domain.City;
import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class CampingRepositoryTest {

    @Autowired
    private CampingRepository campingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void init(){
        System.out.println("init() 생성");

//        User 생성
        User user1 = User.builder()
                .username("user1000")
                .password(passwordEncoder.encode("1234"))
                .phone("01012345677")
                .name("철수")
                .build();
        User user2 = User.builder()
                .username("user2000")
                .password(passwordEncoder.encode("1234"))
                .phone("01012345666")
                .name("유리")
                .build();
        User user3 = User.builder()
                .username("user3000")
                .password(passwordEncoder.encode("1234"))
                .phone("01012345555")
                .name("맹구")
                .build();

        user1 = userRepository.save(user1); // user 저장
        user2 = userRepository.save(user2); // user 저장
        user3 = userRepository.save(user3); // user 저장

        //city 생성

        City city1 = City.builder()
                .city("제천")
                .build();
        City city2 = City.builder()
                .city("풍기")
                .build();


        city1 = cityRepository.save(city1); // user 저장
        city2 = cityRepository.save(city2); // user 저장

        //camping 생성

        Camping camping1 = Camping.builder()
                .camp_name("설악산 캠핑장 1")
                .content("공기 좋고 물 좋은 설악산으로 오세요 1")
                .address("속초")
                .user(user1)
                .city(city1)
                .build();
        Camping camping2 = Camping.builder()
                .camp_name("설악산 캠핑장 2")
                .content("공기 좋고 물 좋은 설악산으로 오세요 2")
                .address("속초")
                .user(user1)
                .city(city1)
                .build();
        Camping camping3 = Camping.builder()
                .camp_name("설악산 캠핑장 3")
                .content("공기 좋고 물 좋은 설악산으로 오세요 3")
                .address("속초")
                .user(user1)
                .city(city1)
                .build();

//        Camping camping4 = Camping.builder()
//                .camp_name("설악산 캠핑장 4")
//                .content("공기 좋고 물 좋은 설악산으로 오세요 4")
//                .address(city2.getCity() + " 속초")
//                .user(user1)
//                .city(city1)
//                .build();




        camping1 = campingRepository.save(camping1);
        camping2 = campingRepository.save(camping2);
        camping3 = campingRepository.save(camping3);

        campingRepository.findAll().forEach(System.out::println);

    }

}