package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Management_System.entities.Project;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring"
,nullValuePropertyMappingStrategy = IGNORE)
public interface ProjectMapper {

    Project toEntity(CreateProjectRequest request);

    @Condition
    default boolean isNotEmptyAndBlank(String value) {
        return value != null && !value.isBlank();
    }
}
