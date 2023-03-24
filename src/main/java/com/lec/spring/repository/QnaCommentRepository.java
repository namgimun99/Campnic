package com.lec.spring.repository;

import com.lec.spring.domain.QnaComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {

    // 특정글(qna)의 댓글들 조회
    List<QnaComment> findByQna(Long qna, Sort sort);
}