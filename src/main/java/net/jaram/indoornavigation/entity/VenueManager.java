package net.jaram.indoornavigation.entity;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class VenueManager extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(nullable = false)
    private Boolean isChief;

    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}
