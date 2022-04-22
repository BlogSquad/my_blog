package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.CommentRepository;
import project.myblog.web.dto.comment.CommentRequest;

@Transactional
@Service
public class CommentService {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;


    public CommentService(PostService postService, MemberService memberService, CommentRepository commentRepository) {
        this.postService = postService;
        this.memberService = memberService;
        this.commentRepository = commentRepository;
    }

    public Long createComments(String email, Long postId, CommentRequest commentsRequest) {
        Member member = memberService.findMemberByEmail(email);
        Post post = postService.findPostById(postId);

        return commentRepository.save(commentsRequest.toEntity(post, member)).getId();
    }
}
