package net.jaram.indoornavigation.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class VenueMapData extends BaseTimeEntity {
    @Id
    @Getter
    private String uuid;

    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(nullable = false)
    private Long size;

    @Setter
    @Column
    private String description;
}
