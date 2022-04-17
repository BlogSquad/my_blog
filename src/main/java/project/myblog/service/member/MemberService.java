package project.myblog.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.auth.dto.LoginMember;
import project.myblog.domain.member.Member;
import project.myblog.exception.BizException;
import project.myblog.exception.ExceptionCode;
import project.myblog.repository.MemberRepository;
import project.myblog.web.dto.member.response.MemberResponse;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public LoginMember signUp(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            Member save = memberRepository.save(new Member(email));
            return new LoginMember(save);
        }
        return new LoginMember(member.get());
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberOfMine(String email) {
        Member member = findMemberByEmail(email);
        return new MemberResponse(member);
    }

    public void updateMemberOfMineIntroduction(String email, String introduction) {
        Member member = findMemberByEmail(email);
        member.updateIntroduction(introduction);
    }

    public void updateMemberOfMineSubject(String email, String subject) {
        Member member = findMemberByEmail(email);
        member.updateSubject(subject);
    }

    public void deleteMember(String email) {
        Member member = findMemberByEmail(email);
        member.delete();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .filter(member -> !member.isDeleted())
                .orElseThrow(() -> new BizException(ExceptionCode.MEMBER_INVALID));
    }
}
