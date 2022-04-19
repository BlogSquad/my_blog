package project.myblog.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
