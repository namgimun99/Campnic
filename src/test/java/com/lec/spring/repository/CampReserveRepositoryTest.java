package com.lec.spring.repository;

import com.lec.spring.domain.*;
import com.lec.spring.service.CampingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CampReserveRepositoryTest {


    @Autowired
    private CampingRepository campingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CampsiteRepository campsiteRepository;

    @Autowired
    private CampReserveRepository campreserveRepository;

    @Autowired
    private CampsiteFileRepository campsiteFileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void init() {
        System.out.println("init() 생성");

//        User 생성
        User user1 = userRepository.findByUsername("CAMPING1");

        User user2 = userRepository.findByUsername("CAMPING2");



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

//      Camping camping4 = Camping.builder()
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

        //Camp_Reserve 생성

//        CampReserve campReserve1 = CampReserve.builder()
//                .user(user1)
//                .sdate(20230326)
//                .edate(20230326)
//                .campsite(campsite1)
//                .coupon("12345678")
//                .build();
//
//        CampReserve campReserve2 = CampReserve.builder()
//                .user(user1)
//                .sdate(20230326)
//                .edate(20230326)
//                .campsite(campsite2)
//                .coupon("45678910")
//                .build();
//
//        CampReserve campReserve3 = CampReserve.builder()
//                .user(user2)
//                .sdate(20230326)
//                .edate(20230326)
//                .campsite(campsite3)
//                .coupon("13572468")
//                .build();
//
//        CampReserve campReserve4 = CampReserve.builder()
//                .user(user1)
//                .sdate(20230326)
//                .edate(20230326)
//                .campsite(campsite4)
//                .coupon("12411235")
//                .build();
//
//        CampReserve campReserve5 = CampReserve.builder()
//                .user(user2)
//                .sdate(20230326)
//                .edate(20230326)
//                .campsite(campsite5)
//                .coupon("53467654")
//                .build();
//
//        CampReserve campReserve6 = CampReserve.builder()
//                .user(user2)
//                .sdate(20230326)
//                .edate(20230326)
//                .campsite(campsite6)
//                .coupon("93983473")
//                .build();
//
//        campReserve1 = campreserveRepository.save(campReserve1);
//        campReserve2 = campreserveRepository.save(campReserve2);
//        campReserve3 = campreserveRepository.save(campReserve3);
//        campReserve4 = campreserveRepository.save(campReserve4);
//        campReserve5 = campreserveRepository.save(campReserve5);
//        campReserve6 = campreserveRepository.save(campReserve6);


        campreserveRepository.findAll().forEach(System.out::println);
    }
}

