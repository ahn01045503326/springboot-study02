# 배달 플랫폼 백엔드 구축

---

## 프로젝트 스펙
1. Server Framework : SpringBoot 2.7.x
2. Build Tool : Gradle Groovy
3. Language : Java 11
4. Database Library : JPA
5. Database Server : MySQL 8.x

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