package com.lec.spring.repository;

import com.lec.spring.domain.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 username 으로 조회
    User findByUsername(String username);

    // 회원 탈퇴
    void deleteById(Id id);

}