package net.jaram.indoornavigation.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Venue {
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

    @Getter
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Getter
    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    private Date updatedAt;

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
