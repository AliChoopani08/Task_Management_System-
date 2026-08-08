package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring"
, nullValuePropertyMappingStrategy = IGNORE)
public interface ProfileMapper {

    Profile toEntity(CompleteOrUpdateProfileRequest request);

    void updateProfile(@MappingTarget Profile profile, CompleteOrUpdateProfileRequest request);

    @Mapping(target = "userId", source = "user.id")
    ProfileSummary toSummary(Profile profile);
}
