package net.jaram.indoornavigation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Manager {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(nullable = false)
    private Date createdAt;

    @Getter
    @Column(nullable = false)
    private Date updatedAt;
}
