package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Profile;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class ProfileMapperTest {

    private final ProfileMapper mapper = Mappers.getMapper(ProfileMapper.class);

    private Profile profile;
    private User user;

    @BeforeEach
    void setUp() {
        user =  User.builder()
                .id(1L)
                .phoneNumber("09876543210")
                .build();
        profile = Profile.builder()
                .id(2L)
                .firstName("Mohammad")
                .surname("Rezaee")
                .birthDate(of(2003,3,25))
                .build();
        profile.addProfileToUser(user);
    }

    @Test
    void shouldMapRequestToEntity() {
        CompleteOrUpdateProfileRequest request = CompleteOrUpdateProfileRequest.builder()
                .firstName("Mohammad")
                .surname("Rezaee")
                .birthDate(of(2003,3,25))
                .build();

        final Profile entity = mapper.toEntity(request);

        assertThat(entity)
                .extracting(Profile::getFirstName)
                .isEqualTo("Mohammad" );
    }

    @Test
    void shouldUpdateProfileName() {
        CompleteOrUpdateProfileRequest request = CompleteOrUpdateProfileRequest.builder()
                .firstName("Akbar")
                .build();
        mapper.updateProfile(profile, request);

        assertThat(profile)
                .extracting(Profile::getFirstName, Profile::getSurname)
                .containsExactly("Akbar", "Rezaee");
    }

    @Test
    void shouldMapEntityToSummary() {
        final Long userId = user.getId();

        final ProfileSummary summary = mapper.toSummary(profile);

        assertThat(summary)
                .extracting(ProfileSummary::firstName, ProfileSummary::userId)
                .containsExactly("Mohammad", userId);

    }
}
