package com.Ali_Choopani.Task_Management_System.services.user.profile;

import com.Ali_Choopani.Task_Management_System.dto.user.profile.CompleteProfileFieldsRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.UpdateProfileFieldsRequest;

public interface ProfileService {

    ProfileSummary completeOrUpdateProfileFields(CompleteProfileFieldsRequest request, Long userId);
    ProfileSummary updateProfileFields(UpdateProfileFieldsRequest request, Long userId);
    ProfileSummary displayUserProfileView(Long userId);
}
