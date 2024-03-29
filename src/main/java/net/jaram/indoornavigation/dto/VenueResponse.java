package net.jaram.indoornavigation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaram.indoornavigation.domain.Venue;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VenueResponse {
    private Long id;
    private String name;
    private String description;

    public VenueResponse(Venue venue) {
        id = venue.getId();
        name = venue.getVenueName();
        description = venue.getDescription();
    }
}
