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

        City city1 = cityRepository.findByCity("서울시");
        City city2 = cityRepository.findByCity("수원시");
        City city3 = cityRepository.findByCity("하남시");
        City city4 = cityRepository.findByCity("용인시");

        //camping 생성

        Camping camping1 = Camping.builder()
                .camp_name("한강공원 난지캠핑장")
                .content("관리사무소, 개수대, 샤워실, 화장실 등 공용시설 이용가능")
                .address("마포구 한강난지로 28")
                .user(user1)
                .city(city1)
                .build();
        Camping camping2 = Camping.builder()
                .camp_name("앵봉산 캠핑장")
                .content("주차장, 화장실, 샤워장 모든 시설이 갖춰져 있어요.")
                .address("은평구 진광동 382-2")
                .user(user1)
                .city(city1)
                .build();
        Camping camping3 = Camping.builder()
                .camp_name("천왕산 캠핑장")
                .content("다랭이논 체험장, 스마트팜 센터, 도시텃밭 등 다양한 연령대가 즐길 수 있어요.")
                .address("구로구 항동")
                .user(user1)
                .city(city1)
                .build();
        Camping camping4 = Camping.builder()
                .camp_name("광교호수공원 가족캠핑장")
                .content("위치, 접근성, 깔끔한 시설, 카라반 및 각종 시설 구비가 되어 있어요.")
                .address("영통구 하동 640-5")
                .user(user1)
                .city(city2)
                .build();
        Camping camping5 = Camping.builder()
                .camp_name("싱글벙글 캠핑장&펜션")
                .content("매점, 세면장, 샤워장, 개수대, 모래놀이터, 수영장 구비.")
                .address("용인시 처인구 이동면 서리 855-1")
                .user(user1)
                .city(city4)
                .build();
        Camping camping6 = Camping.builder()
                .camp_name("검단산숲에캠핑장")
                .content("트램펄린, 산책로, 놀이시설, 물놀이, 샤워실 등 시설이 깔끔해요.")
                .address("하남시 검단산로 146번길 80")
                .user(user1)
                .city(city3)
                .build();



        camping1 = campingRepository.save(camping1);
        camping2 = campingRepository.save(camping2);
        camping3 = campingRepository.save(camping3);
        camping4 = campingRepository.save(camping4);
        camping5 = campingRepository.save(camping5);
        camping6 = campingRepository.save(camping6);


        //campsite 생성
        Campsite campsite1 = Campsite.builder()
                .number("a-1")
                .price(50000)
                .content("4인용, 바베큐 가능")
                .camping(camping1)
                .build();

        Campsite campsite2 = Campsite.builder()
                .number("a-2")
                .price(70000)
                .content("5인용, 바베큐 가능")
                .camping(camping2)
                .build();

        Campsite campsite3 = Campsite.builder()
                .number("a-3")
                .price(30000)
                .content("2인용, 바베큐 가능")
                .camping(camping3)
                .build();

        Campsite campsite4 = Campsite.builder()
                .number("b-1")
                .price(80000)
                .content("6인용, 바베큐 가능")
                .camping(camping4)
                .build();

        Campsite campsite5 = Campsite.builder()
                .number("b-2")
                .price(120000)
                .content("단체석, 바베큐 가능")
                .camping(camping5)
                .build();

        Campsite campsite6 = Campsite.builder()
                .number("b-2")
                .price(25000)
                .content("1인석, 바베큐 가능")
                .camping(camping6)
                .build();

        Campsite campsite7 = Campsite.builder()
                .number("a-1")
                .price(50000)
                .content("4인용, 바베큐 가능")
                .camping(camping6)
                .build();

        Campsite campsite8 = Campsite.builder()
                .number("a-2")
                .price(70000)
                .content("5인용, 바베큐 가능")
                .camping(camping5)
                .build();

        Campsite campsite9 = Campsite.builder()
                .number("a-3")
                .price(30000)
                .content("2인용, 바베큐 가능")
                .camping(camping4)
                .build();

        Campsite campsite10 = Campsite.builder()
                .number("b-1")
                .price(80000)
                .content("6인용, 바베큐 가능")
                .camping(camping3)
                .build();

        Campsite campsite11 = Campsite.builder()
                .number("b-2")
                .price(120000)
                .content("단체석, 바베큐 가능")
                .camping(camping2)
                .build();

        Campsite campsite12 = Campsite.builder()
                .number("b-2")
                .price(25000)
                .content("1인석, 바베큐 가능")
                .camping(camping1)
                .build();

        campsite1 = campsiteRepository.save(campsite1);
        campsite2 = campsiteRepository.save(campsite2);
        campsite3 = campsiteRepository.save(campsite3);
        campsite4 = campsiteRepository.save(campsite4);
        campsite5 = campsiteRepository.save(campsite5);
        campsite6 = campsiteRepository.save(campsite6);
        campsite7 = campsiteRepository.save(campsite7);
        campsite8 = campsiteRepository.save(campsite8);
        campsite9 = campsiteRepository.save(campsite9);
        campsite10 = campsiteRepository.save(campsite10);
        campsite11 = campsiteRepository.save(campsite11);
        campsite12 = campsiteRepository.save(campsite12);

        //      파일 생성


        CampFileDTO file1 = CampFileDTO.builder()
                .file("camp1.jpg")
                .source("camp1.jpg")
                .campsite(campsite1.getId())
                .build();
        CampFileDTO file2 = CampFileDTO.builder()
                .file("camp2.jpg")
                .source("camp2.jpg")
                .campsite(campsite2.getId())
                .build();
        CampFileDTO file3 = CampFileDTO.builder()
                .file("camp3.jpg")
                .source("camp3.jpg")
                .campsite(campsite3.getId())
                .build();
        CampFileDTO file4 = CampFileDTO.builder()
                .file("camp1.jpg")
                .source("camp1.jpg")
                .campsite(campsite4.getId())
                .build();
        CampFileDTO file5 = CampFileDTO.builder()
                .file("camp2.jpg")
                .source("camp2.jpg")
                .campsite(campsite5.getId())
                .build();
        CampFileDTO file6 = CampFileDTO.builder()
                .file("camp3.jpg")
                .source("camp3.jpg")
                .campsite(campsite6.getId())
                .build();
        CampFileDTO file7 = CampFileDTO.builder()
                .file("camp2.jpg")
                .source("camp2.jpg")
                .campsite(campsite7.getId())
                .build();
        CampFileDTO file8 = CampFileDTO.builder()
                .file("camp1.jpg")
                .source("camp1.jpg")
                .campsite(campsite8.getId())
                .build();
        CampFileDTO file9 = CampFileDTO.builder()
                .file("camp2.jpg")
                .source("camp2.jpg")
                .campsite(campsite9.getId())
                .build();
        CampFileDTO file10 = CampFileDTO.builder()
                .file("camp1.jpg")
                .source("camp1.jpg")
                .campsite(campsite10.getId())
                .build();
        CampFileDTO file11 = CampFileDTO.builder()
                .file("camp3.jpg")
                .source("camp3.jpg")
                .campsite(campsite11.getId())
                .build();
        CampFileDTO file12 = CampFileDTO.builder()
                .file("camp2.jpg")
                .source("camp2.jpg")
                .campsite(campsite12.getId())
                .build();

        file1 = campsiteFileRepository.save(file1);
        file2 = campsiteFileRepository.save(file2);
        file3 = campsiteFileRepository.save(file3);
        file4 = campsiteFileRepository.save(file4);
        file5 = campsiteFileRepository.save(file5);
        file6 = campsiteFileRepository.save(file6);
        file7 = campsiteFileRepository.save(file7);
        file8 = campsiteFileRepository.save(file8);
        file9 = campsiteFileRepository.save(file9);
        file10 = campsiteFileRepository.save(file10);
        file11 = campsiteFileRepository.save(file11);
        file12 = campsiteFileRepository.save(file12);


        //Camp_Reserve 생성

        CampReserve campReserve1 = CampReserve.builder()
                .user(user1)
                .sdate(20230326)
                .edate(20230326)
                .campsite(campsite1)
                .coupon("12345678")
                .build();

        CampReserve campReserve2 = CampReserve.builder()
                .user(user1)
                .sdate(20230326)
                .edate(20230326)
                .campsite(campsite2)
                .coupon("45678910")
                .build();

        CampReserve campReserve3 = CampReserve.builder()
                .user(user2)
                .sdate(20230326)
                .edate(20230326)
                .campsite(campsite3)
                .coupon("13572468")
                .build();

        CampReserve campReserve4 = CampReserve.builder()
                .user(user1)
                .sdate(20230326)
                .edate(20230326)
                .campsite(campsite4)
                .coupon("12411235")
                .build();

        CampReserve campReserve5 = CampReserve.builder()
                .user(user2)
                .sdate(20230326)
                .edate(20230326)
                .campsite(campsite5)
                .coupon("53467654")
                .build();

        CampReserve campReserve6 = CampReserve.builder()
                .user(user2)
                .sdate(20230326)
                .edate(20230326)
                .campsite(campsite6)
                .coupon("93983473")
                .build();

        campReserve1 = campreserveRepository.save(campReserve1);
        campReserve2 = campreserveRepository.save(campReserve2);
        campReserve3 = campreserveRepository.save(campReserve3);
        campReserve4 = campreserveRepository.save(campReserve4);
        campReserve5 = campreserveRepository.save(campReserve5);
        campReserve6 = campreserveRepository.save(campReserve6);


        campreserveRepository.findAll().forEach(System.out::println);
    }
}

