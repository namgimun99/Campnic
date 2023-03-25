package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "reserved_date")
@DynamicInsert   // insert 시 null 인 필드 제외
@DynamicUpdate   // update 시 null 인 필드 제외
public class reservedDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservedDate")
    private LocalDate reservedDate;

    @ManyToOne
    @ToString.Exclude
    private Campsite campsite;
}
