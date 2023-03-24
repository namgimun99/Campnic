package com.lec.spring.repository;

import com.lec.spring.domain.Item;
import com.lec.spring.domain.Lender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 특정 lender(lenderId)의 items
    List<Item> findByLender(Lender lender);
}