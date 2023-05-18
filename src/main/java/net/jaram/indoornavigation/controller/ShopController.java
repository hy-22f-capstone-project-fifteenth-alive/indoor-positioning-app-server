package net.jaram.indoornavigation.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.domain.Shop;
import net.jaram.indoornavigation.dto.ShopResponse;
import net.jaram.indoornavigation.repository.ShopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopRepository shopRepository;

    @Operation(summary = "상점 목록 정보 조회")
    @GetMapping("/{venue_id}")
    ResponseEntity<List<ShopResponse>> getShopList(@PathVariable(name = "venue_id") Long venueId) {
        List<ShopResponse> shopResponse = shopRepository.findAllByVenueId(venueId)
                .stream()
                .map(ShopResponse::new)
                .toList();

        if (shopResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(shopResponse);
    }

    @Operation(summary = "특정 Shop 정보 조회")
    @GetMapping("/{shop_id}")
    ResponseEntity<Optional<ShopResponse>> getShop(@PathVariable(name = "shop_id") Long shopId) {
        Optional<ShopResponse> shopResponse = shopRepository.findById(shopId)
                .map(ShopResponse::new);

        if (shopResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(shopResponse);
    }

    @Operation(summary = "특정 Shop 정보 수정")
    @PutMapping("/{shop_id}")
    ShopResponse updateShop (
            @PathVariable(name = "shop_id") Long shopId,
            String shopName,
            String description
    ) {
        ShopResponse shopResponse;
        Shop shop = shopRepository.findById(shopId).orElseThrow(RuntimeException::new);

        shop.setShopName(shopName);
        shop.setDescription(description);

        try {
            shopRepository.save(shop);
            shopResponse = new ShopResponse(shop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return shopResponse;
    }
}
