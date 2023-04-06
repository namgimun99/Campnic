package com.lec.spring.repository;

import com.lec.spring.domain.CampFileDTO;
import com.lec.spring.domain.CampReserve;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampReserveRepository extends JpaRepository<CampReserve, Long> {


    List<CampReserve> findByUser(User user);

    List<CampReserve> findByUserOrderByIdAsc(User user);

    CampReserve findByCoupon(String coupon);
}