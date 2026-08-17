package com.Ali_Choopani.Task_Management_System.repository;

import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
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
