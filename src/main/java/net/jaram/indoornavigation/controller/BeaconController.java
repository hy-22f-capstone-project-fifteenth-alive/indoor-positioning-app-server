package net.jaram.indoornavigation.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.dto.BeaconResponse;
import net.jaram.indoornavigation.repository.BeaconRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/beacon")
@RequiredArgsConstructor
public class BeaconController {
    private final BeaconRepository beaconRepository;

    @Operation(summary = "beacon 정보로 venue 및 entry point 정보 조회")
    @GetMapping("/search")
    ResponseEntity<Optional<BeaconResponse>> searchEntryPoint(@RequestParam Long major, @RequestParam Long minor) {
        Optional<BeaconResponse> response = beaconRepository.findBeaconByMajorAndMinor(major, minor)
                .map(BeaconResponse::new);

        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }
}
