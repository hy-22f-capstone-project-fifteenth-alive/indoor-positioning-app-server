package net.jaram.indoornavigation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class VenueManager {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(nullable = false)
    private Boolean isChief;
}
