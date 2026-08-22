package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.project.MemberSummary;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import org.mapstruct.*;

import static java.lang.String.format;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface ProjectMemberMapper {

    @Mapping(target = "id", source = "project.id")
    @Mapping(target = "title", source = "project.title")
    @Mapping(target = "description", source = "project.description")
    @Mapping(target = "startDate", source = "project.startDate")
    @Mapping(target = "dueDate", source = "project.dueDate")
    @Mapping(target = "manager", source = ".")
    ProjectSummary toSummary(ProjectMember entity);

    @Mapping(target = "id", source = "member.id")
    @Mapping(target = "name", expression = "java(projectMember.getMember().getProfile().getFullName())")
    @Mapping(target = "role", source = "role")
    MemberSummary toMemberSummary(ProjectMember projectMember);

    }

