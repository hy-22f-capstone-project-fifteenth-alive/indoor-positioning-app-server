package net.jaram.indoornavigation.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.domain.Shop;
import net.jaram.indoornavigation.domain.Venue;
import net.jaram.indoornavigation.dto.ShopResponse;
import net.jaram.indoornavigation.repository.ShopRepository;
import net.jaram.indoornavigation.repository.VenueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor
public class ShopController {

    private final VenueRepository venueRepository;
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

    @Operation(summary = "특정 층 Shop 목록 정보 조회")
    @GetMapping("/{venue_id}/floor/{floor}")
    ResponseEntity<List<ShopResponse>> getShopListByOrdinal(
            @PathVariable(name = "venue_id") Long venueId,
            @PathVariable(name = "floor") int floor
    ) {
        List<ShopResponse> shopResponse = shopRepository.findAllByVenueIdAndFloor(venueId, floor)
                .stream()
                .map(ShopResponse::new)
                .toList();

//        if (shopResponse.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
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

    @Operation(summary = "특정 Shop 정보 추가")
    @PostMapping
    ResponseEntity<Optional<ShopResponse>> createShop(
            Long venueId,
            String uuid,
            String name,
            Integer floor
    ) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(RuntimeException::new);

        Optional<Shop> shopOptional = shopRepository.findByUuid(uuid);
        System.out.println(shopOptional);

        Shop shop = Shop.builder()
                .uuid(uuid)
                .shopName(name)
                .floor(floor)
                .description("")
                .venue(venue)
                .build();

        try {
            venue.addShop(shop);
            venueRepository.save(venue);
            shopRepository.save(shop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<ShopResponse> shopResponse = shopRepository.findByUuid(shop.getUuid())
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
