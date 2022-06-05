package project.myblog.unit.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.myblog.auth.dto.LoginMember;
import project.myblog.domain.Member;
import project.myblog.exception.BusinessException;
import project.myblog.repository.MemberRepository;
import project.myblog.service.MemberService;
import project.myblog.UnitTest;
import project.myblog.web.dto.member.MemberResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.myblog.acceptance.member.MemberStepsRequest.NAVER_EMAIL;
import static project.myblog.exception.ErrorCode.MEMBER_INVALID;

class MemberServiceTest extends UnitTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void 회원가입() {
        // when
        LoginMember loginMember = memberService.signUp(NAVER_EMAIL);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @DisplayName("중복된 회원가입은 회원가입을 하지 않고, 기존 회원 정보를 반환한다.")
    @Test
    void 중복_회원가입_안됨() {
        // when
        LoginMember loginMember = memberService.signUp(NAVER_EMAIL);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @Test
    void 내_회원_정보_조회() {
        // given
        memberRepository.save(createMember());

        // when
        MemberResponse memberResponse = memberService.findMemberOfMine(NAVER_EMAIL);

        // then
        MemberResponse expectedMember = new MemberResponse(createMember());
        assertThat(memberResponse).isEqualTo(expectedMember);
    }

    @Test
    void 내_회원_정보_수정_한줄_소개() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.updateMemberOfMineIntroduction(NAVER_EMAIL, "한줄 소개 변경");

        // then
        Member member = memberRepository.findByEmailAndIsDeletedFalse(NAVER_EMAIL).get();
        assertThat(member.getIntroduction()).isEqualTo("한줄 소개 변경");
    }

    @Test
    void 내_회원_정보_수정_제목() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.updateMemberOfMineSubject(NAVER_EMAIL, "제목 변경");

        // then
        Member member = memberRepository.findByEmailAndIsDeletedFalse(NAVER_EMAIL).get();
        assertThat(member.getSubject()).isEqualTo("제목 변경");
    }

    @Test
    void 회원_탈퇴() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.deleteMember(NAVER_EMAIL);

        // then
        List<Member> members = memberRepository.findAllByEmail(NAVER_EMAIL);
        assertThat(members.get(0).isDeleted()).isTrue();
    }

    @Test
    void 회원_탈퇴_후_재가입() {
        // given
        memberRepository.save(createMember());
        memberService.deleteMember(NAVER_EMAIL);

        // when
        memberService.signUp(NAVER_EMAIL);

        // then
        List<Member> members = memberRepository.findAllByEmail(NAVER_EMAIL);
        assertThat(members.size()).isEqualTo(2);

        Member member = memberRepository.findByEmailAndIsDeletedFalse(NAVER_EMAIL).get();
        assertThat(member.isDeleted()).isFalse();
    }

    @Test
    void 존재하지_않는_회원() {
        // when
        assertThatThrownBy(() -> memberService.findMemberOfMine("email"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(MEMBER_INVALID.getMessage());
    }

    private Member createMember() {
        return new Member(NAVER_EMAIL);
    }
}
