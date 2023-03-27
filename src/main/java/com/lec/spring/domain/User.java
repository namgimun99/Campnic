package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "db_user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;// 회원 비밀번호

    @Transient   // DB 에 반영안함
    @ToString.Exclude
    @JsonIgnore
    private String re_password;  // 비밀번호 확인 입력

    @Column(nullable = false)
    private String name; // 회원 이름

    @Column(nullable = false)
    private String phone;

    @ColumnDefault(value = "0")
    private int point;

    @Transient
    private String provider;

    // 권한
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Authority> authorities = new ArrayList<>();

    public void addAuthority(Authority...authorities){      // xxxToMany 의  경우 만들어두면 편리해서 작성함
        Collections.addAll(this.authorities, authorities);
    }
}
