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

Java8에서는 Optional<T> 클래스를 도입하여 NPE를 방지할 수 있도록 도와준다. 

Optional<T>
- NULL이 올 수 있는 값을 감싸는 Wrapper 클래스로, NPE가 발생하지 않도록 도와준다. 
- 모든 타입의 객체를 담을 수 있는 래퍼 클래스이다.

---

Wrapper Class 는 자바에서 사용되는 원시타입(Primitive Type)의 데이터를 서로 형 변환이 가능하도록 지원해주는 Class로써, 원시타입(Primitive Type)의 자료형들을 Class화 한것이다.

---

Annotation
1. @NoArgsConstructor : 파라미터가 없는 기본 생성자를 생성

2. @AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자 생성

3. @RequiredArgsConstructor : final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 생성

4. @Data : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor, @Value

5. @EqualsAndHashCode(callSuper = true) :
   - HashCode : 두 객체가 같은 객체인지, 동일성(identity) 를 비교하는 연산자, 두 객체 내부 필드의 값들이 같은지 판단
   - Object.equals() : 두 객체의 내용이 같은지, 동등성(equality) 를 비교하는 연산자
   - CallSuper : equals와 hashCode 메소드 자동 생성 시 부모 클래스의 필드까지 감안할지 안 할지에 대해서 설정
   - callSuper = true : 부모 클래스 필드 값들도 동일한지 체크
   - callSuper = false (기본값) : 자신 클래스의 필드 값들만 체크

---

@Retention(RetentionPolicy.RUNTIME)
````
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     */
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     */
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
````
- SOURCE : 어노테이션을 사실상 주석처럼 사용하는 것. 컴파일러가 컴파일할때 해당 어노테이션의 메모리를 버립니다.
- CLASS : 컴파일러가 컴파일에서는 어노테이션의 메모리를 가져가지만 실질적으로 런타임시에는 사라지게 됩니다. 런타임시에 사라진다는 것은 리플렉션으로 선언된 어노테이션 데이터를 가져올 수 없게 됩니다. 디폴트값입니다.
- RUNTIME : 어노테이션을 런타임시에까지 사용할 수 있습니다. JVM이 자바 바이트코드가 담긴 class 파일에서 런타임환경을 구성하고 런타임을 종료할 때까지 메모리는 살아있습니다.

---

@AliasFor(annotation = Service.class)
- @AliasFor is an annotation that is used to declare aliases for annotation attributes.
- annotation 속성에 대한 별칭을 선언하는 데 사용되는 annotation 입니다.

---

Spring Argument Resolver
- Controller에 들어오는 파라미터를 가공 하거나 (ex. 암호화 된 내용 복호화), 파라미터를 추가하거나 수정해야 하는 경우에 사용한다.

Spring Argument Resolver는 언제 실행되는가?
````
1. Client Request 요청
2. Dispatcher Servlet에서 해당 요청을 처리
3. Client Request에 대한 Handler Mapping
   3-1. RequestMapping에 대한 매칭 (RequestMappingHandlerAdapter)
   3-2. Interceptor 처리
   3-3. Argument Resolver 처리 <– 바로 여기서 우리가 지정한 Argument Resolver 실행
   3-4. Message Converter 처리
4. Controller Method invoke
````

---