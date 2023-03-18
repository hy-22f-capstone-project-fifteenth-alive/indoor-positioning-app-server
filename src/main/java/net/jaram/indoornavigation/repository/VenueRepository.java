package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.entity.Venue;
import net.jaram.indoornavigation.oauth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    Optional<Venue> findById(String id);
    List<Venue> findAll();
}
