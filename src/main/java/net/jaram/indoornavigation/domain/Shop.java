package net.jaram.indoornavigation.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Shop extends BaseTimeEntity {
    @Id
    @Getter
    private String uuid;

    @Getter
    @Setter
    @Column(nullable = false)
    private String shopName;

    @Getter
    @Column(nullable = false)
    private Integer floor;

    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Getter
    @Setter
    @Column(nullable = false)
    private String description;
}
