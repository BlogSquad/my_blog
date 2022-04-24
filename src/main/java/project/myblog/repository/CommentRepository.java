package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
