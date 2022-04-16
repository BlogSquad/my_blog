package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
