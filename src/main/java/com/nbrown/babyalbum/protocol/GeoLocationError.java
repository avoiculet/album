package com.nbrown.babyalbum.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by avoiculet on 06/03/2019.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocationError {
    private boolean success;
    private GeoLocationSpecificError error;
}
