package com.nbrown.babyalbum.controller;

import com.nbrown.babyalbum.protocol.PhotoDTO;
import com.nbrown.babyalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.nbrown.babyalbum.util.Constants.CLIENT_ID;

/**
 * Created by avoiculet on 05/03/2019.
 */
@RestController
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/album")
    public PhotoDTO save(@RequestHeader(value = CLIENT_ID) String clientId,
                         @RequestParam("latitude") String latitude,
                         @RequestParam("longitude") String longitude,
                         @RequestParam("datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                         @RequestParam("file") MultipartFile file) throws IOException {
        return albumService.save(file, clientId, latitude, longitude, dateTime);
    }

    @GetMapping("/album")
    public List<PhotoDTO> getAll(@RequestHeader(value = CLIENT_ID) String clientId) {
        return albumService.getAll(clientId);
    }

    @DeleteMapping("/album/{fileName}")
    public void delete(@RequestHeader(value = CLIENT_ID) String clientId,
                       @PathVariable("fileName") String fileName) throws IOException {
        albumService.delete(clientId, fileName);
    }

    @GetMapping("/album/{fileName}")
    public ResponseEntity<Resource> download(@RequestHeader(value = CLIENT_ID) String clientId,
                                             @PathVariable("fileName") String fileName) throws IOException {
        return albumService.get(clientId, fileName);
    }
}
