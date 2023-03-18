package net.jaram.indoornavigation.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
