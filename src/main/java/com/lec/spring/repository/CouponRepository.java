package com.lec.spring.repository;

import com.lec.spring.domain.Coupon;
import com.lec.spring.domain.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // 쿠폰일련번호로 조회 -> '이미 사용한 쿠폰입니다' 사용 목적
    Coupon findByCp_sno(String cp_sno);

    // user_id(FK)로 조회
    User findByUserId(User user);
}