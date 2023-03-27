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
@Entity(name = "rentaled_date")
@DynamicInsert   // insert 시 null 인 필드 제외
@DynamicUpdate   // update 시 null 인 필드 제외
public class RentaledDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 글 id (PK)

    @Column(name = "rentaleddate")
    private LocalDate rentaledDate;

    // FK item_id
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Item item;
}
