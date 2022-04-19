package project.myblog.service.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.member.Member;
import project.myblog.repository.post.PostRepository;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.post.PostRequest;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    public Long createPost(String email, PostRequest postRequest) {
        Member member = memberService.findMemberByEmail(email);
        return postRepository.save(postRequest.toEntity(member)).getId();
    }
}
