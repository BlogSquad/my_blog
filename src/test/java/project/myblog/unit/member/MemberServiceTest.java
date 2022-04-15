package project.myblog.unit.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.auth.dto.LoginMember;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.auth.dto.naver.NaverOAuthApiResponse;
import project.myblog.domain.Member;
import project.myblog.repository.MemberRepository;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.member.response.MemberResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static project.myblog.acceptance.member.MemberStepsRequest.EMAIL;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    private OAuthApiResponse oAuthApiResponse;

    @BeforeEach
    void setUp() {
        oAuthApiResponse = new NaverOAuthApiResponse(new NaverOAuthApiResponse.Response(EMAIL));
    }

    @Test
    void 회원가입() {
        // when
        LoginMember loginMember = memberService.signUp(oAuthApiResponse);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @DisplayName("중복된 회원가입은 회원가입을 하지 않고, 기존 회원 정보를 반환한다.")
    @Test
    void 중복_회원가입_안됨() {
        // when
        LoginMember loginMember = memberService.signUp(oAuthApiResponse);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @Test
    void 내_회원_정보_조회() {
        // given
        Member saveMember = memberRepository.save(createMember());

        // when
        MemberResponse memberResponse = memberService.findMemberOfMine(EMAIL);

        // then
        MemberResponse expectedMember = new MemberResponse(createMember());
        assertThat(memberResponse).isEqualTo(expectedMember);
    }

    @Test
    void 내_회원_정보_수정_한줄_소개() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.updateMemberOfMineIntroduction(EMAIL, "한줄 소개 변경");

        // then
        Member member = memberRepository.findByEmail(EMAIL);
        assertThat(member.getIntroduction()).isEqualTo("한줄 소개 변경");
    }

    @Test
    void 내_회원_정보_수정_제목() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.updateMemberOfMineSubject(EMAIL, "제목 변경");

        // jpa 지원 기능 ! @

        // then
        Member member = memberRepository.findByEmail(EMAIL);
        assertThat(member.getSubject()).isEqualTo("제목 변경");
    }

    private Member createMember() {
        return new Member(oAuthApiResponse.getEmail());
    }
}
