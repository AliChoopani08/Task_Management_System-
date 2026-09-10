package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.task.AssigneeSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.ProjectTasksSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskDetails;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE,
uses = ProjectMemberMapper.class)
public interface TaskMapper {

    Task toEntity(CreateTaskRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "assignee", expression = "java(hasAssignee(entity))")
    @Mapping(target = "project", source = "projectManager")
    TaskDetails toSummary(Task entity, ProjectMember projectManager);

    @Mapping(target = "id", source = "assignee.member.id")
    @Mapping(target = "fullName", expression = "java(entity.assignee.getMember().getProfile().getFullName())")
    AssigneeSummary toAssigneeSummary(Task entity);

    @Mapping(target = "taskId", source = "id")
    @Mapping(target = "taskTitle", source = "title")
    @Mapping(target = "assigneeId", expression = "java(getAssigneeId(entity))")
    @Mapping(target = "assigneeFullName", expression = "java(getAssigneeFullName(entity))")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectTitle", source = "project.title")
    ProjectTasksSummary toProjectTaskSummary(Task entity);

    default AssigneeSummary hasAssignee(Task entity) {
        if (entity.getAssignee() != null) {
            return toAssigneeSummary(entity);
        }
        else return null;
    }
    default Long getAssigneeId(Task entity) {
        if (entity.getAssignee() != null) {
            return toAssigneeSummary(entity).id();
        }
        else return null;
    }
    default String getAssigneeFullName(Task entity) {
        if (entity.getAssignee() != null) {
            return toAssigneeSummary(entity).fullName();
        }
        else return null;
    }
    @Condition
    default boolean isNotEmptyAndBlank(String value) {
        return value != null && !value.isBlank();
    }
    @Condition
    default boolean isAssigneeNull(ProjectMember assignee) {
        return assignee == null;
    }

}
