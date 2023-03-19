package net.jaram.indoornavigation.repository;

import net.jaram.indoornavigation.domain.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeaconRepository extends JpaRepository<Beacon, Long> {
}
