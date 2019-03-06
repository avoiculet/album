package com.nbrown.babyalbum.protocol;

import com.nbrown.babyalbum.model.Gender;
import com.nbrown.babyalbum.model.KidUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by avoiculet on 05/03/2019.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KidUserDTO {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;

    public static KidUserDTO from(KidUser kidUser) {
        return new KidUserDTO(kidUser.getName(), kidUser.getDateOfBirth(), kidUser.getGender());
    }
}
