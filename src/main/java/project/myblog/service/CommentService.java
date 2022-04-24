package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Comment;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.repository.CommentRepository;
import project.myblog.web.dto.comment.CommentRequest;
import project.myblog.web.dto.comment.CommentResponse;

import java.util.ArrayList;
import java.util.List;

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

    public Long createComment(String email, Long postId, CommentRequest commentsRequest) {
        Member member = memberService.findMemberByEmail(email);
        Post post = postService.findPostById(postId);

        return commentRepository.save(commentsRequest.toEntity(post, member)).getId();
    }

    public List<CommentResponse> findComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return createCommentsResponse(comments);
    }

    private List<CommentResponse> createCommentsResponse(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment.getContents(), comment.getMember().getEmail()));
        }
        return commentResponses;
    }
}
