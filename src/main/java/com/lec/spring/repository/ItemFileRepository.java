package com.lec.spring.repository;

import com.lec.spring.domain.ItemFileDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemFileRepository extends JpaRepository<ItemFileDTO, Long> {
    //특정 item(itemId)의 첨부파일들
    List<ItemFileDTO> findByItem(Long itemId);
}