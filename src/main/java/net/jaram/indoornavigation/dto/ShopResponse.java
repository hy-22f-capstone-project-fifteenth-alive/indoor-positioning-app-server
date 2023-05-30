package net.jaram.indoornavigation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaram.indoornavigation.domain.Shop;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopResponse {
    private String uuid;
    private String name;
    private int floor;
    private String description;

    public ShopResponse(Shop shop) {
        uuid = shop.getUuid();
        name = shop.getShopName();
        floor = shop.getFloor();
        description = shop.getDescription();
    }
}