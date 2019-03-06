package com.nbrown.babyalbum.model;

import com.nbrown.babyalbum.protocol.KidUserDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by avoiculet on 05/03/2019.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class KidUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String clientId;

    public KidUser(String name, LocalDate dateOfBirth, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static KidUser from(KidUserDTO testUser) {
        return new KidUser(testUser.getName(), testUser.getDateOfBirth(), testUser.getGender());
    }
}
