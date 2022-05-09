package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    //각각의 테스트가 실행되는 순서에 따라서 오류가 발생할 수 있기 때문에 각 테스트 종료후에 저장소를 비워줘야함
    //각각의 테스트가 순서에 상관없이 성공하도록 해야함(순서에 의존하면 안됨)
    @AfterEach //각 테스트 종료후에 실행되는 메서드
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        memberRepository.save(member);

        Member memberFindById = memberRepository.findById(member.getId()).get();
        assertThat(memberFindById).isEqualTo(member); //memberRepository를 통해 찾은 해당id의 멤버가 저장한 멤버와 같은지 테스트
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        Member memberFindByName = memberRepository.findByName("spring1").get();
        assertThat(memberFindByName).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        List<Member> allMember = memberRepository.findAll();
        assertThat(allMember.size()).isEqualTo(2);
    }
}
