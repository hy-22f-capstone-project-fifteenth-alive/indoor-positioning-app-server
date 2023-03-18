package net.jaram.indoornavigation.oauth.repository;

import net.jaram.indoornavigation.oauth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(String id);
}