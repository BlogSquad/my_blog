package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.myblog.domain.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.id = :id and p.isDeleted = false")
    Optional<Post> findByIdAndIsDeletedFalse(Long id);
}
