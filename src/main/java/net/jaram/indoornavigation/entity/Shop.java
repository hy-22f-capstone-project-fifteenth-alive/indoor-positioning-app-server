package net.jaram.indoornavigation.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(nullable = false)
    private String shopName;

    @Setter
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Getter
    @Column(nullable = false)
    private String description;

    @Getter
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Getter
    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    private Date updatedAt;
}
