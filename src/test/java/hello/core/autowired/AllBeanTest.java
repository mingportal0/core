package hello.core.autowired;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;

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
