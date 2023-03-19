package net.jaram.indoornavigation.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Venue extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(nullable = false)
    private String venueName;

    @Getter
    @Column(nullable = false)
    private String description;

    @Getter
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Beacon> beacons = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Shop> shops = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<VenueManager> venueManagers = new HashSet<>();

    public void addBeacon(Beacon beacon){
        beacons.add(beacon);
        beacon.setVenue(this);
    }

    public void addShop(Shop shop){
        shops.add(shop);
        shop.setVenue(this);
    }

    public void addVenueManager(VenueManager venueManager){
        venueManagers.add(venueManager);
        venueManager.setVenue(this);
    }
}
