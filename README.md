# Leisurvation


## 프로젝트 소개
웹 사용자는 캠핑장 예약과 캠핑용품을 대여할 수 있고, 관리자가 되어 캠핑장과 캠핑용품점을 등록하여 사업할 수 있습니다. 최상위 관리자는 권한과 서비스지역을 관리합니다.
> 개발기간 2023-03-20 ~ 2023-04-10


## 멤버 구성
**이수민**(팀장)
캠핑용품(유저), 캠핑용품 (관리자), 날씨api<br/>
**남기문**
캠핑(유저), 캠핑(관리자), 쿠폰<br/>
**박찬미**
권한인증/인가, QnA, 로그인api, 헤더/메인, 마이페이지

## 개발환경 

HTML5, CSS3, JavaScript, jQuery, JSON, Ajax, BootStrap4.0, Java, Spring, Spring Security, Maven, JDBC, Mysql, JPA, Intellij, Apache Tomcat, lombok, Thymeleaf
	


## 시작하기 

 - [ ] db 설정 

> mysql -u root -p 1234<br/>
    create database campdb;<br/>
    create user 'campuser'@'%' identified by '1234';<br/>
    grant all privileges on campdb.*to'campuser'@'%';<br/>
    flush privileges;<br/>
    quit<br/>
    mysql -u campuser -p campdb 1234<br/>
    use campdb;
