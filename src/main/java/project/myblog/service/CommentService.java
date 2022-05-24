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
import project.myblog.web.dto.comment.CommentResponses;

import java.util.List;

import static project.myblog.exception.ErrorCode.COMMENT_INVALID;

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

    public CommentResponse createComment(String email, Long postId, CommentRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        Post post = postService.findPostById(postId);

        Comment saved = commentRepository.save(requestDto.toEntity(post, member));
        return new CommentResponse(saved);
    }

    public CommentResponses findComments(Long postId) {
        List<Comment> parentComments = commentRepository.findAllByPostIdAndParentIdlNull(postId);
        return CommentResponses.create(parentComments);
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

    public CommentResponse createChildComment(String email, Long postId, Long parentId, CommentRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        Post post = postService.findPostById(postId);
        Comment children = requestDto.toEntity(post, member);
        findCommentById(parentId).createChildComment(children);

        commentRepository.save(children);
        return new CommentResponse(children);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new BusinessException(COMMENT_INVALID));
    }
}
