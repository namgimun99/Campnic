package com.lec.spring.repository;

import com.lec.spring.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CampsiteRepositoryTest {

    @Autowired
    private CampingRepository campingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CampsiteRepository campsiteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CampsiteFileRepository campsiteFileRepository;

    @Test
    public void init(){
        System.out.println("init() 생성");

//        User 생성
        User user1 = userRepository.findByUsername("Camping1");

        User user2 = userRepository.findByUsername("Camping2");
        //city 생성

        City city1 = cityRepository.findByCity("제천");

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



        //campsite 생성
        Campsite campsite1 = Campsite.builder()
                .number("a-1")
                .price(50000)
                .content("4인용, 바베큐 가능")
                .camping(camping1)
                .build();

        Campsite campsite2 = Campsite.builder()
                .number("a-2")
                .price(50000)
                .content("4인용, 바베큐 가능")
                .camping(camping1)
                .build();

        Campsite campsite3 = Campsite.builder()
                .number("a-3")
                .price(50000)
                .content("4인용, 바베큐 가능")
                .camping(camping1)
                .build();

        Campsite campsite4 = Campsite.builder()
                .number("b-1")
                .price(60000)
                .content("6인용, 바베큐 가능")
                .camping(camping2)
                .build();

        Campsite campsite5 = Campsite.builder()
                .number("b-2")
                .price(60000)
                .content("6인용, 바베큐 가능")
                .camping(camping2)
                .build();

        Campsite campsite6 = Campsite.builder()
                .number("b-2")
                .price(60000)
                .content("6인용, 바베큐 가능")
                .camping(camping2)
                .build();

        campsite1 = campsiteRepository.save(campsite1);
        campsite2 = campsiteRepository.save(campsite2);
        campsite3 = campsiteRepository.save(campsite3);
        campsite4 = campsiteRepository.save(campsite4);
        campsite5 = campsiteRepository.save(campsite5);
        campsite6 = campsiteRepository.save(campsite6);

//      파일 생성


        CampFileDTO file1 = CampFileDTO.builder()
                .file("face01.png")
                .source("face01.png")
                .campsite(campsite1.getId())
                .build();
        CampFileDTO file2 = CampFileDTO.builder()
                .file("face02.png")
                .source("face02.png")
                .campsite(campsite2.getId())
                .build();
        CampFileDTO file3 = CampFileDTO.builder()
                .file("face03.png")
                .source("face03.png")
                .campsite(campsite3.getId())
                .build();
        CampFileDTO file4 = CampFileDTO.builder()
                .file("face04.png")
                .source("face04.png")
                .campsite(campsite4.getId())
                .build();
        CampFileDTO file5 = CampFileDTO.builder()
                .file("face05.png")
                .source("face05.png")
                .campsite(campsite5.getId())
                .build();

        file1 = campsiteFileRepository.save(file1);
        file2 = campsiteFileRepository.save(file2);
        file3 = campsiteFileRepository.save(file3);
        file4 = campsiteFileRepository.save(file4);
        file5 = campsiteFileRepository.save(file5);

        campsiteFileRepository.findAll().forEach(System.out::println);
    }

}