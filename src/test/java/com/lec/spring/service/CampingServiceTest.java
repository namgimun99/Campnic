package com.lec.spring.service;

import com.lec.spring.domain.CampReserve;
import com.lec.spring.domain.Camping;
import com.lec.spring.domain.Campsite;
import com.lec.spring.domain.City;
import com.lec.spring.repository.CampingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class CampingServiceTest {

    @Autowired
    private CampingRepository campingRepository;
    @Autowired
    private CampingService campingService;


    @Test
    public void testCampinglist() {
        List<Camping> campingList = campingService.campinglist();
        System.out.println(campingList);
    }

    @Test
    public void testCitylist() {
        List<City> cityList = campingService.citylist();
        System.out.println(cityList);
    }
    @Test
    public void testReservelist() {
        List<CampReserve> campReservesList = campingService.campReserveslist();
        System.out.println(campReservesList);
    }
//    @Test
//    public void addCampingTest() {
//        List<Camping> camping1 = campingService.addCamping();
//        System.out.println(camping1);
//    }

    @Test
    public void CampingDetailTest() {
        List<Camping> camping1 = campingService.campingDetail(1L);
        System.out.println(camping1);
    }
    @Test
    public void CampingDeleteTest() {
        int camping1 = campingService.campingDelete(1L);

    }

    @Test
    public void campsiteListTest(){
        List<Campsite> campsite1 = campingService.campsiteList(1L);
        System.out.println(campsite1);

    }

    @Test
    public void couponNum(){
        String r = campingService.couponNum();
        System.out.println(r);
    }

    @Test
    public void campingSave(){
        Camping camping1 = campingService.campingOne(18L);
        System.out.println(camping1);
    }

    @Test
    public void couponfind(){
        boolean exist = campingService.findByCoupon("00000000");

        System.out.println(exist);
    }
}