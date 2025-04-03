package custom.practice.service;

import custom.practice.PracticeApplication;
import custom.practice.domain.Member;
import custom.practice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {

        Member member = new Member();
        member.setName("joon");

        Long savedId = memberService.join(member);

        String getName = memberRepository.findById(savedId).get().getName();
        assertThat(member.getName()).isEqualTo(getName);
    }

    @Test
    public void 중복회원예외() {
        Member member1 = new Member();
        member1.setName("joon");

        Member member2 = new Member();
        member2.setName("joon");

        memberService.join(member1);
        try {
            memberService.join(member2); //같은 이름의 회원 저장시 예외 발생
        } catch (IllegalStateException e) {
            String e_msg = e.getMessage();
            System.out.println("e_msg = " + e_msg);
        }

    }


}