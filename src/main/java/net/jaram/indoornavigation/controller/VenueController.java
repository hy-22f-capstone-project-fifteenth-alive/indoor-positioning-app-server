package net.jaram.indoornavigation.controller;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.dto.UploadFileResponse;
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

@RestController
@RequestMapping("/api/v1/venue")
@RequiredArgsConstructor
public class VenueController {
    private final FileService fileService;

    @GetMapping
    ResponseEntity<List<VenueResponse>> getVenueByBeaconId(@RequestParam String beaconId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/map")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFileResponse uploadFileResponse = null;
        try {
            uploadFileResponse = fileService.upload(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadFileResponse;
    }

    @GetMapping(path = "/map")
    public ResponseEntity<ByteArrayResource> getVenueMapData(@RequestParam String fileName) {
        try {
            byte[] data = fileService.download(fileName);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "\"")
                    .body(resource);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }
}
