package com.lec.spring.repository;

import com.lec.spring.domain.Item;
import com.lec.spring.domain.RentalRecipt;
import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RentalReciptRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LenderRepository lenderRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private  ItemRepository itemRepository;

    @Autowired ItemFileRepository fileRepository;

    @Autowired RentalReciptRepository rentalReciptRepository;

    @Test
    public void test(){

        User user1 = userRepository.findByUsername("USER1");
        User user2 = userRepository.findByUsername("USER2");
        User user3 = userRepository.findByUsername("USER3");

        Item i1 = itemRepository.findByItemName("초강력 텐트");
        Item i2 = itemRepository.findByItemName("스텐 세트");
        Item i3 = itemRepository.findByItemName("캔들");
        Item i4 = itemRepository.findByItemName("식기류 세트");
        Item i5 = itemRepository.findByItemName("낚시용 의자");
        Item i6 = itemRepository.findByItemName("모기장");

        // RentalRecipt
        RentalRecipt r1 = RentalRecipt.builder()
                .sdate(LocalDate.of(2023, 3, 1))
                .edate(LocalDate.of(2023, 3, 5))
                .quantity(5)
                .item(i1)
                .user(user1)
                .build();

        RentalRecipt r2 = RentalRecipt.builder()
                .sdate(LocalDate.of(2023, 3, 4))
                .edate(LocalDate.of(2023, 3, 8))
                .quantity(5)
                .item(i2)
                .user(user2)
                .build();

        RentalRecipt r3 = RentalRecipt.builder()
                .sdate(LocalDate.now())
                .edate(LocalDate.now().plusDays(1))
                .quantity(1)
                .item(i3)
                .user(user3)
                .build();

        RentalRecipt r4 = RentalRecipt.builder()
                .sdate(LocalDate.now())
                .edate(LocalDate.now().plusDays(1))
                .quantity(1)
                .item(i4)
                .user(user1)
                .build();

        RentalRecipt r5 = RentalRecipt.builder()
                .sdate(LocalDate.now())
                .edate(LocalDate.now().plusDays(1))
                .quantity(1)
                .item(i5)
                .user(user2)
                .build();

        RentalRecipt r6 = RentalRecipt.builder()
                .sdate(LocalDate.now())
                .edate(LocalDate.now().plusDays(1))
                .quantity(1)
                .item(i6)
                .user(user3)
                .build();

        r1 = rentalReciptRepository.save(r1);
        r2 = rentalReciptRepository.save(r2);
        r3 = rentalReciptRepository.save(r3);
        r4 = rentalReciptRepository.save(r4);
        r5 = rentalReciptRepository.save(r5);
        r6 = rentalReciptRepository.save(r6);

        rentalReciptRepository.findAll().forEach(System.out::println);

        rentalReciptRepository.findByUser(user1).forEach(System.out::println);
        rentalReciptRepository.findByUser(user2).forEach(System.out::println);
        rentalReciptRepository.findByUser(user3).forEach(System.out::println);

    }

}