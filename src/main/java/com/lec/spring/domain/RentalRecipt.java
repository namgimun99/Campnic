package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "rental_recipt")
public class RentalRecipt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FK user_id
    @ManyToOne
    @ToString.Exclude
    private User user;

    //FK item_id
    @ManyToOne
    @ToString.Exclude
    private Item item;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "sdate", nullable = false)
    private LocalDate sdate;

    @Column(name = "edate", nullable = false)
    private LocalDate edate;
}
