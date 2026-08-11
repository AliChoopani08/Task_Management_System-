package com.Ali_Choopani.Task_Managment_System.service;

import com.Ali_Choopani.Task_Managment_System.TestMocksHelper.MockWhenHelper;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Profile;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.entities.UserRole;
import com.Ali_Choopani.Task_Managment_System.mappers.ProfileMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.ProfileRepository;
import com.Ali_Choopani.Task_Managment_System.services.user.profile.ProfileServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.Ali_Choopani.Task_Managment_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository repository;
    @Mock
    private ProfileMapper mapper;

    @InjectMocks
    private ProfileServiceImpl service;

    private Profile profile;
    private ProfileSummary summary;

    @BeforeEach
    void setUp() {
        profile = Profile.builder()
                .id(1L)
                .firstName("Amin")
                .surname("Mohammadi")
                .birthDate(of(2009, 9, 17))
                .biography("Front End Developer")
                .build();

        summary = ProfileSummary.builder()
                .id(1L)
                .firstName("Amin")
                .surname("Mohammadi")
                .biography("Front End Developer")
                .userId(1L)
                .build();
    }

    @Test
    void shouldCompleteTheProfileFields_whenProfileExists() {
        CompleteOrUpdateProfileRequest request = CompleteOrUpdateProfileRequest.builder()
                .firstName("Amin")
                .surname("Mohammadi")
                .birthDate(of(2009, 9, 17))
                .biography("Front End Developer")
                .build();
        Long userId = 1L;


        whenHelper(repository.findByUserId(anyLong()), Optional.of(profile));
        doAnswer(invocation ->  {
            Profile profile = invocation.getArgument(0);
            CompleteOrUpdateProfileRequest dto = invocation.getArgument(1);

            profile = Profile.builder()
                    .firstName(dto.getFirstName())
                    .surname(dto.getSurname())
                    .birthDate(dto.getBirthDate())
                    .biography(dto.getBiography())
                    .build();
            return null;
        }).when(mapper).updateProfile(any(Profile.class), any(CompleteOrUpdateProfileRequest.class));
        whenHelper(repository.save(any(Profile.class)), profile);
        whenHelper(mapper.toSummary(any(Profile.class)), summary);

        final ProfileSummary response = service.completeProfileFields(request, userId);

        assertThat(response)
                .extracting(ProfileSummary::firstName, ProfileSummary::userId)
                .containsExactly("Amin", userId);

        verify(mapper).updateProfile(profile, request);
    }
}
