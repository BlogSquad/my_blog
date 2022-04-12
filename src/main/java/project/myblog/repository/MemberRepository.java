package project.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.myblog.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
