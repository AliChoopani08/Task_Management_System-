package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.ProjectMemberSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Profile;
import com.Ali_Choopani.Task_Managment_System.entities.ProjectMember;
import org.mapstruct.*;

import static java.lang.String.format;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface ProjectMemberMapper {

    @Mapping(target = "title", source = "project.title")
    @Mapping(target = "description", source = "project.description")
    @Mapping(target = "startDate", source = "project.startDate")
    @Mapping(target = "dueDate", source = "project.dueDate")
    @Mapping(target = "managerId", source = "member.id")
    @Mapping(target = "managerName", expression = "java(getMemberName(entity.getMember().getProfile()))")
    ProjectMemberSummary toSummary(ProjectMember entity);

    default String getMemberName(Profile profile) {
        return format("%s %s", profile.getName(), profile.getLastName());
    }
    }

