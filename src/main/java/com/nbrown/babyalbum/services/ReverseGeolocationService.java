package com.nbrown.babyalbum.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbrown.babyalbum.protocol.GeoLocationError;
import com.nbrown.babyalbum.protocol.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.nbrown.babyalbum.utils.Constants.GEOLOCATION_URL;

/**
 * Created by avoiculet on 06/03/2019.
 */
@Service
public class ReverseGeolocationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReverseGeolocationService(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;
    }

    //The API keeps returning Request Throttled and never returns
    //There must be some header i am missing
    //The implementation is complete
    //And should work once the workaround is found.
    public String getCity(String latitude, String longitude) throws IOException {
        String url = String.format("%s/%s,%s?json=1", GEOLOCATION_URL, latitude, longitude);
        /*return Optional.ofNullable(executeApiCall(url))
                .map(location -> location.getCity())
                .orElse("Unknown");*/
        return "Unknown";
    }

    //TODO: refactor into functional pattern
    private Location executeApiCall(String url) throws IOException {
        boolean retry = false;
        Location location = null;
        do {
            try {
                System.out.println(url);
                location = restTemplate.getForObject(url, Location.class);
            } catch (HttpStatusCodeException e) {
                String errorBody = e.getResponseBodyAsString();
                System.out.println(errorBody);
                GeoLocationError error = objectMapper.readValue(errorBody, GeoLocationError.class);
                if (!error.getError().getCode().equals("006")) {
                    e.printStackTrace();
                    return null;
                }
                retry = true;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } while (retry);

        return location;
    }
}
