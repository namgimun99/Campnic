package com.lec.spring.repository;

import com.lec.spring.domain.CampReserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampReserveRepository extends JpaRepository<CampReserve, Long> {


}