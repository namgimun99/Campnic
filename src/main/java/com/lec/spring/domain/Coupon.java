package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cpNum;  // 쿠폰 일련번호

    @ManyToOne
    private User user;
//    @OneToOne(optional = false)
//    private CampReserve campReserve;
}
