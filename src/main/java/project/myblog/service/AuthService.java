package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.repository.MemberRepository;
import project.myblog.web.dto.OAuthResponse;
import project.myblog.web.dto.SessionMember;

@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public SessionMember login(OAuthResponse oAuthResponse) {
        Member member = memberRepository.findByEmail(oAuthResponse.getEmail());
        if (member == null) {
            Member saveMember = memberRepository.save(new Member(oAuthResponse.getEmail()));
            return new SessionMember(saveMember);
        }

        return new SessionMember(member);
    }
}
