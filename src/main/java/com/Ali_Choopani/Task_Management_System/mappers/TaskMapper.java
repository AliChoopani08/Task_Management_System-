package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.task.AssigneeSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE,
uses = ProjectMemberMapper.class)
public interface TaskMapper {

    Task toEntity(CreateTaskRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "assignee", source = "entity")
    @Mapping(target = "project", source = "projectManager")
    TaskSummary toSummary(Task entity, ProjectMember projectManager);

    @Mapping(target = "id", source = "assignee.member.id")
    @Mapping(target = "fullName", expression = "java(entity.assignee.getMember().getProfile().getFullName())")
    AssigneeSummary toAssigneeSummary(Task entity);


}
