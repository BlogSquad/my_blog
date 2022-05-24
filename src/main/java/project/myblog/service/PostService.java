package project.myblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myblog.domain.Member;
import project.myblog.domain.Post;
import project.myblog.exception.BusinessException;
import project.myblog.exception.ErrorCode;
import project.myblog.repository.HitsRedisRepository;
import project.myblog.repository.PostRepository;
import project.myblog.web.dto.post.PostPagingResponses;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.PostResponse;

@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final HitsRedisRepository hitsRedisRepository;

    public PostService(PostRepository postRepository, MemberService memberService, HitsRedisRepository hitsRedisRepository) {
        this.postRepository = postRepository;
        this.memberService = memberService;
        this.hitsRedisRepository = hitsRedisRepository;
    }

    public Long createPost(String email, PostRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        return postRepository.save(requestDto.toEntity(member)).getId();
    }

    public PostResponse findPost(Long postId) {
        Post post = findPostById(postId);
        hitsRedisRepository.incrementHits(post.getId());

        return new PostResponse(post);
    }

    public void updatePost(String email, Long postId, PostRequest requestDto) {
        Member member = memberService.findMemberByEmail(email);
        Post post = findPostById(postId);

        post.update(requestDto.getTitle(), requestDto.getContents(), member);
    }

    public void deletePost(String email, Long postId) {
        Member member = memberService.findMemberByEmail(email);
        Post post = findPostById(postId);

        post.delete(member);
    }

    public Post findPostById(Long postId) {
        return postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_INVALID));
    }

    public PostPagingResponses findAllPostPaging(Pageable pageable) {
        Page<Post> postsPaging = postRepository.findAll(pageable);
        return PostPagingResponses.create(postsPaging);
    }
}
