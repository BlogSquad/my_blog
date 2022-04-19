package project.myblog.unit.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.auth.dto.LoginMember;
import project.myblog.domain.member.Member;
import project.myblog.exception.BusinessException;
import project.myblog.repository.MemberRepository;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.member.response.MemberResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.myblog.acceptance.member.MemberStepsRequest.EMAIL;
import static project.myblog.exception.ExceptionCode.MEMBER_INVALID;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void 회원가입() {
        // when
        LoginMember loginMember = memberService.signUp(EMAIL);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @DisplayName("중복된 회원가입은 회원가입을 하지 않고, 기존 회원 정보를 반환한다.")
    @Test
    void 중복_회원가입_안됨() {
        // when
        LoginMember loginMember = memberService.signUp(EMAIL);

        // then
        assertThat(loginMember).isEqualTo(new LoginMember(createMember()));
    }

    @Test
    void 내_회원_정보_조회() {
        // given
        memberRepository.save(createMember());

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
        Member member = memberRepository.findByEmailAndIsDeletedFalse(EMAIL).get();
        assertThat(member.getIntroduction()).isEqualTo("한줄 소개 변경");
    }

    @Test
    void 내_회원_정보_수정_제목() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.updateMemberOfMineSubject(EMAIL, "제목 변경");

        // then
        Member member = memberRepository.findByEmailAndIsDeletedFalse(EMAIL).get();
        assertThat(member.getSubject()).isEqualTo("제목 변경");
    }

    @Test
    void 회원_탈퇴() {
        // given
        memberRepository.save(createMember());

        // when
        memberService.deleteMember(EMAIL);

        // then
        List<Member> members = memberRepository.findAllByEmail(EMAIL);
        assertThat(members.get(0).isDeleted()).isTrue();
    }

    @Test
    void 회원_탈퇴_후_재가입() {
        // given
        memberRepository.save(createMember());
        memberService.deleteMember(EMAIL);

        // when
        memberService.signUp(EMAIL);

        // then
        List<Member> members = memberRepository.findAllByEmail(EMAIL);
        assertThat(members.size()).isEqualTo(2);

        Member member = memberRepository.findByEmailAndIsDeletedFalse(EMAIL).get();
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
        return new Member(EMAIL);
    }
}
