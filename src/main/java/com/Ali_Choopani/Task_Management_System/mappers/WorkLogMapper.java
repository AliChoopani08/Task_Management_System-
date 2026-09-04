package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.workLog.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogDetails;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogSummary;
import com.Ali_Choopani.Task_Management_System.entities.WorkLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface WorkLogMapper {

    WorkLog toEntity(CreateWorkLogRequest request);

    @Mapping(target = "authorId", source = "author.member.id")
    @Mapping(target = "authorName", expression = "java(entity.getAuthor().getMember().getProfile().getFullName())")
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "taskTitle", source = "task.title")
    WorkLogDetails toDetails(WorkLog entity);

    WorkLogSummary toSummary(WorkLog entity);
}
