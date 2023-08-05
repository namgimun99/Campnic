# Campnic - JPA, Thymeleaf 프로젝트

## Team
**이수민**(팀장) : 
캠핑용품(유저), 캠핑용품 (관리자), 날씨api<br/>
**남기문** : 
캠핑(유저), 캠핑(관리자), 쿠폰 등록, 포인트 적립<br/>
**박찬미** : 
권한인증/인가, QnA, 로그인api, 헤더/메인, 마이페이지

> 개발기간 2023-03-20 ~ 2023-04-10

## 기술 스택 ##

에디터 : IntelliJ</br>
개발 툴 : SpringBoot 3.0.4</br>
자바 : JAVA 17</br>
빌드 : Maven 4.0</br>
서버 : localhost</br>
데이터베이스 : MySql</br>
필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security, Spring Boot DevTools, Thymeleaf, Validation 

## 개발환경 

HTML5, CSS3, JavaScript, jQuery, JSON, Ajax, BootStrap3.0.4, Java, Spring, Spring Security, Maven, JDBC, Mysql, JPA, Intellij, Apache Tomcat, lombok, Thymeleaf
	
## 프로젝트
📺 프로젝트 메인페이지
![image](https://github.com/namgimun99/Campnic/assets/124684039/1d92e361-ce2b-4a3e-ae17-544c08b40b14)


## 🔍 프로젝트 특징 및 기능
- 웹 사용자는 캠핑장 예약과 캠핑용품을 대여할 수 있고, 관리자가 되어 캠핑장과 캠핑용품점을 등록하여 사업할 수 있습니다. 최상위 관리자는 권한과 서비스지역을 관리합니다.
- Spring Framework를 이용해 더 편리하고 쉽게 개발하였습니다.
- 주요 개발 기능
    - 회원가입, 로그인, kakao 로그인
    - 지역 검색을 통해 원하는 지역의 캠핑장, 렌탈샵 찾기
    - 캠핑 예약 및 렌탈 요청
    - 예약 후 쿠폰 번호 발급 및 사용시 포인트 적립, 발급되지 않은 쿠폰번호이거나 이미 사용한 쿠폰 번호일시 X
    - Qna 작성 가능
    - 캠핑장 또는 렌탈 예약날짜가 지났을 시 취소 불가 그 외에는 취소 가능
    
  
  **일반유저**</br>
  - 캠핑장 등록이나 렌탈샵 등록을 할 수 있는 권한 요청
  
 
  **렌탈 관리자 권한을 가진 유저**</br>
  - 관리자가 만들어 놓은 지역 내 본인의 렌탈샵 등록
  - 본인의 회사 등록 후 렌탈 아이템 등록, 수정, 삭제

 
  **캠핑 관리자 권한을 가진 유저**</br>
  - 관리자가 만들어 놓은 지역 내 본인의 캠핑장 등록
  - 캠핑장 내부에 존재하는 구역 등록, 수정, 삭제


  **최상위 관리자**</br>
  - 일반유저가 관리자 권한을 요청 시 승인/거부 할 수 있음
  - 적절치 못한 Qna 삭제 가능
  - Qna 공지글 작성 시 맨 위에 나오도록 처리
  - 캠핑장이나 렌탈샵을 등록할 수 있는 지역 등록
  - 허위 캠핑장이나 렌탈샵 삭제 가능
</br>

📋 ERD 및 로직 프로세스
![image](https://github.com/namgimun99/Campnic/assets/124684039/796e2963-dc85-4cd6-814e-2d13c06a8e58)

![image](https://github.com/namgimun99/Campnic/assets/124684039/6914ed06-9bc1-48ce-b998-48f18d37d94b)

