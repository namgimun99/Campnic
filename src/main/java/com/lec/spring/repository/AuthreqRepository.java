package com.lec.spring.repository;

import com.lec.spring.domain.Authreq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthreqRepository extends JpaRepository<Authreq, Long> {
}