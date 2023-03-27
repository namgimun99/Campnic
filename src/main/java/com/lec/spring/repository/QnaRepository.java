package com.lec.spring.repository;

import com.lec.spring.domain.Qna;
import com.lec.spring.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    // 페이징
//    List<Qna> selectFromRow(int from, int rows);

    List<Qna> findAllByUserId(Long id);

}