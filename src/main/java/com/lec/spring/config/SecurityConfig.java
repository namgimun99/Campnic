package com.lec.spring.config;

// WebSecurityConfigurerAdapter
// deprecated 공식 : https://docs.spring.io/spring-security/site/docs/5.7.0-M2/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.html
//    ↑ 읽어보면  WebSecurityConfigurerAdapter가 Deprecated 되었으니 SecurityFilterChain를 Bean으로 등록해서 사용하라는 말.
// 대안 공식문서 참조 : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

//  Spring Security 6 에선
// authorizeRequests() 는 deprecated 되고
// antMathers(), mvcMathcers(), regexMatchers() @EnableGlobalMethodSecurity 들은  없어졌다?
// https://stackoverflow.com/questions/74683225/updating-to-spring-security-6-0-replacing-removed-and-deprecated-functionality

// What's new Sprint Security 6
// https://docs.spring.io/spring-security/reference/whats-new.html

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // PasswordEncoder 를 bean 으로 IoC 에 등록
    // IoC 에 등록된다, IoC 내에선 '어디서든' 가져다가 사용할수 있다.
    @Bean
    public PasswordEncoder encoder(){
        System.out.println("PasswordEncoder bean 생성");
        return new BCryptPasswordEncoder();
    }

    
    // ↓ Security 를 동작 시키지 않기
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.ignoring().anyRequest();
//    }
    
    // ↓ SecurityFilterChain 을 Bean 으로 등록해서 사용

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())      // CSRF 비활성화

                .authorizeHttpRequests(auth -> auth   // AuthorizationManagerRequestMatcherRegistry

                        // delete 페이지랑 authreq 버튼 auth 걸어야 됨.
                        .requestMatchers("/camp/reserve/**").authenticated()
                        .requestMatchers("/camp/recipt/**", "/camp/reserveDelete/**").hasAnyRole("MEMBER")
                        .requestMatchers("/lender/itemRent/**", "/lender/recipts/**").hasAnyRole("MEMBER")
                        .requestMatchers("/camp/admin/camping/list/**", "/camp/admin/camping/detail/**", "/camp/admin/camping/write/**").hasAnyRole("CAMPING")
                        .requestMatchers("/camp/admin/camping/update/**","/camp/admin/camping/delete/**" ,"/camp/admin/campsite/list/**" ).hasAnyRole("CAMPING")
                        .requestMatchers("/camp/admin/campsite/write/**", "/camp/admin/campsite/detail/**", "/camp/admin/campsite/update/**", "/camp/admin/campsite/delete/**").hasAnyRole("CAMPING")
                        .requestMatchers("/lender/admin/write/**", "/lender/admin/update/**", "/lender/admin/list/**").hasAnyRole("LENDER")
                        .requestMatchers("/lender/admin/itemList/**", "/lender/admin/itemWrite/**", "/lender/admin/itemDetail/**", "/lender/admin/itemUpdate/**").hasAnyRole("LENDER")
                        .requestMatchers("/admin/authCheck/**", "/admin/city/**").hasAnyRole("ADMIN")
                        .requestMatchers("/coupon/write/**").authenticated()
                        .requestMatchers("/qna/write/**", "/qna/detail/**", "/qna/update/**", "/qna/delete/**").hasAnyRole("MEMBER", "LENDER", "CAMPING", "ADMIN")
                        .anyRequest().permitAll()
                )


                .formLogin(form -> form
                        .loginPage("/user/login")  // 로그인 필요한 상황 발생시 매개변수의 url (로그인 폼) 으로 request 발생ㄴ
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/")

                        // 로그인 성공직후 수행할코드
                        //.successHandler(AuthenticationSuccessHandler)  // 로그인 성공후 수행할 코드.
                        .successHandler(new CustomLoginSuccessHandler("/main"))

                        // 로그인 실패하면 수행할 코드
                        // .failureHandler(AuthenticationFailureHandler)
                        .failureHandler(new CustomLoginFailureHandler())
                )

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")       // 로그아웃 수행 url
                        //.logoutSuccessUrl("/login?logout")    // 로그아웃 성공후 redirect url
                        .invalidateHttpSession(false)   // session invalidate (디폴트 true)
                        // .deleteCookies("JSESSIONID")   // 쿠키 제거

                        // 로그아웃 성공후 수행할 코드
                        // .logoutSuccessHandler(LogoutSuccessHandler)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        // 권한(Authorization) 오류 발생시 수행할 코드
                        // .accessDeniedHandler(AccessDeniedHandler)
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                .build();
    }

    
    

} // end Config












