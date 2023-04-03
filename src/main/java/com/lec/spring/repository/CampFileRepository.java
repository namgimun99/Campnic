package com.lec.spring.repository;

import com.lec.spring.domain.CampFileDTO;
import com.lec.spring.domain.ItemFileDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampFileRepository extends JpaRepository<CampFileDTO, Long> {
    //특정 item(itemId)의 첨부파일들
    List<CampFileDTO> findByCampsite(Long campId);
}