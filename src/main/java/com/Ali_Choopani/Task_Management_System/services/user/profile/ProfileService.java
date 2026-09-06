package com.Ali_Choopani.Task_Management_System.services.user.profile;

import com.Ali_Choopani.Task_Management_System.dto.user.profile.CompleteOrUpdateProfileFieldsRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileSummary;

public interface ProfileService {

    ProfileSummary completeOrUpdateProfileFields(CompleteOrUpdateProfileFieldsRequest request, Long userId);
    ProfileSummary displayUserProfileView(Long userId);
}
