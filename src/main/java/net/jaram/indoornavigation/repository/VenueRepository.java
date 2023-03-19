package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    Optional<Venue> findByBeacons(String beaconId);
    List<Venue> findAll();
}
