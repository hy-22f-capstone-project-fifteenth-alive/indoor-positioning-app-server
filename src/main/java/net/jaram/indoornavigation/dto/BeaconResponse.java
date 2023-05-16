package net.jaram.indoornavigation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaram.indoornavigation.domain.Beacon;
import net.jaram.indoornavigation.domain.Venue;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BeaconResponse {
    private Long id;
    private Long major;
    private Long minor;
    private String floor;
    private VenueResponse venue;

    public BeaconResponse(Beacon beacon) {
        id = beacon.getId();
        major = beacon.getMajor();
        minor = beacon.getMinor();
        floor = beacon.getFloor();
        venue = new VenueResponse(beacon.getVenue());
    }
}
