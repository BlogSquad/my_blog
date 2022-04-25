package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Comment;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;
import project.myblog.repository.CommentRepository;
import project.myblog.web.dto.comment.CommentRequest;
import project.myblog.web.dto.comment.CommentResponse;

import java.util.ArrayList;
import java.util.List;

import static project.myblog.exception.ExceptionCode.COMMENT_INVALID;

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

    public Long createComment(String email, Long postId, CommentRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        Post post = postService.findPostById(postId);

        return commentRepository.save(requestDto.toEntity(post, member)).getId();
    }

    public List<CommentResponse> findComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostIdAndIsDeletedFalse(postId);
        return createCommentsResponse(comments);
    }

    public void updateComment(String email, Long commentId, CommentRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        Comment comment = findCommentById(commentId);

        comment.update(requestDto.getContents(), member);
    }

    public void deleteComment(String email, Long commentId) {
        Member member = memberService.findMemberByEmail(email);
        Comment comment = findCommentById(commentId);

        comment.delete(member);
    }

    private List<CommentResponse> createCommentsResponse(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment.getContents(), comment.getMember().getEmail()));
        }
        return commentResponses;
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new BusinessException(COMMENT_INVALID));
    }
}
