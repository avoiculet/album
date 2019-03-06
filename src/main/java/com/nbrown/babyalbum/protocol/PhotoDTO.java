package com.nbrown.babyalbum.protocol;

import com.nbrown.babyalbum.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by avoiculet on 06/03/2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private String clientId;

    private String latitude;

    private String longitude;

    private LocalDateTime dateTime;

    private String fileName;

    private String city;

    public static PhotoDTO from(Photo save) {
        return new PhotoDTO(save.getClientId(), save.getLatitude(), save.getLongitude(), save.getDate(), save.getFileName(), save.getCity());
    }
}
