package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.String.format;
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


    public String getAge() {
        final Period age = between(this.getBirthDate(), now());

        return String.format("%d years, %d months, %d days", age.getYears(), age.getMonths(), age.getDays());
    }

    public String getFullName() {
        return format("%s %s", this.getFirstName(), this.getSurname());
    }
}
