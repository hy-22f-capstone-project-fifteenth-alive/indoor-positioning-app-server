package net.jaram.indoornavigation.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.domain.Venue;
import net.jaram.indoornavigation.domain.VenueMapData;
import net.jaram.indoornavigation.dto.UploadFileResponse;
import net.jaram.indoornavigation.dto.VenueResponse;
import net.jaram.indoornavigation.repository.VenueMapDataRepository;
import net.jaram.indoornavigation.repository.VenueRepository;
import net.jaram.indoornavigation.service.FileService;
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

    @Operation(summary = "Venue 리스트 정보 조회")
    @GetMapping
    ResponseEntity<List<VenueResponse>> getVenueList() {
        List<VenueResponse> venueResponseList = venueRepository.findAll()
                .stream()
                .map(VenueResponse::new)
                .toList();

        return ResponseEntity.ok().body(venueResponseList);
    }

    @Operation(summary = "특정 Venue 정보 조회")
    @GetMapping("/{venue_id}")
    ResponseEntity<Optional<VenueResponse>> getVenue(@PathVariable(name = "venue_id") Long venueId) {
        Optional<VenueResponse> venueResponse = venueRepository.findById(venueId)
                .map(VenueResponse::new);

        if (venueResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(venueResponse);
    }

    @Operation(summary = "특정 Venue IMDF 파일 업로드")
    @PostMapping(value = "/{venue_id}/map", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse uploadFile(
            @PathVariable("venue_id") Long venueId,
            @RequestParam("file") MultipartFile file
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

    @Operation(summary = "특정 Venue IMDF 파일 다운로드")
    @GetMapping(path = "/{venue_id}/map")
    public ResponseEntity<ByteArrayResource> getVenueMapData(
            @PathVariable(name = "venue_id") Long venueId,
            @RequestParam(required = false) Optional<String> version
    ) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(RuntimeException::new);

        Optional<VenueMapData> venueMapData;
        if (version.isEmpty()) {
            venueMapData = venueMapDataRepository.findFirstByVenueOrderByCreatedAtDesc(venue);
        } else {
            venueMapData = venueMapDataRepository.findFirstByVenueAndUuid(venue, version.get());
        }

        if (venueMapData.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            try {
                byte[] data = fileService.download(venueMapData.get().getUuid());
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
}
