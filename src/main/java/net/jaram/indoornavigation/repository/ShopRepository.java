package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByVenueId (Long venueId);
}