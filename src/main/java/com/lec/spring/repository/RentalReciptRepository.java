package com.lec.spring.repository;

import com.lec.spring.domain.RentalRecipt;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalReciptRepository extends JpaRepository<RentalRecipt, Long> {

    //User 로 찾기
    List<RentalRecipt> findByUser (User user);

    List<RentalRecipt> findByUserOrderByIdDesc(User user);
}