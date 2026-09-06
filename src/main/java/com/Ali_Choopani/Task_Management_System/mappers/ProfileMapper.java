package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.user.profile.CompleteOrUpdateProfileFieldsRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Management_System.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring"
, nullValuePropertyMappingStrategy = IGNORE)
public interface ProfileMapper {

    Profile toEntity(CompleteOrUpdateProfileFieldsRequest request);

    void updateProfile(@MappingTarget Profile profile, CompleteOrUpdateProfileFieldsRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "age" , expression = "java(profile.getAge())")
    ProfileSummary toSummary(Profile profile);
}
