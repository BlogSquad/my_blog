package project.myblog.unit.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.myblog.auth.dto.LoginMember;
import project.myblog.auth.dto.OAuthApiResponse;
import project.myblog.auth.dto.naver.NaverOAuthApiResponse;
import project.myblog.domain.Member;
import project.myblog.repository.MemberRepository;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.MemberIntroductionRequest;
import project.myblog.web.dto.MemberSubjectRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static project.myblog.acceptance.member.MemberStepsRequest.EMAIL;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;
    private OAuthApiResponse oAuthApiResponse;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
        oAuthApiResponse = new NaverOAuthApiResponse(new NaverOAuthApiResponse.Response(EMAIL));
    }

    @Test
    void 회원가입() {
        when(memberRepository.findByEmail(anyString())).thenReturn(null);
        when(memberRepository.save(any())).thenReturn(createMember());

        // when
        LoginMember loginMember = memberService.signUp(oAuthApiResponse);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
        verify(memberRepository, times(1)).save(any());
    }

    @DisplayName("중복된 회원가입은 회원가입을 하지 않고, 기존 회원 정보를 반환한다.")
    @Test
    void 중복_회원가입_안됨() {
        when(memberRepository.findByEmail(anyString())).thenReturn(createMember());

        // when
        LoginMember loginMember = memberService.signUp(oAuthApiResponse);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @Test
    void 내_회원_정보_조회() {
        // given
        when(memberRepository.findByEmail(anyString())).thenReturn(createMember());

        // when
        Member member = memberService.findMemberOfMine(new LoginMember(createMember()));

        // then
        assertThat(member).isEqualTo(new Member(
                oAuthApiResponse.getEmail(),
                "한줄 소개가 작성되지 않았습니다.",
                "monkeyDugi"
        ));
    }

    @Test
    void 내_회원_정보_수정_한줄_소개() {
        // given
        when(memberRepository.findByEmail(anyString())).thenReturn(createMember());

        // when
        memberService.updateMemberOfMineIntroduction(
                new LoginMember(createMember()),
                new MemberIntroductionRequest("한줄 소개 변경")
        );

        // then
        Member member = memberRepository.findByEmail(oAuthApiResponse.getEmail());
        assertThat(member).isEqualTo(new Member(
                oAuthApiResponse.getEmail(),
                "한줄 소개 변경",
                "monkeyDugi"
        ));
    }

    @Test
    void 내_회원_정보_수정_제목() {
        // given
        when(memberRepository.findByEmail(anyString())).thenReturn(createMember());

        // when
        memberService.updateMemberOfMineSubject(
                new LoginMember(createMember()),
                new MemberSubjectRequest("제목 변경")
        );

        // then
        Member member = memberRepository.findByEmail(oAuthApiResponse.getEmail());
        assertThat(member).isEqualTo(new Member(
                oAuthApiResponse.getEmail(),
                "한줄 소개가 작성되지 않았습니다.",
                "제목 변경"
        ));
    }

    private Member createMember() {
        return new Member(oAuthApiResponse.getEmail());
    }
}
