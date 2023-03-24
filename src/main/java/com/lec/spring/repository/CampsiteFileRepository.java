package com.lec.spring.repository;

import com.lec.spring.domain.CampFileDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampsiteFileRepository extends JpaRepository<CampFileDTO,Long> {
//    특정 글의 첨부파일
    List<CampFileDTO> findByCampsite(Long campsiteId);
}
