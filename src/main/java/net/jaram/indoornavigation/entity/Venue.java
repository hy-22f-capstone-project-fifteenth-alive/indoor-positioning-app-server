package net.jaram.indoornavigation.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    @OneToMany
    @JoinColumn(name="venue_id", nullable = false)
    private Set<Beacon> beacons;

    @Getter
    @OneToMany
    @JoinColumn(name="venue_id", nullable = false)
    private Set<Shop> shops;

    @Getter
    @OneToMany
    @JoinColumn(name="venue_id", nullable = false)
    private Set<VenueManager> venueManagers;

    @Getter
    @Column(nullable = false)
    private Date createdAt;

    @Getter
    @Column(nullable = false)
    private Date updatedAt;
}
