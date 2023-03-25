package com.lec.spring.repository;

import com.lec.spring.domain.Item;
import com.lec.spring.domain.RentaledDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentaledDateRepository extends JpaRepository<RentaledDate, Long> {
    List<RentaledDate> findByRentaledDateBetweenAndItem(LocalDate start, LocalDate end, Item item);

}