package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.user.profile.CompleteProfileFieldsRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileFieldsMapper;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Management_System.entities.Profile;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring"
, nullValuePropertyMappingStrategy = IGNORE)
public interface ProfileMapper {

    Profile toEntity(CompleteProfileFieldsRequest request);

    void updateProfile(@MappingTarget Profile profile, ProfileFieldsMapper request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "age" , expression = "java(profile.getAge())")
    ProfileSummary toSummary(Profile profile);

    @Condition
    default boolean isNotEmptyAndBlank(String value) {
        return value != null && !value.isBlank();
    }
}
