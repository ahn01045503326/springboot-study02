"2023-09-21T00:20:12.679516" -> ISO8601

---

java : int -> mysql : int
java : long , double -> mysql : bigint

---

Argument Resolver
- Controller로 들어온 파라미터를 가공하거나 수정 기능을 제공하는 객체이다.

동작방식
1. Client Request 요청
2. Dispatcher Servlet에서 해당 요청 처리
3. Client Request에 대한 Handler Mapping
   - 3-1. RequestMapping에 대한 매칭 (RequestMappingHandlerAdapter가 수행)
   - 3-2. Interceptor 처리 
   - 3-3. Argument Resolver 처리 <-- Argument Resolver 실행 지점 
   - 3-4. Message Converter 처리
4. Controller Method invoke

ModelResolver(objectMapper) : 카멜케이스를 스네이크케이스로 변경

---

ContentCachingRequestWrapper : body 데이터를 캐싱해놓을 수 있다.

---

asIterator() 메서드 : 이 열거에 포함 된 요소들을 순회 하는 Iterator를 리턴합니다.

forEachRemaining(Consumer<? super E> action) : 모든 원소에 대해서 주어진 작업을 수행한다, 예외가 발생하기 전까지

---