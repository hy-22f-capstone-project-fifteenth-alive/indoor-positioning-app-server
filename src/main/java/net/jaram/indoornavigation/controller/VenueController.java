package net.jaram.indoornavigation.controller;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.domain.Venue;
import net.jaram.indoornavigation.domain.VenueMapData;
import net.jaram.indoornavigation.dto.UploadFileResponse;
import net.jaram.indoornavigation.repository.VenueMapDataRepository;
import net.jaram.indoornavigation.repository.VenueRepository;
import net.jaram.indoornavigation.service.FileService;
import net.jaram.indoornavigation.dto.VenueResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/venue")
@RequiredArgsConstructor
public class VenueController {
    private final FileService fileService;

    private final VenueRepository venueRepository;
    private final VenueMapDataRepository venueMapDataRepository;

    @GetMapping
    ResponseEntity<List<VenueResponse>> getVenueByBeaconId(@RequestParam String beaconId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/map")
    public UploadFileResponse uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("venue_id") Long venueId
            ) {
        UploadFileResponse uploadFileResponse;
        Venue venue = venueRepository.findById(venueId).orElseThrow(RuntimeException::new);

        UUID uuid = UUID.randomUUID();
        try {
            uploadFileResponse = fileService.upload(file, uuid.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        VenueMapData venueMapData = VenueMapData.builder()
                .uuid(uuid.toString())
                .size(file.getSize())
                .build();
        venue.addVenueMapData(venueMapData);
        venueRepository.save(venue);

        return uploadFileResponse;
    }

    @GetMapping(path = "/map")
    public ResponseEntity<ByteArrayResource> getVenueMapData(
            @RequestParam(name="venue_id") Long venueId,
            @RequestParam(required = false) Optional<String> version
    ) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(RuntimeException::new);

        Optional<VenueMapData> venueMapData;
        if (version.isEmpty()) {
            venueMapData = venueMapDataRepository.findFirstByVenueOrderByCreatedAtDesc(venue);
        } else {
            venueMapData = venueMapDataRepository.findFirstByVenueAndUuid(venue, version.get());
        }

        try {
            byte[] data = fileService.download(venueMapData.orElseThrow(RuntimeException::new).getUuid());
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(venueMapData.get().getUuid() + ".imdf", StandardCharsets.UTF_8) + "\"")
                    .body(resource);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }
}
