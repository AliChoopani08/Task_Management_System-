package com.Ali_Choopani.Task_Management_System.testFactories;

import com.Ali_Choopani.Task_Management_System.entities.Profile;
import com.Ali_Choopani.Task_Management_System.entities.User;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;

public class UserTestFactory {

    public static User createUser(Long id, String identifier, String password, String fullName) {
        final String[] separatedFullName = fullName.split(" ");

        User.UserBuilder userBuilder = User.builder();
        if (identifier.contains("@"))
            userBuilder.email(identifier);
        else
            userBuilder.phoneNumber(identifier);

        User user = userBuilder
                .id(id)
                .password(password)
                .role(ROLE_USER)
                .build();

        Profile profile = Profile.builder()
                .firstName(separatedFullName[0])
                .surname(separatedFullName[1])
                .build();
        profile.addProfileToUser(user);

        return user;
    }
}
