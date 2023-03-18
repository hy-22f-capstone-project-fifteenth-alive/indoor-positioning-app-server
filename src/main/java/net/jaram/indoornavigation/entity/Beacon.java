package net.jaram.indoornavigation.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Beacon implements Serializable {
    @Id
    private String uuid;

    @Getter
    @Column(nullable = false)
    private Date createdAt;

    @Getter
    @Column(nullable = false)
    private Date updatedAt;
}