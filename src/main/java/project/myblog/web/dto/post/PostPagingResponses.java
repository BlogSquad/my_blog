package project.myblog.web.dto.post;

import org.springframework.data.domain.Page;
import project.myblog.domain.Post;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostPagingResponses {
    private List<PostResponse> posts;
    private long totalCount;
    private int pageSize;
    private int currentPage;
    private int totalPage;

    private PostPagingResponses(List<PostResponse> posts, long totalCount, int pageSize, int currentPage, int totalPage) {
        this.posts = posts;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public static PostPagingResponses create(Page<Post> postsPaging) {
        return new PostPagingResponses(
                postResponseToList(postsPaging),
                postsPaging.getTotalElements(),
                postsPaging.getSize(),
                postsPaging.getNumber(),
                postsPaging.getTotalPages()
        );
    }

    private static List<PostResponse> postResponseToList(Page<Post> postsPaging) {
        return postsPaging.getContent().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
