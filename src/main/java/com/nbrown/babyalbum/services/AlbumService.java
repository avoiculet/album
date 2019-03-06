package com.nbrown.babyalbum.services;

import com.nbrown.babyalbum.dao.FileDAO;
import com.nbrown.babyalbum.model.Photo;
import com.nbrown.babyalbum.protocol.PhotoDTO;
import com.nbrown.babyalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by avoiculet on 05/03/2019.
 */
@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FileDAO fileDAO;
    private final ReverseGeolocationService geoLocationService;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, FileDAO fileDAO, ReverseGeolocationService geoLocationService) {
        this.albumRepository = albumRepository;
        this.fileDAO = fileDAO;
        this.geoLocationService = geoLocationService;
    }

    public PhotoDTO save(MultipartFile file, String clientId, String latitude, String longitude, LocalDateTime dateTime) throws IOException {
        //Resillency condition here
        //There is a risk that between the save of the file
        //And saving the additional attributes, something may fail (e.g. DB offline)
        //In this case depending on the requirements
        //We may choose to remove the file or to continue
        //For this exercise I have chosen to continue and accept the result.
        String fileName = getFileName(file);
        fileDAO.save(file, fileName);
        return saveAdditionalInformation(fileName, clientId, latitude, longitude, dateTime);
    }

    private String getFileName(MultipartFile file) {
        //Can either get it from the multipart file or assign a random name
        if (file.getOriginalFilename() != null) {
            return file.getOriginalFilename();
        }
        return UUID.randomUUID().toString();
    }

    private PhotoDTO saveAdditionalInformation(String fileName, String clientId, String latitude, String longitude, LocalDateTime dateTime) throws IOException {
        Photo photo = Photo.builder()
                .clientId(clientId)
                .latitude(latitude)
                .longitude(longitude)
                .date(dateTime)
                .fileName(fileName)
                .city(geoLocationService.getCity(latitude, longitude))
                .build();
        return PhotoDTO.from(albumRepository.save(photo));
    }

    //Design choice. Keep full implementation is service to reduce number of IO calls to DB / Filesystem
    //If we would keep the formation of the response in the controller
    //We would need one call to check for the file size for the content length
    //Which involves a DB call to check if user owns the file + IO call to the filesystem
    public ResponseEntity<Resource> get(String clientId, String fileName) throws IOException {

        checkAuthorization(clientId, fileName);

        byte[] file = fileDAO.getFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName))
                .contentLength(file.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(file));
    }

    //Resillency condition here
    //There is a risk that between the delete of the file from the file system
    //And the removal of it from the db, something may fail (such as access to the db may be down)
    //In this case depending on the requirements
    //We may choose to rollback the file delete(by renaming it, and then deleting if operation is successful in a finally block)
    //or to continue
    //For this exercise I have chosen to continue and accept the result.
    public void delete(String clientId, String fileName) throws IOException {
        checkAuthorization(clientId, fileName);
        fileDAO.delete(fileName);
        albumRepository.deleteByClientIdAndFileNameIgnoreCase(clientId, fileName);
    }

    public List<PhotoDTO> getAll(String clientId) {
        return albumRepository
                .findAllByClientId(clientId)
                .map(this::mapPhotos)
                .get();
    }

    private List<PhotoDTO> mapPhotos(List<Photo> photos) {
        return photos
                .stream()
                .map(PhotoDTO::from)
                .collect(Collectors.toList());
    }

    private void checkAuthorization(String clientId, String fileName) {
        albumRepository
                .findByClientIdAndFileNameAllIgnoreCase(clientId, fileName)
                .orElseThrow(() -> new IllegalAccessError("Client id does not own this file"));
    }


}
