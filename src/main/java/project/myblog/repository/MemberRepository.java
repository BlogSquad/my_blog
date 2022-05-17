package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.myblog.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.email = :email and m.isDeleted = false")
    Optional<Member> findByEmailAndIsDeletedFalse(String email);
}
