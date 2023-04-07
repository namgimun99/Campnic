package com.lec.spring.repository;

import com.lec.spring.domain.Camping;
import com.lec.spring.domain.City;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampingRepository extends JpaRepository<Camping, Long> {

    List<Camping> findByUserOrderByIdAsc(User user);


    List<Camping> findByCity(City city);
}