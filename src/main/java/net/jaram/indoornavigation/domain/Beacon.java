package net.jaram.indoornavigation.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Beacon extends BaseTimeEntity {
    @Id
    private String uuid;

    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}