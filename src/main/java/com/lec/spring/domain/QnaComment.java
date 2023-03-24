package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "qna_comment")
public class QnaComment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @ToString.Exclude
    private User user;   // 댓글 작성자 (FK)

    @Column(name = "qna_id")
    @JsonIgnore
    private Long qna;   // 어느글의 댓글인지 (FK)
}
