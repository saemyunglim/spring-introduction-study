package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest//스프링 컨테이너와 테스트가 모두 동작하도록 하는 어노테이션
@Transactional //@Transactional이 붙은 임의의 메서드는 해당 메서드의 명령이 모두 성공하지 않으면 예외처리시켜 롤백시켜버림(모두 성공할 경우에만 커밋함). 단, 테스트 코드에서는 해당 메서드의 명령어가 모두 실행되고난 후에 롤백되어 DB에 들어갔던 데이터가 모두 초기화됨.
public class MemberServiceIntegrationTest {


    @Autowired MemberRepository memberRepository; //테스트 코드는 다른 곳에서 사용하는 경우가 없기때문에 생성자로 주입시킬 필요가 없음
                                                  //@Autowired를 통해 지정한 타입에 해당하는 객체를 가져옴
    @Autowired MemberService memberService;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long savedId = memberService.join(member);

        //then
        Member foundMember = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(foundMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));

//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}
