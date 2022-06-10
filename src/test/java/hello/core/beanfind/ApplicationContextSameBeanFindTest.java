package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByDuplicate(){
//        MemberRepository bean = ac.getBean(MemberRepository.class);
        // org.springframework.beans.factory.NoUniqueBeanDefinitionException:
        // No qualifying bean of type 'hello.core.member.MemberRepository' available:
        // expected single matching bean but found 2: memberRepository1,memberRepository2

        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
//        assertThat(bean).isInstanceOf(MemberRepository.class);
    }
    
    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 타입 이름을 지정하면 된다..")
    void findBeanByName(){
        MemberRepository bean = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(bean).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입 이름을 모두 조회하기")
    void findAllBeanByTupe() {
        
        Map<String, MemberRepository> beans = ac.getBeansOfType(MemberRepository.class);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + " value = " + beans.get(key));
        }
        System.out.println("beans = " + beans);
        assertThat(beans.size()).isEqualTo(2);
    }

    @Configuration
    static class SameBeanConfig{

        @Bean
        public MemberRepository memberRepository1(){
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2(){
            return new MemoryMemberRepository();
        }

    }
}
