package com.Ali_Choopani.Task_Management_System.security;

import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.util.Collections.singleton;

@DataJpaTest
@ActiveProfiles("test")
public class UserDetailServiceImplTest {

    private UserDetailServiceImpl detailsService;
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        detailsService = new UserDetailServiceImpl(repository);
    }

    @Test
    void shouldLoadUserById() {
        User user = User.builder()
                .phoneNumber("09876543210")
                .password("ALI123@gm")
                .role(ROLE_USER)
                .build();
        final User savedUser = repository.save(user);

        final UserDetails foundUser = detailsService.loadUserById(savedUser.getId());

        Assertions.assertThat(foundUser)
                .extracting(UserDetails::getUsername, UserDetails::getAuthorities)
                .containsExactly("09876543210", singleton(new SimpleGrantedAuthority(ROLE_MANAGER.name())));
    }
}
