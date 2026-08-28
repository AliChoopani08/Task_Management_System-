package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.project.*;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import org.mapstruct.*;

import java.util.Set;

import static java.lang.String.format;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface ProjectMemberMapper {

    @Mapping(target = "id", source = "project.id")
    @Mapping(target = "title", source = "project.title")
    @Mapping(target = "manager", source = ".")
    ProjectSummary toSummary(ProjectMember entity);

    @Mapping(target = "id", source = "member.id")
    @Mapping(target = "name", expression = "java(projectMember.getMember().getProfile().getFullName())")
    @Mapping(target = "role", source = "role")
    MemberSummary toMemberSummary(ProjectMember projectMember);

    @Mapping(target = "title", source = "project.title")
    @Mapping(target = "manager", source = "projectMember")
    ProjectDetails toProjectDetails(ProjectMember projectMember);

    @Mapping(target = "id", source = "project.id")
    @Mapping(target = "title", source = "project.title")
    MyProjectsSummary toMyProjectsSummary(ProjectMember entity);

    Set<MyProjectsSummary> toMyProjectsSummary(Set<ProjectMember> entity);
    }

