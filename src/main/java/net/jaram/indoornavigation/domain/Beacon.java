package net.jaram.indoornavigation.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Beacon extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    private Long major;

    @Getter
    private Long minor;

    @Getter
    private String floor;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}