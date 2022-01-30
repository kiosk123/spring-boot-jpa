# Springboot와 JPA의 활용

## 구성정보
* JPA 2.2
* java 버전 11
* bootstrap v4.3.1
* Thymeleaf 3

## 스프링 부트 프로젝트 구성하기
* [Spring Initializr 사이트 활용](https://start.spring.io/)  

## 참고사이트
 - [Spring 가이드 문서](https://spring.io/guides)
 - [타임리프 가이드 문서](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.pdf)
 - [Spring Boot 참고 문서](https://docs.spring.io/spring-boot/docs/)
 - [쿼리 파라미터 로그 남기기](https://github.com/gavlyukovskiy/spring-boot-data-source-decorator)
    - 그레이들에서 다음과 같이 설정
    - implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6' 
    - 운영에서는 사용하지 말 것



## 챕터
* 1. 프로젝트 생성
* 2. 타임리프 뷰 구성
* 3. yaml을 이용한 jpa 설정
* 4. 엔티티 매핑
* 5. 멤버 도메인 개발
* 6. 아이템 도메인 개발
* 7. 주문 도메인 개발
* 8. 뷰를 포함한 완성