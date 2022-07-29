# 스프링 공부
참고
[https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8/lecture/55340?tab=note&volume=1.00](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8/lecture/55340?tab=note&volume=1.00)

# SOLID 원칙
- SRP: 단일 책임 원칙(single responsibility principle)
MemberService는 멤버를 생성하는 실행만 하는 역할만하고 AppConfig는 구현 객체를 생성하고 연결하는 책임을 갖는다.


- OCP: 개방-폐쇄 원칙 (Open/closed principle)
소프트웨어는 변경에는 닫혀있고 확장에는 열려있다. App을 사용 영역과 구성 영역으로 나눠 클라이언트 코드의 변경 없이 AppConfig의 FixDiscountPolicy에서 RateDiscountPolicy로 변경해 클라이언트에 주입하였다. 그래서 소프트웨어 요소를 새로 확장해도 사용 영역의 변경은 닫혀있다.

- LSP: 리스코프 치환 원칙 (Liskov substitution principle)


- ISP: 인터페이스 분리 원칙 (Interface segregation principle)


- DIP: 의존관계 역전 원칙 (Dependency inversion principle)
프로그래머는 추상화에만 의존해야 한다. 클라이언트 코드는 DiscountPolicy 추상화 인터페이스에만 의존하도록 하였다. 하지만 추상화 인터페이스로는 실행할 수 없기 때문에 AppConfig가 대신 구현 클래스를 생성해 클라이언트 코드에 의존관계를 주입했다.

# IoC
제어의 역전. 기존의 코드는 구현 객체가 직접 구현 객체를 생성하고 연결하고 실행하였다. 구현 객체가 프로그램의 제어 흐름을 스스로 조종한 것이다.
반면에 AppConfig를 활용해 클라이언트 구현 객체의 생성하고 연결하는 것을 자기 자신이 아닌 외부에서 제어하게 하였다. 이것을 제어의 역전이라고 한다.

## 프레임워크 vs 라이브러리
프레임워크는 자신이 코드는 직접 작성하지만 코드를 제어하고 실행하는 권한은 넘기는 것이다.
반면에 라이브러리는 자신이 코드도 작성하고 제어의 흐름도 자신이 담당한다.

# DI
의존관계 주입. OrderServiceImpl은 어떤 객체가 주입될지 알 수 없다.
- 정적인 의존관계는 프로그램을 실행하지 않아도 의존관계를 알 수 있다.
- 동적인 객체 인스턴스 의존관계는 App 실행 시점에 인스턴스 참조가 연결된 의존관계이다.
- App 실행 시점에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 
클라이언트의 서버의 실제 의존관계가 연결되는 것을 의존관계 주입이라고 한다.
- DI를 사용하면 클라이언트 코드를 변경하지 않고 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
- DI를 사용하면 정적인 클래스 의존관계를 변경하지 않고 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.

# Ioc 컨테이너, DI 컨테이너
AppConfig 처럼 의존관계 주입을 해주는 것을 IoC 컨테이너 또는 DI 컨테이너(최근 주로 사용.)라고 한다.
또는 어셈블러, 오브젝트 팩토리 등으로 불리기도 한다.

# Spring
- ApplicationContext : 스프링 컨테이너. 인터페이스.
- @Configuration : 구성정보
- @Bean : @Bean이 붙은 메서드의 명을 key로 리턴값을 value로 스프링 컨테이너에 등록한다. 이렇게 등록된 Bean을 스프링빈이라고 한다.
- 이제 스프링 컨테이너에서 필요한 스프링 빈을 찾아야 한다. (ac.getBean())

# ApplicationContext
인터페이스. XML 기반으로 만들 수 있고 어노테이션 기반으로도 만들 수 있다. (임의로 만들 수도 있다.)
어노테이션 기반으로 구현하려면 아래와 같다.
```java
ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
```
원래는 BeanFactory, ApplicationContext를 구분해서 이야기하기도 하는데 
BeanFactory를 직접 쓰는 경우는 많이 없으므로 일반적으로 ApplicationContext를 스프링 컨테이너라고 한다.


# Bean
빈 이름은 메서드명. 빈 이름을 직접 지정할 수도 있다. 빈 이름이 중복된다면 문제가 발생하기 때문에 조심.

# 스프링 컨테이너를 생성한다면..
1. 스프링 컨테이너 생성
2. 스프링 컨테이너 생성 내 스프링 빈 저장소에 빈 이름 - 빈 객체 구조로 저장.
3. 스프링 빈 의존관계 설정 : 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입한다.

# GenericXmlApplicationContext
XML을 사용하면 컴파일 없이 빈 설정 정보를 변경할 수 있다.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="memberService" class="hello.core.member.MemberServiceImpl" >
        <constructor-arg name="memberRepository" ref="memberRepository" />
    </bean>

    <bean id="memberRepository" class="hello.core.member.MemoryMemberRepository"/>

    <bean id="orderService" class="hello.core.order.OrderServiceImpl" >
        <constructor-arg name="memberRepository" ref="memberRepository" />
        <constructor-arg name="discountPolicy" ref="discountPolicy" />
    </bean>

    <bean id="discountPolicy" class="hello.core.discount.RateDiscountPolicy" >
    </bean>
</beans>
```
AppConfig.java와 거의 유사함을 알 수 있다.

# BeanDefinition
Xml을 이용해서 bean 설정을 하나 class를 이용해서 하나 ApplicationContext에서 빈 설정 정보를 읽을 수 있는 것은
ApplicationContext가 BeanDefinition을 이용해 빈 설정 정보를 읽기 때문이다.
- BeanDefinition에 있는 정보
  - BeanClassName : 생성할 빈의 클래스 명 (java를 이용할 때처럼 팩토리 역할 빈을 사용하면 없음.)
  - factoryBeanName : 팩토리 역할의 빈을 사용할 결우 이름 (appConfig)
  - factoryMethodName : 빈을 생성할 팩토리 메서드 지정 (memberService)
  - Scope : 싱글톤(기본값)
  - lazyInit : 스프링 컨테이너를 생설할 때 빈을 생성하는 것이 아니라 실제 빈을 사용할 때까지 최대한 생성을 지연처리 하는지 여부
  - InitMehodName : 빈을 생성하고 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명
  - DestroyMethodName : 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명
  - Constructor argument, Properties : 의존관계 주입에서 사용. (java를 이용할 때처럼 팩토리 역할 빈을 사용하면 없음.)

# 자동 의존관계 주입
@ComponentScan을 사용해 @Component가 붙은 스프링빈을 모두 스프링 컨테이너에 등록한다.
```java
@Configuration
@ComponentScan(
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

	
}
```
위와 같이 @Component.Filter를 쓸 수 있다. @Configuration은 @Component도 갖고 있기 때문에 컴포넌트 스캔 대상에 포함된다.
그래서 제외 필터 조건을 넣어 자동 DI 대상에서 제외한다.


## 의존관계가 있을 때
생성자에 @Autowired를 넣으면 자동으로 스프링 컨테이너가 스프링 빈을 찾아서(타입으로 찾는다.) 주입한다.
```java
@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

# 탐색 위치와 기본 스캔 대상
basePackage를 지정해 탐색 시작 위치를 지정할 수 있다.
basePackage =  {"hello.core", "hello.sub"} 처럼 여러개를 넣을 수도 있다.
```java
@ComponentScan(
		basePackage =  "hello.core",
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
```
지정안하면 해당 클래스(@ComponentScan가 붙은 class)가 있는 위치 부터 시작한다.
스프링부트는 @SpringbootApplication에 @ComponentScan이 있기 때문에 이 class가 시작 위치가 된다.

## 기본 스캔 대상
- @Component
- @Controller
- @Service
- @Repository
- @Configuration
원래 어노테이션은 상속관계가 없는데 이건 스프링 기능이다.
스프링의 부가 기능을 수행한다.
- @Controller : 스프링 MVC 컨트롤러로 인식
- @Repository : 스프링 데이터 접근 계층으로 인식하고 데이터 계층의 예외를 스프링 예외로 인식한다.

# 다양한 의존관계 주입
## 생성자 주입
- 생성자를 통해 의존 관계를 주입. 그래서 빈 생성 단계에서 일어남.
- 생성자 호출 시점에 딱 1번만 호출되는 것이 보장됨. 필수, 불변 의존관계에 사용.
- 생성자가 1개면 @Autowired를 생략해도 된다.

## 수정자 주입
- setter를 이용해 의존 관계를 주입. (자바 빈 프로퍼티 규약에 의해 setXX()를 이용해 필드를 수정하는 메서드)
- @Autowired를 넣어야 의존 관계를 주입한다.
- 빈 생성이 일어난 후 의존관계 주입 단계에서 일어남.
- 선택적(@Autowired(required=false)처럼 사용.), 변경 가능성이 있는 객체에 대해 사용함.

## 필드 주입
- 말 그대로 필드에 바로 주입하는 방법.
- 코드가 간결해서 개발자들을 유혹하지만 외부에서 변경이 불가능해 테스트 코드를 짜기 힘들어진다. (setter를 여는 방법 밖에 없다.)
- DI 프레임워크가 없으면 아무것도 할 수 없다.
- 애플리케이션 코드가 아닌 테스트 코드에서 사용할 수도 있다.

## 일반 메서드 주입
- 수정자 주입과 같이 빈 생성이 일어난 후 의존 관게를 주입한다.
- 일반적으로 잘 사용하지 않는다.

# 옵션 처리
- @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드가 아예 호출도 안된다.
- @Nullable인 경우 스프링빈이 아닌 경우에 null이 들어간다.
- Optional<Member>인 경우 스프링빈이 아닌 경우에 Optional.empty가 들어간다.

# 생성자 주입을 선택해라
- 대부분의 의존관계 주입은 애플리케이션 종료 시점까지 의존관계를 변경할 일이 없다.
- 수정자 주입을 사용하면 누군가 변경할 수 있다.
- 불변하게 설계가 가능하다.
- 생성자를 이미 지정해주었기 때문에 테스트 시 생성자에 파람을 안넣으면 컴파일 오류가 나 개발자의 실수를 방지 할 수 있다.
- 필드에 final을 쓸 수 있다. 실수로 initialize를 안했을 시 알 수 있다.
- 가끔 옵션으로 부여하고 싶은 빈이 있으면 수정자 주입 방식을 사용하면 된다.

# 롬복과 최신 트랜드
- @RequiredArgsConstructor를 사용하면 final이 붙은 필드에 대해 생성자를 만들어준다.
```java
// OrderServiceImpl.java
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
}
```

# @Autowired
## @Autowired
여러 빈이 있으면 필드이름, 파라미터 이름으로 빈 이름을 추가 매칭한다.
예를 들면 DiscountPolicy에 RateDiscountPolicy와 FixDiscountPolicy를 매칭하면 오류가 날 것이다.
```java
// OrderServiceImpl.java
public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
	this.memberRepository = memberRepository;
	this.discountPolicy = rateDiscountPolicy;
}
//이것도 된다.
@Autowired
private final DiscountPolicy discountPolicy;
```
DiscountPolicy 파라미터 이름을 rateDiscountPolicy으로 주면 알아서 RateDiscountPolicy를 매칭한다.
그러면 에러가 해결된다.

그래서 @Autowired는
1. 타입으로 매칭한다.
2. 타입 매칭 결과가 2개 이상 일때는 필드이름, 파라미터 이름으로 빈 이름을 추가 매칭한다.

## @Qualifier
추가적인 구분자를 붙여주는 방식이다.
```java
// RateDiscountPolicy.java
@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy{
}

// OrderServiceImpl.java
public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
	this.memberRepository = memberRepository;
	this.discountPolicy = discountPolicy;
}
```
@Qualifier는
1. @Qualifier 끼리 매칭
2. 빈 이름 매칭

## @Primary
우선순위를 지정하는 방법. @Autowired 타입 매칭 결과가 2개 이상일 때 @Primary가 있으면 우선권을 가진다.
```java
// RateDiscountPolicy.java
@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy{
}

// OrderServiceImpl.java
public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
	this.memberRepository = memberRepository;
	this.discountPolicy = discountPolicy;
}
```
RateDiscountPolicy에 @Primary가 있기 때문에 RateDiscountPolicy가 주입된다.

## @Qualifier, @Primary 활용
메인 데이터베이스에 @Primary를 주고 서브 데이터베이스에 @Qualifier를 줘서 특별한 경우일 때 @Qualifier를 써서 명시적으로 스프링 빈을 획득할 수 있다.

# Annotaion 직접 만들기
아까 @Qualifier를 쓸 때 문자열을 직접 넣어야 하기 때문에 잘못입력한다면 컴파일 단계에서 오류를 잡을 수 없다.
그래서 Annotaion을 만들어서 해결해보자.
```java
// MainDiscountPolicy.java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {

}
```
사용은 이렇게 할 수 있다.
```java
// MainDiscountPolicy.java
@Component
@RateDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
}

// OrderServiceImpl.java
public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
}
```

# 조회한 빈이 모두 필요할 때 List, Map
의도적으로 해당 빈이 모두 필요할 수도 있다. 예를 들면 고객이 고정 할인 방식, 비율 할인 방식 중 선택해 사용하는 것이다.
```java
public class AllBeanTest {

	@Test
	void findAllBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
		
		DiscountService discountPolicy = ac.getBean(DiscountService.class);
		Member member = new Member(1L, "userA", Grade.VIP);
		
		assertThat(discountPolicy).isInstanceOf(DiscountService.class);
		
		int fixDiscountPrice = discountPolicy.discount(member, 10000, "fixDiscountPolicy");
		assertThat(fixDiscountPrice).isEqualTo(1000);
		
		int rateDiscountPrice = discountPolicy.discount(member, 10000, "rateDiscountPolicy");
		assertThat(rateDiscountPrice).isEqualTo(1000);
	}
	
	static class DiscountService{
		private final Map<String, DiscountPolicy> policyMap;
		private final List<DiscountPolicy> policies;
		
		public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
			this.policyMap = policyMap;
			this.policies = policies;
			System.out.println("policyMap = " + policyMap);
			System.out.println("policies = " + policies);
		}

		public int discount(Member member, int price, String discountCode) {
			DiscountPolicy discountPolicy = policyMap.get(discountCode);
			int discountPrice = discountPolicy.discount(member, price);
			return discountPrice;
		}
	}
}
```
위와 같이 Map으로 DiscountPolicy의 스프링빈을 모두 가져와서 원하는 대로 선택해 쓸 수 있다.

# 자동, 수동의 올바른 실무 운영 기준
## 편리한 자동 기능을 기본으로 사용하자.
## 수동 빈은 언제 사용할까?
- 업무 로직 빈 : 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층을 관리하는 리포지토리.
- 업무 오직 빈은 가급적 자동 빈 등록을 하는 게 좋다. 
하지만 DiscountPolicy 처럼 다형성을 활용하는 스프링 빈은 수동 빈으로 등록하는 게 한눈에 보기 편해서 좋다.
아래와 같이 수동으로 등록하면 한눈에 보기 편하다.
```java
@Configuration
public class DiscountPolicyConfig{

	@Bean
	public DiscountPolicy rateDiscountPolicy(){
		return new RateDiscountPolicy();
	}
	@Bean
	public DiscountPolicy fixDiscountPolicy(){
		return new FixDiscountPolicy();
	}
}
```
- 기술 지원 빈 : 데이터베이스 연결, 공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술이다.
- 기술 지원 로직은 업무 로직과 비교해 숫자가 매우 적고 애플리케이션 전반에 걸쳐 광범위하게 영향을 미친다. 
그래서 가급적 수동 빈 등록을 해야 문제가 나타났을 때 문제 파악하기에 용이하다.
- 스프링이나 스프링부트가 자동으로 등록하는 빈에 대해서는 스프링이 의도하는 대로 쓰는 것이 좋다. 
예를 들면 스프링 부트의 DataSource의 경우 자동으로 등록되기 때문에 스프링의 의도에 맞게 잘 쓰는 게 좋다.

# 빈 생명주기 콜백
## 빈 생명주기 콜백 시작
데이터베이스 커넥션 풀이나 네트워크 소켓처럼 애플리케이션 시작에 미리 연결을 해두고 애플리케이션 종료 때 이 연결을 모두 종료하는 작업이 필요하다.
스프링에서 이 작업을 진행하려면 객체의 초기화와 종료 작업을 해야 한다.
- 예제
```java
public class NetworkClient {

	private String url;
	
	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
		connect();
		call("초기화 연결 메세지");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//서비스 시작 시 호출
	public void connect() {
		System.out.println("connect: " + url);
	}
	
	public void call(String message) {
		System.out.println("call: " + url + " message: " + message);
	}
	
	//서비스 종료 시 호출
	public void disconnect(String message) {
		System.out.println("close: " + url);
	}
}

public class BeanLifeCycleTest {

	@Test
	public void lifecycleTest() {
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCyclConfig.class);
		NetworkClient client = ac.getBean(NetworkClient.class);
		
		ac.close();
	}
	
	@Configuration
	static class LifeCyclConfig{
		
		@Bean
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://hello-spring.dev");
			return networkClient;			
		}
	}
}
```
```terminal
생성자 호출, url = null
connect: null
call: null message: 초기화 연결 메세지
00:54:50.675 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@22a637e7, started on Sat Jul 30 00:54:50 KST 2022
```
예제처럼 url이 null이 아닌 의존관계가 끝났을 때를 알아야 connect()를 호출할 수 있을 것이다.
- 스프링 빈의 이벤트 라이프사이클
스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸빈 콜백 -> 스프링 종료

> 참고) 객체의 생성과 초기화를 분리하자.
객체는 생성 및 필수 정보만 넣고 초기화는 이렇게 생성된 값들을 이용해 커넥션을 연결하는 등 무거운 동작을 하는 게 좋다.
이렇게 쓰면 객체 생성 후 최초 액션 시 초기화를 호출하는 지연의 방식을 사용할 수도 있다.

스프링은 3가지의 빈 생명주기 콜백을 지원한다.

## 인터페이스 InitializingBean, DisposableBean
```java
public class NetworkClient implements InitializingBean, DisposableBean{

	private String url;
	
	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//서비스 시작 시 호출
	public void connect() {
		System.out.println("connect: " + url);
	}
	
	public void call(String message) {
		System.out.println("call: " + url + " message: " + message);
	}
	
	//서비스 종료 시 호출
	public void disconnect() {
		System.out.println("close: " + url);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		connect();
		call("초기화 연결 메세지");
	}

	@Override
	public void destroy() throws Exception {
		disconnect();
	}
}
```
```terminal
생성자 호출, url = null
connect: http://hello-spring.dev
call: http://hello-spring.dev message: 초기화 연결 메세지
00:53:25.975 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@22a637e7, started on Sat Jul 30 00:53:25 KST 2022
close: http://hello-spring.dev
```
- 이렇게 쓰면 스프링 인터페이스에 의존적이다. 
- 메서드 이름을 마음대로 정할 수 없다.
- 내가 고칠 수 없는 외부 라이브러리에 사용할 수 없다.

## 빈 등록 초기화, 소멸 메서드
`@Bean(initMethod = "init", destroyMethod = "close")`와 같이 쓴다.
`destroyMethod = "(inferred)"`라서 destroyMethod를 지정하지 않으면 기본값으로 close나 shutdown라는 이름의 메서드를 자동으로 호출해준다.
이 추론 기능을 사용하기 싫으면 `destroyMethod = ""`로 주면 된다.
```java
public class BeanLifeCycleTest {

	@Test
	public void lifecycleTest() {
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCyclConfig.class);
		NetworkClient client = ac.getBean(NetworkClient.class);
		
		ac.close();
	}
	
	@Configuration
	static class LifeCyclConfig{
		
		@Bean(initMethod = "init", destroyMethod = "close")
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://hello-spring.dev");
			return networkClient;			
		}
	}
}
```
## @PostConstruct, @PreDestroy
최신 스프링에서 권장하는 방법
import 문을 보면 `import javax.annotation.PostConstruct`라서 JSR-2050이라는 자바 표준이다.
하지만 외부 라이브러리에는 못써서 @Bean initMethod, destroyMethod 방법을 써야 한다.
- @PostConstruct은 초기화 콜백
- @PreDestroy은 소멸빈 콜백
```java
public class NetworkClient{

	private String url;
	
	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//서비스 시작 시 호출
	public void connect() {
		System.out.println("connect: " + url);
	}
	
	public void call(String message) {
		System.out.println("call: " + url + " message: " + message);
	}
	
	//서비스 종료 시 호출
	public void disconnect() {
		System.out.println("close: " + url);
	}

	@PostConstruct
	public void init() throws Exception {
		connect();
		call("초기화 연결 메세지");
	}

	@PreDestroy
	public void close() throws Exception {
		disconnect();
	}
}
```

# 빈 스코프
스프링은 다양한 스코프를 지원한다.
- 싱글톤 스코프 : 기본. 스프링 컨테이너의 시작과 종료까지 유지. 가장 넓은 스코프.
- 프로토타입 스코프 : 스프링 컨테이너는 빈의 생성과 의존관계 주입, 초기화 메서드까지만 관여한다.
- 웹 관련 스코프
	- request : 웹 요청이 들어오고 나갈 때 까지 유지되는 스코프.
	- session : 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프.
	- application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프.
	
## 프로토타입 스코프
프로토 타입으로 만든 스프링 컨테이너는 고객이 스프링 빈을 요청할 시 요청 때마다 다른 빈을 반환한다.
그래서 @PreDestory 같은 메서드는 호출되지 않는다. 그래서 클라이언트가 종료 메서드를 호출해야 한다.
```java
public class PrototypeTest {

	@Test
	void prototypeBeanFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		System.out.println("find prototypeBean1");
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		System.out.println("find prototypeBean2");
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		System.out.println("prototypeBean1 = " + prototypeBean1);
		System.out.println("prototypeBean2 = " + prototypeBean2);
		
		assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
		
		ac.close();
	}
	
	@Scope("prototype")
	static class PrototypeBean{
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean init");
		}
		
		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean destroy");
		}
	}
}
/* Terminal
find prototypeBean1
PrototypeBean init
find prototypeBean2
PrototypeBean init
prototypeBean1 = hello.core.scope.PrototypeTest$PrototypeBean@710c2b53
prototypeBean2 = hello.core.scope.PrototypeTest$PrototypeBean@5386659f
01:22:01.159 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@69b2283a, started on Sat Jul 30 01:22:01 KST 2022
*/
```

## 프로토타입 빈과 싱글톤 빈을 같이 사용할 떄 생기는 문제
```java
public class SingletonWIthPrototypeTest1 {

	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();
		assertThat(prototypeBean1.getCount()).isEqualTo(1);
		
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		assertThat(prototypeBean2.getCount()).isEqualTo(1);
		
		ac.close();
	}
	
	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
		
		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		assertThat(count1).isEqualTo(1);
		
		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int count2 = clientBean2.logic();
		assertThat(count2).isEqualTo(2);
		
		ac.close();
	}
	
	@Scope("singleton")
	static class ClientBean{
		private final PrototypeBean prototypeBean; //생성시점에 이미 주입됨.

		public ClientBean(PrototypeBean prototypeBean) {
			this.prototypeBean = prototypeBean;
		}
		
		public int logic() {
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	@Scope("prototype")
	static class PrototypeBean{
		private int count = 0;
		
		public void addCount() {
			count++;
		}
		
		public int getCount() {
			return count;
		}
		
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean init " + this);
		}
		
		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean destroy");
		}
	}
}
```
ClientBean 생성 시점에 PrototypeBean이 이미 주입이 끝난 상태라 의도대로 count는 둘다 1이 아니다.

## 해결 방법 - ObjectProvider
물론 logic에서 스프링 컨테이너에서 매번 PrototypeBean을 가져오면 된다.
이것을 DL(Dependency Lookup)이라고 한다.
```java
@Scope("singleton")
	static class ClientBean{
		private final ApplicationContext applicationContext; //생성시점에 이미 주입됨.

		public ClientBean(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}
		
		public int logic() {
			PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
```
하지만 코드가 너무 더러워진다. ObjectProvider를 사용해 해결 가능하다.
```java
@Scope("singleton")
static class ClientBean{
	@Autowired
	private ObjectProvider<PrototypeBean> prototypeBeanProvider;
	
	public int logic() {
		PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
		prototypeBean.addCount();
		int count = prototypeBean.getCount();
		return count;
	}
}
```
하지만 위 방법은 스프링 인터페이스에 의존적이다. 그래서 JSR-330 자바 표준인 Provider를 쓰는 게 좋다.
라이브러리 등록이 필요하다.
```gradle
dependencies {
	implementation group: 'javax.inject', name: 'javax.inject', version: '1'
```
```java
@Scope("singleton")
static class ClientBean{
	@Autowired
	private Provider<PrototypeBean> prototypeBeanProvider;
	
	public int logic() {
		PrototypeBean prototypeBean = prototypeBeanProvider.get();
		prototypeBean.addCount();
		int count = prototypeBean.getCount();
		return count;
	}
}
```

## 웹 스코프
웹 환경에서만 동작한다.
- request : 웹 요청이 들어오고 나갈 때 까지 유지되는 스코프. HTTP 요청 마다 별도의 빈 객체가 생성됨.
- session : 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프.
- application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프.
- websocket : 웹 소켓과 동일한 생명주기를 같는 스코프.



