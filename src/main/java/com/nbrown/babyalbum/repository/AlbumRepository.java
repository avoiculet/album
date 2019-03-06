package com.nbrown.babyalbum.repository;

import com.nbrown.babyalbum.model.Photo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by avoiculet on 06/03/2019.
 */
public interface AlbumRepository extends CrudRepository<Photo, Long> {
    Optional<Photo> findByClientIdAndFileNameAllIgnoreCase(String clientId, String fileName);

    Optional<List<Photo>> findAllByClientId(String clientId);

    @Transactional
    void deleteByClientIdAndFileNameIgnoreCase(String clientId, String fileName);
}
