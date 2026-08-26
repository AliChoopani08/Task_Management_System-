package com.Ali_Choopani.Task_Management_System.service;

import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Management_System.services.user.UserSpecification;
import com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.Ali_Choopani.Task_Management_System.services.user.UserSpecification.searchByNameOrEmail;
import static com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserSpecificationTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        final User user = createUser(null, "hasani_sajjad.123@gmail.com", "Afh@1239", "Sajjad Hasani");

        repository.save(user);
    }

    @Test
    void name() {
        final Specification<User> query = searchByNameOrEmail("Sajjad");

        final List<User> foundUser = repository.findAll(query);

        assertThat(foundUser.getFirst())
                .extracting(u -> u.getProfile().getFullName())
                .isEqualTo("Sajjad Hasani");
    }
}
