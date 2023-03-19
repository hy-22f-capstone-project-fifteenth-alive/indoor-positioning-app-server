package net.jaram.indoornavigation.configuration;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.domain.Beacon;
import net.jaram.indoornavigation.domain.Shop;
import net.jaram.indoornavigation.domain.Venue;
import net.jaram.indoornavigation.repository.VenueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class DumpDataConfiguration implements CommandLineRunner {

    private final VenueRepository venueRepository;

    @Override
    public void run(String... args) {
        Venue venue1 = Venue.builder()
                .venueName("한양대학교 ERICA 캠퍼스 3공학관")
                .description("한양대학교 ERICA 캠퍼스 3공학관입니다.")
                .build();
        Beacon beacon1 = Beacon.builder()
                .uuid("568aa15b-0e3c-4bb2-8a48-1c882893b36c")
                .build();
        Shop shop1 = Shop.builder()
                .shopName("웹실")
                .description("소프트웨어 전공 학회 자람의 학회방입니다.")
                .build();
        venue1.addBeacon(beacon1);
        venue1.addShop(shop1);
        venueRepository.save(venue1);
    }
}