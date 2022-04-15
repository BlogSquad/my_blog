package project.myblog.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.auth.dto.LoginMember;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.domain.Member;
import project.myblog.repository.MemberRepository;
import project.myblog.web.dto.member.response.MemberResponse;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public LoginMember signUp(OAuthApiResponse response) {
        Member member = memberRepository.findByEmail(response.getEmail());
        if (member == null) {
            Member save = memberRepository.save(new Member(response.getEmail()));
            return new LoginMember(save);
        }
        return new LoginMember(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberOfMine(String email) {
        return new MemberResponse(memberRepository.findByEmail(email));
    }

    public void updateMemberOfMineIntroduction(String email, String introduction) {
        Member member = memberRepository.findByEmail(email);
        member.updateIntroduction(introduction);
    }

    public void updateMemberOfMineSubject(String email, String subject) {
        Member member = memberRepository.findByEmail(email);
        member.updateSubject(subject);
    }
}
