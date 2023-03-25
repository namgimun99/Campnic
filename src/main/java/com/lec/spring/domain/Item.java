package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "item")
@DynamicInsert   // insert 시 null 인 필드 제외
@DynamicUpdate   // update 시 null 인 필드 제외
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 글 id (PK)

    @Column(nullable = false) // vaildation에서 [lendername]종류로 적게함
    private String itemName;

    private int price;

    @Column(nullable = false)
    private String content;

    //FK lender_id
    @ManyToOne
    @ToString.Exclude
    private Lender lender;

    //첨부파일
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<ItemFileDTO> fileList = new ArrayList<>();

    public void addFiles(ItemFileDTO... files){
        Collections.addAll(fileList, files);
    }

    // 대여 내역
    @OneToMany(mappedBy = "item")
    @ToString.Exclude
    @Builder.Default
    private List<RentalRecipt> rentalReciptList = new ArrayList<>();

    public void addRentalRecipt(RentalRecipt rentalRecipt) {
        rentalReciptList.add(rentalRecipt);
    }

    // 대여 가능 여부 체크
    public boolean isAvailable(LocalDate sdate, LocalDate edate) {
        for (RentalRecipt rentalRecipt : rentalReciptList) {
            if (rentalRecipt.getSdate().isBefore(edate) && rentalRecipt.getEdate().isAfter(sdate)) {
                return false;
            }
        }
        return true;
    }
}
