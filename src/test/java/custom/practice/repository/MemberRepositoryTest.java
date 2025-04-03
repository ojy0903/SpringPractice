package custom.practice.repository;

import custom.practice.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void methodTest() {
        Member member = new Member();
        member.setName("hello");

        memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getName()).isEqualTo(member.getName());

    }
}