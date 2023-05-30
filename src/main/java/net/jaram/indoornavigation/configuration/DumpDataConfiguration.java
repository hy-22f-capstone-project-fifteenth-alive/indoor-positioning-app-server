package net.jaram.indoornavigation.configuration;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.domain.Beacon;
import net.jaram.indoornavigation.domain.Shop;
import net.jaram.indoornavigation.domain.Venue;
import net.jaram.indoornavigation.domain.VenueMapData;
import net.jaram.indoornavigation.repository.ShopRepository;
import net.jaram.indoornavigation.repository.VenueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class DumpDataConfiguration implements CommandLineRunner {

    private final VenueRepository venueRepository;
    private final ShopRepository shopRepository;

    @Override
    public void run(String... args) {
        Venue venue1 = Venue.builder()
                .venueName("테스트 용 머시기")
                .description("테스트용 맵입니다.")
                .build();
        Venue venue2 = Venue.builder()
                .venueName("한양대학교 ERICA 캠퍼스 3공학관")
                .description("한양대학교 ERICA 캠퍼스 3공학관입니다.")
                .build();
        Beacon beacon1 = Beacon.builder()
                .major(10001L)
                .minor(19641L)
                .floor("1")
                .build();
        Shop shop1 = Shop.builder()
                .uuid("a89da5f4-6e16-495d-a982-27a31af751d4")
                .shopName("101호")
                .floor(1)
                .description("소프트웨어 전공 학회 자람의 학회방입니다.")
                .build();
        Shop shop2 = Shop.builder()
                .uuid("a89da5f4-6e16-495d-a982-27a31af751d5")
                .shopName("102호")
                .floor(1)
                .description("소프트웨어 전공 학회 자람의 학회방입니다.")
                .build();
        Shop shop3 = Shop.builder()
                .uuid("a89da5f4-6e16-495d-a982-27a31af751d6")
                .shopName("103호")
                .floor(1)
                .description("소프트웨어 전공 학회 자람의 학회방입니다.")
                .build();
        Shop shop4 = Shop.builder()
                .uuid("734946bc-80b6-443e-9c00-39d2b42ad667")
                .shopName("104호")
                .floor(1)
                .description("소프트웨어 전공 학회 자람의 학회방입니다.")
                .build();
        Shop shop5 = Shop.builder()
                .uuid("4ada741f-1c54-4fd2-aec4-e5ef040c6c27")
                .shopName("105호")
                .floor(1)
                .description("소프트웨어 전공 학회 자람의 학회방입니다.")
                .build();
        VenueMapData map1 = VenueMapData.builder()
                .uuid("555aa55b-0e3c-4bb2-8a48")
                .size(500L)
                .build();
        VenueMapData map2 = VenueMapData.builder()
                .uuid("555aa55b-0e3c-4bb2-8a48-1c882893b36c")
                .size(500L)
                .build();
        venue1.addBeacon(beacon1);
        venue1.addShop(shop1);
        venue1.addShop(shop2);
        venue1.addShop(shop3);
        venue1.addShop(shop4);
        venue1.addShop(shop5);
        venue1.addVenueMapData(map1);
        venue2.addVenueMapData(map2);
        venueRepository.save(venue1);
        venueRepository.save(venue2);
        shopRepository.save(shop1);
        shopRepository.save(shop2);
        shopRepository.save(shop3);
        shopRepository.save(shop4);
        shopRepository.save(shop5);
    }
}