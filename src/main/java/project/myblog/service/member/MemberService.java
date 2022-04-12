package project.myblog.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.domain.Member;
import project.myblog.repository.MemberRepository;

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
    public Member findMemberOfMine(LoginMember loginMember) {
        return memberRepository.findByEmail(loginMember.getEmail());
    }
}
