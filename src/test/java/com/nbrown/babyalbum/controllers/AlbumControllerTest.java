package com.nbrown.babyalbum.controllers;

import com.nbrown.babyalbum.services.AlbumService;
import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * Created by avoiculet on 05/03/2019.
 */
public class AlbumControllerTest {

    private AlbumController albumController;
    private AlbumService albumService;

    @Before
    public void setUp() {
        this.albumService = mock(AlbumService.class);
        this.albumController = new AlbumController(albumService);
    }

    //For sake of time taken to complete this exercise, i have ommitted tests for Album
}
