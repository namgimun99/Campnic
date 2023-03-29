package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.repository.*;
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
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RentalReciptRepository rentalReciptRepository;

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
        City city = cityRepository.findById(2L).orElse(null);
        lenderService.lenderUpdate(lender, city);
        System.out.println(lender);
    }

    @Test
    void addItem() {
        Item item = Item.builder()
                .itemName("장작")
                .price(8000000)
                .content("잘 타요")
                .build();
        Lender lender = lenderRepository.findById(2L).orElse(null);
        String lenderName = lender.getLenderName();
        lenderService.addItem(item, lenderName);
    }

    @Test
    void updateItem() {
        Lender lender = lenderRepository.findById(2L).orElse(null);
        Item item = Item.builder()
                .id(8L)
                .itemName("숯")
                .price(8000000)
                .content("잘 타요")
                .lender(lender)
                .build();

        lenderService.updateItem(item);
    }

    @Test
    void myItemList() {
        Lender lender = lenderRepository.findById(2L).orElse(null);
        String lenderName = lender.getLenderName();
        lenderService.myItemList(lenderName).forEach(System.out::println);
    }

    @Test
    void itemList() {
        lenderService.itemList().forEach(System.out::println);
    }

    @Test
    void searchItemList() {
        lenderService.searchItemList("수원시").forEach(System.out::println);
    }

}