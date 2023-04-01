package com.lec.spring.repository;

import com.lec.spring.domain.Camping;
import com.lec.spring.domain.Campsite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampsiteRepository extends JpaRepository<Campsite, Long> {

    List<Campsite> findByCampingId(Long id);


    List<Campsite> findByCampingIdOrderByIdDesc(Long id);

    List<Campsite> findByCampingIdOrderByIdAsc(Long id);
}