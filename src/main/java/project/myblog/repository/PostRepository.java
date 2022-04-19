package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.post.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
