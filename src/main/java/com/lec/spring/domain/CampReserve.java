package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name= "camp_reserve")
@DynamicInsert
@DynamicUpdate
public class CampReserve extends BaseEntity{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @ToString.Exclude
        private User user;   // 글 작성자 (FK)

        @ManyToOne
        @ToString.Exclude
        private Campsite campsite; // FK

        @Column(length = 8)
        private int sdate; // 날짜

        @Column(length = 8)
        private int edate; // 날짜

        @ColumnDefault("00000000")
        @Column(length = 8)
        private String coupon;


}

