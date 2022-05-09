package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

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

        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }


        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}