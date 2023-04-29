package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.domain.Venue;
import net.jaram.indoornavigation.domain.VenueMapData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueMapDataRepository extends JpaRepository<VenueMapData, Long> {
    Optional<VenueMapData> findFirstByVenueAndUuid(Venue venue, String uuid);

    Optional<VenueMapData> findFirstByVenueOrderByCreatedAtDesc(Venue venue);
}
