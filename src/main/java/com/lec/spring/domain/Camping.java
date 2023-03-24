package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name= "camping")
@DynamicInsert
@DynamicUpdate
public class Camping extends BaseEntity{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @ToString.Exclude
        private User user;   // 글 작성자 (FK)

        @ManyToOne
        @ToString.Exclude
        private City city; // FK

        @Column(nullable = false, length = 20)
        private String camp_name;// 이름

        @Column(nullable = false)
        private String content;// 소개

        @Column(nullable = false)
        private String address; // 주소
}
