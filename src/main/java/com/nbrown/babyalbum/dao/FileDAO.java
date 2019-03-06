package com.nbrown.babyalbum.dao;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by avoiculet on 06/03/2019.
 */
public interface FileDAO {
    void save(MultipartFile file, String fileName) throws IOException;

    byte[] getFile(String fileName) throws IOException;

    void delete(String fileName) throws IOException;
}
