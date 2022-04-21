package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
