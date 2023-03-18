package net.jaram.indoornavigation.controller;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.dto.VenueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venue")
@RequiredArgsConstructor
public class VenueController {
    @GetMapping
    ResponseEntity<List<VenueResponse>> getVenueList() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
