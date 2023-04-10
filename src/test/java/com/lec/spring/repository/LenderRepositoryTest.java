package com.lec.spring.repository;

import com.lec.spring.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LenderRepositoryTest {

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
    public void init(){
        //User 가져오기
        User lender1 = userRepository.findByUsername("LENDER1");

        //City 생성
        City c1 = City.builder()
                .city("수원시")
                .build();

        City c2 = City.builder()
                .city("서울시")
                .build();

        City c3 = City.builder()
                .city("하남시")
                .build();

        City c4 = City.builder()
                .city("용인시")
                .build();

        c1 = cityRepository.save(c1);
        c2 = cityRepository.save(c2);
        c3 = cityRepository.save(c3);
        c4 = cityRepository.save(c4);

        //구분
        System.out.println("-------------lender------------------");

        //Lender 생성
        Lender l1 = Lender.builder()
                .lenderName("campinggogo")
                .address("장안구")
                .user(lender1)
                .city(c1)
                .build();

        Lender l2 = Lender.builder()
                .lenderName("대명캠핑")
                .address("동작구")
                .user(lender1)
                .city(c2)
                .build();

        Lender l3 = Lender.builder()
                .lenderName("fun 캠프")
                .address("스타필드")
                .user(lender1)
                .city(c3)
                .build();

        Lender l4 = Lender.builder()
                .lenderName("용용죽겠지")
                .address("처인구")
                .user(lender1)
                .city(c4)
                .build();

        l1 = lenderRepository.save(l1);
        l2 = lenderRepository.save(l2);
        l3 = lenderRepository.save(l3);
        l4 = lenderRepository.save(l4);

        lenderRepository.findAll().forEach(System.out::println);

        //구분
        System.out.println("-------------item------------------");

        //Item 생성
        Item i1 = Item.builder()
                .itemName("초강력 텐트")
                .content("인기 많아요")
                .price(1000000L)
                .lender(l1)
                .build();

        Item i2 = Item.builder()
                .itemName("스텐 세트")
                .content("별로 안썼어요")
                .price(2000000L)
                .lender(l1)
                .build();

        Item i3 = Item.builder()
                .itemName("캔들")
                .content("분위기 좋게 만들어줘요")
                .price(3000000L)
                .lender(l2)
                .build();

        Item i4 = Item.builder()
                .itemName("식기류 세트")
                .content("열전도가 좋아요")
                .price(4000000L)
                .lender(l2)
                .build();

        Item i5 = Item.builder()
                .itemName("낚시용 의자")
                .content("튼튼해요")
                .price(5000000L)
                .lender(l3)
                .build();

        Item i6 = Item.builder()
                .itemName("모기장")
                .content("여름 한정")
                .price(6000000L)
                .lender(l3)
                .build();

        i1 = itemRepository.save(i1);
        i2 = itemRepository.save(i2);
        i3 = itemRepository.save(i3);
        i4 = itemRepository.save(i4);
        i5 = itemRepository.save(i5);
        i6 = itemRepository.save(i6);

        itemRepository.findAll().forEach(System.out::println);
        //구분
        System.out.println("---------특정lender의 items-------------");
        System.out.println(itemRepository.findByLender(l1));

        //첨부파일
        ItemFileDTO file1 = ItemFileDTO.builder()
                .file("item1.jpg")
                .source("item1.jpg")
                .item(i1.getId())
                .build();
        ItemFileDTO file2 = ItemFileDTO.builder()
                .file("item1.jpg")
                .source("item1.jpg")
                .item(i1.getId())
                .build();
        ItemFileDTO file3 = ItemFileDTO.builder()
                .file("item2.jpg")
                .source("item2.jpg")
                .item(i2.getId())
                .build();
        ItemFileDTO file4 = ItemFileDTO.builder()
                .file("item2.jpg")
                .source("item2.jpg")
                .item(i2.getId())
                .build();
        ItemFileDTO file5 = ItemFileDTO.builder()
                .file("item3.jpg")
                .source("item3.jpg")
                .item(i3.getId())
                .build();
        ItemFileDTO file6 = ItemFileDTO.builder()
                .file("item3.jpg")
                .source("item3.jpg")
                .item(i3.getId())
                .build();
        ItemFileDTO file7 = ItemFileDTO.builder()
                .file("item4.jpg")
                .source("item4.jpg")
                .item(i4.getId())
                .build();
        ItemFileDTO file8 = ItemFileDTO.builder()
                .file("item4.jpg")
                .source("item4.jpg")
                .item(i4.getId())
                .build();
        ItemFileDTO file9 = ItemFileDTO.builder()
                .file("item5.jpg")
                .source("item5.jpg")
                .item(i5.getId())
                .build();
        ItemFileDTO file10 = ItemFileDTO.builder()
                .file("item5.jpg")
                .source("item5.jpg")
                .item(i5.getId())
                .build();
        ItemFileDTO file11 = ItemFileDTO.builder()
                .file("item6.jpg")
                .source("item6.jpg")
                .item(i6.getId())
                .build();
        ItemFileDTO file12 = ItemFileDTO.builder()
                .file("item6.jpg")
                .source("item6.jpg")
                .item(i6.getId())
                .build();

        file1 = fileRepository.save(file1);
        file2 = fileRepository.save(file2);
        file3 = fileRepository.save(file3);
        file4 = fileRepository.save(file4);
        file5 = fileRepository.save(file5);
        file6 = fileRepository.save(file6);
        file7 = fileRepository.save(file7);
        file8 = fileRepository.save(file8);
        file9 = fileRepository.save(file9);
        file10 = fileRepository.save(file10);
        file11 = fileRepository.save(file11);
        file12 = fileRepository.save(file12);

        fileRepository.findAll().forEach(System.out::println);

        //구분
        System.out.println("---------특정item의 files-------------");
        System.out.println(fileRepository.findByItem(i1.getId()));


    }
}