package com.lec.spring.config;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalDetails implements UserDetails {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 로그인한 사용자 정보 추출
    private User user;

    public User getUser() {
        return user;
    }

    public PrincipalDetails(User user){
        System.out.println("UserDetails(user) 생성: " + user);
        this.user = user;
    }

    // 해당 User 의 '권한(들)'을 리턴
    // 현재 로그인한 사용자의 권한정보가 필요할때마다 호출된다. 혹은 필요할때마다 직접 호출해 사용할수도 있다

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities() 호출");

        Collection<GrantedAuthority> collect = new ArrayList<>();

        List<Authority> list = userService.selectAuthoritiesById(user.getId());  // DB 에서 user 의 권한(들) 읽어오기

        for(Authority auth : list){
            collect.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return auth.getName();
                }

                // thymeleaf 등에서 활용할 문자열
                @Override
                public String toString() {
                    return auth.getName();
                }
            });
        }

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠긴건 아닌지?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 credential 이 만료 된건 아닌지?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 활성화 되었는지?
    @Override
    public boolean isEnabled() {
        return true;
        // ex)
        // 사이트에서 1년동안 회원이 로그인을 안하면 휴면계정으로 하기로 했다면?
        // 현재시간 - 로그인시간 => 1년을 초과하면 false

    }
}
