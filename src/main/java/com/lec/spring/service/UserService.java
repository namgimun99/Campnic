package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Authreq;
import com.lec.spring.domain.User;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.AuthreqRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    @Autowired
    private AuthreqRepository authreqRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository;}
    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository){this.authorityRepository = authorityRepository;}


    @Autowired
    public UserService(){
        System.out.println(getClass().getName() + "()생성");
    }

    // 아이디로 회원정보 읽어오기
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    // id(PK)로 회원정보 get하기
    public User getUserById(String id){
        Long uid = Long.parseLong(id);
        User user = userRepository.findById(uid).orElse(null);

        return user;
    }

    // 특정 아이디의 회원 존재하는지 확인
    public boolean isExist(String username){
        User user = findByUsername(username);
        return (user != null) ? true : false;
    }

    // 신규 회원 등록
    public int register(User user){
        user.setUsername(user.getUsername().toUpperCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        Authority auth = authorityRepository.findByName("ROLE_MEMBER");
        user.addAuthority(auth);
        userRepository.save(user);

        return 1;
    }

    // 권한등록
//    public void registerAuthReq(List<String> auth, String username, int i){
    public void registerAuthReq(String auth, String username){
        User u = userRepository.findByUsername(username);
//        for(i = 0; i <= auth.size(); i++){
//            Authority au = authorityRepository.findByName(auth.get(i));
        Authority au = authorityRepository.findByName(auth);
        Authreq ar = new Authreq();
        ar.setUser(u);
        ar.setAuthority(au);
        authreqRepository.save(ar);     // authreq에 INSERT
//        }
    }

    // 권한등록 리스트
    public List<Authreq> getAllAuthreq(){
        List<Authreq> list = authreqRepository.findAll();
        return list;
    }

    // 권한 수락
    public void acceptAuth(String authreqId, String userId, String authId){
        User user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        Authority auth = authorityRepository.findById(Long.parseLong(authId)).orElse(null);
        user.addAuthority(auth);

        Authreq a = authreqRepository.findById(Long.parseLong(authreqId)).orElse(null);
        authreqRepository.delete(a);
    }

    // 권한 거절
    public void refuseAuth(String authreqId){
        Authreq a = authreqRepository.findById(Long.parseLong(authreqId)).orElse(null);
        authreqRepository.delete(a);
    }

    // 해당 사용자의 권한들
    public List<Authority> selectAuthoritiesById(long id){
        User user = userRepository.findById(id).orElse(null);

        if(user != null)
            return user.getAuthorities();

        return new ArrayList<>();
    }

    // 회원탈퇴
    public int deleteLoggedUser(){
        User user = U.getLoggedUser();
        userRepository.delete(user);
        return 1;
    }

}
