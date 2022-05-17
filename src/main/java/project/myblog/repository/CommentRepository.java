package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.myblog.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c join fetch c.member WHERE c.post.id = :postId and c.parent = null")
    List<Comment> findAllByPostIdAndParentIdlNull(Long postId);

    @Query("SELECT c FROM Comment c WHERE c.id = :id and c.isDeleted = false")
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);
}
