package com.Ali_Choopani.Task_Managment_System.services.user.profile;

import com.Ali_Choopani.Task_Managment_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.ProfileSummary;

public interface ProfileService {

    ProfileSummary completeProfileFields(CompleteOrUpdateProfileRequest request, Long userId);
}
