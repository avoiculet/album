package com.nbrown.babyalbum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//Improvements:
//1. Add indexes to client id
//2. Make client id in album table a foreign key to the user table.
//3. Make AlbumService fault tolerant by rolling back the save to the temp area if the repository fails to save.
//4. Add unique constraint to client id in the kid_user table
@SpringBootApplication
public class BabyalbumApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BabyalbumApplication.class, args);
    }

}
