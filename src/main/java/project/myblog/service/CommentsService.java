package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.CommentsRepository;
import project.myblog.web.dto.comments.CommentsRequest;

@Transactional
@Service
public class CommentsService {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentsRepository commentsRepository;


    public CommentsService(PostService postService, MemberService memberService, CommentsRepository commentsRepository) {
        this.postService = postService;
        this.memberService = memberService;
        this.commentsRepository = commentsRepository;
    }

    public Long createComments(String email, Long postId, CommentsRequest commentsRequest) {
        Member member = memberService.findMemberByEmail(email);
        Post post = postService.findPostById(postId);

        return commentsRepository.save(commentsRequest.toEntity(post, member)).getId();
    }
}
