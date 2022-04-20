package project.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;
import project.myblog.exception.ExceptionCode;
import project.myblog.repository.PostRepository;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.PostResponse;

import java.util.Optional;

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
        Post post = findPostById(postId);
        return new PostResponse(post);
    }

    public Long updatePost(String email, Long postId, PostRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);

        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            if (post.get().isAuthorization(member)) {
                return post.get().update(requestDto.getTitle(), requestDto.getContents());
            }
            throw new BusinessException(ExceptionCode.POST_AUTHORIZATION);
        }
        return postRepository.save(requestDto.toEntity(member)).getId();
    }

    public void deletePost(String email, Long postId) {
        Member member = memberService.findMemberByEmail(email);
        Post post = findPostById(postId);

        if (post.isAuthorization(member)) {
            postRepository.delete(post);
        } else {
            throw new BusinessException(ExceptionCode.POST_AUTHORIZATION);
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.POST_INVALID));
    }
}
