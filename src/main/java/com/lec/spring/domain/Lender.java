package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "lender")
@DynamicInsert   // null인 필드 제외
@DynamicUpdate   // 변경된 필드만
public class Lender extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String lenderName;

    // FK user_id
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private User user;

    // FK city_id
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private City city;

    //내가 등록한 Items
    @OneToMany(mappedBy = "lender", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Item> itemList = new ArrayList<>();

    public void addItems(Item... items){
        Collections.addAll(itemList, items);
    }

    //TODO cascade
}
