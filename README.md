# 배달 플랫폼 백엔드 구축

![infra](/img/infra.png)

---

## 프로젝트 스펙
1. Server Framework : SpringBoot 2.7.x
2. Build Tool : Gradle Groovy
3. Language : Java 11
4. Database Library : JPA
5. Database Server : MySQL 8.x
6. rabbitmq

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

## 서비스 로직
Controller -> BusinessService -> Service -> DB

## 내용
