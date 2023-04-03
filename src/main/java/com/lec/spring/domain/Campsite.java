package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name= "campsite")
@DynamicInsert
@DynamicUpdate
public class Campsite{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @ToString.Exclude
        private Camping camping;   // FK


        @Column(nullable = false)
        private String number;// 구역 번호

        @Column
        private int price; // 가격

        @Column(nullable = false)
        private String content;// 소개

        @OneToMany(mappedBy = "campsite", cascade = CascadeType.ALL)
        @ToString.Exclude
        @Builder.Default
        private List<CampReserve> campReserveList = new ArrayList<>();

        public void addItems(CampReserve... campReserve){
                Collections.addAll(campReserveList, campReserve);
        }

        @OneToMany(mappedBy = "campsite", cascade = CascadeType.ALL)
        @ToString.Exclude
        @Builder.Default
        private List<CampFileDTO> fileList = new ArrayList<>();

        public void addFiles(CampFileDTO... files){
                Collections.addAll(fileList, files);
        }



}
