package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
