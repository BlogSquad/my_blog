package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.repository.MemberRepository;
import project.myblog.web.dto.OAuthApiResponse;
import project.myblog.web.dto.SessionMember;

import java.util.Optional;

@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public SessionMember login(OAuthApiResponse response) {
        Optional<Member> member = memberRepository.findByEmail(response.getEmail());
        return new SessionMember(
                member.orElse(
                        memberRepository.save(new Member(response.getEmail())))
        );
    }
}
