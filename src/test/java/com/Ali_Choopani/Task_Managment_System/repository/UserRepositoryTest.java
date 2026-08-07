package com.Ali_Choopani.Task_Managment_System.repository;

import com.Ali_Choopani.Task_Managment_System.entities.ProjectRole;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.entities.UserRole;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.Ali_Choopani.Task_Managment_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User entity;
    @BeforeEach
    void setUp() {
        entity = User.builder()
                .phoneNumber("09876543210")
                .password("Ali12345@CH")
                .role(ROLE_USER)
                .build();

        repository.save(entity);
    }

}
