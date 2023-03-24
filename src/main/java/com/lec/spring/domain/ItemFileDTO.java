package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "item_file")
public class ItemFileDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FK
    @Column(name = "item_id")
    private Long item;

    @Column(nullable = false)
    private String source;    // 원본 파일명
    @Column(nullable = false)
    private String file;       // 저장된 파일명 (rename 된 파일명)

    @Transient  // <- DB 저장용 필드가 아님.
    private boolean isImage;   // 이미지 여부
}
















