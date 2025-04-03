package custom.practice.service;

import custom.practice.domain.Member;
import custom.practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isPresent()) {
            return findMember.get();
        } else {
            throw new IllegalStateException("해당 Id의 회원은 존재하지 않습니다");
        }
    }

}
