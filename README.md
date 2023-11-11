# 배달 플랫폼 백엔드 구축

![infra](/img/infra.png)

---

## 프로젝트 스펙
1. Server Framework : SpringBoot 2.7.x
2. Build Tool : Gradle Groovy
3. Language : Java 11
4. Database Library : JPA
5. Database Server : MySQL 8.x
6. RabbitMQ : 3.7

---

## 모듈 정보
1. api (API Server)
2. common (공통 모델)
3. db (DB 모듈)
4. store-admin (가맹점 Server)

---

## API 공통 Spec
```
{
    "result" : {
        "result_code" : "200",
        "result_message" : "OK",
        "result_description" : "성공",
    },
    "body" : {
        ....
        ....
    }
}
````

## 데이터베이스

![DB](/img/DB.png)

## Annotation
- @Business : 비지니스 로직
- @Converter : 객체 변환 (requset -> entity / entity -> response)
- @UserSession : 유저 세션 정보

## 프로젝트 로직
Controller -> BusinessService -> Service -> DB

## 서비스 내용

#### [일반 사용자]
- 회원가입
- 로그인 (JWT)
#### [스토어]
- 등록
- 조회
#### [스토어 메뉴]
- 등록
- 조회
#### [가맹점]
- 회원가입 (SpringSecurity)
- 로그인

#### [비동기 주문]
1. 일반 사용자 로그인 (JWT Token 생성 후 return)
2. 일반 사용자 주문 (userOrder에 저장-> Queue에 넣기)
3. 가맹점으로 주문 PUSH (Queue에서 주문정보 가져오기)

![RabbitMQ](/img/RabbitMQ.png)