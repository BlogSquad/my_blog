package project.myblog.service.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.member.Member;
import project.myblog.domain.post.Post;
import project.myblog.exception.BusinessException;
import project.myblog.exception.ExceptionCode;
import project.myblog.repository.post.PostRepository;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.response.PostResponse;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    public Long createPost(String email, PostRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        return postRepository.save(requestDto.toEntity(member)).getId();
    }

    public PostResponse findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.POST_INVALID));

        return new PostResponse(post);
    }
}
