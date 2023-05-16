package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.domain.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeaconRepository extends JpaRepository<Beacon, Long> {
    Optional<Beacon> findBeaconByMajorAndMinor(Long major, Long minor);
}
