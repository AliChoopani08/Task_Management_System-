package com.Ali_Choopani.Task_Managment_System.services.user.profile;

import com.Ali_Choopani.Task_Managment_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Profile;
import com.Ali_Choopani.Task_Managment_System.exceptions.NotFoundProfile;
import com.Ali_Choopani.Task_Managment_System.mappers.ProfileMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository repository;
    private final ProfileMapper mapper;

    @Override
    @Transactional
    public ProfileSummary completeProfileFields(CompleteOrUpdateProfileRequest request, Long userId) {
        final Profile profile = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundProfile(userId));

        mapper.updateProfile(profile, request);
        final Profile updatedProfile = repository.save(profile);

        return mapper.toSummary(updatedProfile);
     }
}
