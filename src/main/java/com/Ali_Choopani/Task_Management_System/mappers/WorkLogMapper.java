package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.comment.CommentSummary;
import com.Ali_Choopani.Task_Management_System.dto.comment.CreateCommentRequest;
import com.Ali_Choopani.Task_Management_System.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface CommentMapper {

    Comment toEntity(CreateCommentRequest request);

    @Mapping(target = "authorId", source = "author.member.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentSummary toSummary(Comment entity);
}
