package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
