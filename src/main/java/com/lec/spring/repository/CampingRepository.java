package com.lec.spring.repository;

import com.lec.spring.domain.Camping;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampingRepository extends JpaRepository<Camping, Long> {



}