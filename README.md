### 스프링 공부
참고
[https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8/lecture/55340?tab=note&volume=1.00](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8/lecture/55340?tab=note&volume=1.00)

### SOLID 원칙
- SRP: 단일 책임 원칙(single responsibility principle)
MemberService는 멤버를 생성하는 실행만 하는 역할만하고 AppConfig는 구현 객체를 생성하고 연결하는 책임을 갖는다.


- OCP: 개방-폐쇄 원칙 (Open/closed principle)
소프트웨어는 변경에는 닫혀있고 확장에는 열려있다. App을 사용 영역과 구성 영역으로 나눠 클라이언트 코드의 변경 없이 AppConfig의 FixDiscountPolicy에서 RateDiscountPolicy로 변경해 클라이언트에 주입하였다. 그래서 소프트웨어 요소를 새로 확장해도 사용 영역의 변경은 닫혀있다.

- LSP: 리스코프 치환 원칙 (Liskov substitution principle)


- ISP: 인터페이스 분리 원칙 (Interface segregation principle)


- DIP: 의존관계 역전 원칙 (Dependency inversion principle)
프로그래머는 추상화에만 의존해야 한다. 클라이언트 코드는 DiscountPolicy 추상화 인터페이스에만 의존하도록 하였다. 하지만 추상화 인터페이스로는 실행할 수 없기 때문에 AppConfig가 대신 구현 클래스를 생성해 클라이언트 코드에 의존관계를 주입했다.

### IoC
제어의 역전. 기존의 코드는 구현 객체가 직접 구현 객체를 생성하고 연결하고 실행하였다. 구현 객체가 프로그램의 제어 흐름을 스스로 조종한 것이다.
반면에 AppConfig를 활용해 클라이언트 구현 객체의 생성하고 연결하는 것을 자기 자신이 아닌 외부에서 제어하게 하였다. 이것을 제어의 역전이라고 한다.

#### 프레임워크 vs 라이브러리
프레임워크는 자신이 코드는 직접 작성하지만 코드를 제어하고 실행하는 권한은 넘기는 것이다.
반면에 라이브러리는 자신이 코드도 작성하고 제어의 흐름도 자신이 담당한다.

### DI
의존관계 주입. OrderServiceImpl은 어떤 객체가 주입될지 알 수 없다.
- 정적인 의존관계는 프로그램을 실행하지 않아도 의존관계를 알 수 있다.
- 동적인 객체 인스턴스 의존관계는 App 실행 시점에 인스턴스 참조가 연결된 의존관계이다.
- App 실행 시점에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 
클라이언트의 서버의 실제 의존관계가 연결되는 것을 의존관계 주입이라고 한다.
- DI를 사용하면 클라이언트 코드를 변경하지 않고 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
- DI를 사용하면 정적인 클래스 의존관계를 변경하지 않고 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.

### Ioc 컨테이너, DI 컨테이너
AppConfig 처럼 의존관계 주입을 해주는 것을 IoC 컨테이너 또는 DI 컨테이너(최근 주로 사용.)라고 한다.
또는 어셈블러, 오브젝트 팩토리 등으로 불리기도 한다.

### Spring
- ApplicationContext : 스프링 컨테이너
- @Configuration : 구성정보
- @Bean : @Bean이 붙은 메서드의 명을 key로 리턴값을 value로 스프링 컨테이너에 등록한다. 이렇게 등록된 Bean을 스프링빈이라고 한다.
- 이제 스프링 컨테이너에서 필요한 스프링 빈을 찾아야 한다. (ac.getBean())


