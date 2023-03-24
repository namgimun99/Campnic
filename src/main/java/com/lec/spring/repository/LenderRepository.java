package com.lec.spring.repository;

import com.lec.spring.domain.Lender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LenderRepository extends JpaRepository<Lender, Long> {
}