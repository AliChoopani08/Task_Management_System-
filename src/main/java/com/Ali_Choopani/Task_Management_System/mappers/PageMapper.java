package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.PageResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface PageMapper {

    default <T> PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse<>(page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
