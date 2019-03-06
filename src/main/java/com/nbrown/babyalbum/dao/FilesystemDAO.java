package com.nbrown.babyalbum.dao;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.nbrown.babyalbum.util.Constants.UPLOAD_FOLDER;

/**
 * Created by avoiculet on 06/03/2019.
 */
@Component
public class FilesystemDAO implements FileDAO {

    @Override
    public void save(MultipartFile file, String fileName) throws IOException {
        byte[] bytes = file.getBytes();
        Files.write(getPath(fileName), bytes);
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        return Files.readAllBytes(getPath(fileName));
    }

    @Override
    public void delete(String fileName) throws IOException {
        Files.delete(getPath(fileName));
    }

    private Path getPath(String fileName) {
        return Paths.get(UPLOAD_FOLDER + fileName);
    }
}
