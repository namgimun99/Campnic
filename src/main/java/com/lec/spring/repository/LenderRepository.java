package com.lec.spring.repository;

import com.lec.spring.domain.Lender;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LenderRepository extends JpaRepository<Lender, Long> {

    Lender findByLenderName(String lenderName);

    List<Lender> findByUserOrderByIdDesc(User user);
}