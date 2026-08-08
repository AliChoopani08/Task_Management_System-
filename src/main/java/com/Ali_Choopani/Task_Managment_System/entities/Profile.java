package com.Ali_Choopani.Task_Managment_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static java.time.Period.between;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    private String firstName;
    private String surname;
    private LocalDate birthDate;
    private String biography;


    public void addProfileToUser(User user) {
        this.setUser(user);
        user.setProfile(this);
    }


    public LocalDate getAge() {
        final Period ageDifference = between(this.getBirthDate(), now());

        return of(ageDifference.getYears(), ageDifference.getMonths(), ageDifference.getDays());
    }
}
