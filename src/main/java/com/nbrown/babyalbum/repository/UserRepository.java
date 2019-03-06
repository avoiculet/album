package com.nbrown.babyalbum.repository;

import com.nbrown.babyalbum.model.KidUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by avoiculet on 05/03/2019.
 */
public interface UserRepository extends CrudRepository<KidUser, Long> {
    Optional<KidUser> findByClientId(String clientId);
}
