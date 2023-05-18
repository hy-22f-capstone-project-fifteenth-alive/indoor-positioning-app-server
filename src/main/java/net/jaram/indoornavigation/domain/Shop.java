package net.jaram.indoornavigation.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Shop extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String shopName;

    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Getter
    @Setter
    @Column(nullable = false)
    private String description;
}
