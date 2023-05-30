package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.domain.Shop;
import net.jaram.indoornavigation.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByVenueId (Long venueId);
    List<Shop> findAllByVenueIdAndFloor (Long venueId, int floor);

    Optional<Shop> findByUuid(String uuid);
}