package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.ProjectSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring"
,nullValuePropertyMappingStrategy = IGNORE)
public interface ProjectMapper {

    Project toEntity(CreateProjectRequest request);
}
