# Springboot와 JPA의 활용

## 구성정보
* JPA 2.2  
* java 버전 11  
* bootstrap v4.3.1  
* Thymeleaf 3  
* Lombok  

## 스프링 부트 프로젝트 구성하기
* [Spring Initializr 사이트 활용](https://start.spring.io/)  

## 참고사이트
 - [Spring 가이드 문서](https://spring.io/guides)
 - [타임리프 가이드 문서](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.pdf)
 - [Spring Boot 참고 문서](https://docs.spring.io/spring-boot/docs/)
 - [쿼리 파라미터 로그 남기기](https://github.com/gavlyukovskiy/spring-boot-data-source-decorator)
    - 그레이들에서 다음과 같이 설정
      - `implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6'` 
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

## gradle 의존성

```gradle
plugins {
	id 'org.springframework.boot' version '2.3.5.RELEASE'
	id 'io.spring.dependency-management' version '1.0.10.RELEASE'
	id 'java'
}

group = 'springboot.jpa'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6' //운영에서는 사용하지 말 것
    implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-devtools'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}

test {
	useJUnitPlatform()
}
```

## 현재 프로젝트의 의존관계 보기 gradlew 이용
```bash
./gradlew dependencies —configuration compileClasspath
```
## EntityManager, EntityManagerFactory 주입 방법

Spring Data JPA에서는 `@Autowired`를 이용한 주입을 지원하지만 Spring Data JPA를 사용하지 않을 경우 다음과 같이 사용한다.

```java
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    //... 생략
```

Spring Boot 에서는 주입 대상을 final로 설정하고 생성자 파라미터와 주입대상 파라미터를 맞춰 주면 자동 주입된다.
```java
@Repository
public class MemberRepository {

    private final EntityManager em;
    private final EntityManagerFactory emf;

    public MemberRepository(EntityManager em, EntityManagerFactory emf) {
        this.em = em;
        this.emf = emf;
    }

    //... 생략
```

위 코드를 Lombok을 사용하면 다음과 같이 간단하게 표현된다
```java
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager em;
    private final EntityManagerFactory emf;

    //... 생략

```

## 준영속 영속시 주의 사항
준영속 방법은 두가지가 있다
- 변경 감지
```java
@Transactional
void update(Item itemParam) { // itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
    Item findItem = em.find(Item.class, itemParam.getId()); // 같은 엔티티를 조회한다.
    findItem.setPrice(itemParam.getPrice()); // 데이터를 수정한다.
}
```
- 병합사용  
  -  변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이  
변경된다. 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.) 
```java
public void save(Item item) {
    if (item.getId() == null) {
        em.persist(item);
    } else {
        em.merge(item);
    }
}

/**
 * 준영속 엔티티를 병합 후 영속성 컨텍스트에 있는 엔티티를 사용하고 싶으면 다음과 같이 사용
 * 병합 후 업데이트 된 값은 반환하는 객체에 담겨져 있다
 */
Item item = em.merge(item)
```
