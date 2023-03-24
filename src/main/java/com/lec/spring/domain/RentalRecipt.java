package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "rental_recipt")
public class RentalRecipt extends BaseEntity{

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

    @Column(nullable=false)
    private Long date;
}
