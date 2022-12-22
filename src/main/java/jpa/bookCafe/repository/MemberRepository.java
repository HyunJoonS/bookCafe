package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByUserId(String userId);

    public Optional<Member> findByUserIdAndPassword(String userId, String password);
}
