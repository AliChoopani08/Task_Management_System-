package com.Ali_Choopani.Task_Management_System.repository;

import com.Ali_Choopani.Task_Management_System.entities.Profile;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.repositories.ProfileRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository repository;
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email("ali.34.choupani@gmail.com")
                .password("Ali1@#Ch")
                .role(ROLE_USER)
                .build();

        Profile profile = Profile.builder()
                .firstName("Ali")
                .surname("Ahmadi")
                .build();
        profile.addProfileToUser(user);

        this.user = userRepository.save(user);
    }

    @Test
    void shouldFindByUserId_whenExists() {
        final Long userId = user.getId();

        final Optional<Profile> foundProfile = repository.findByUserId(userId);

        assertThat(foundProfile.isPresent()).isTrue();
        foundProfile.ifPresent(profile -> assertThat(profile)
                .extracting(Profile::getFirstName, p -> p.getUser().getId())
                .containsExactly("Ali", userId));
    }
}
