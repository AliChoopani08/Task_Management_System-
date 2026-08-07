package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.RefreshTokenSummary;
import com.Ali_Choopani.Task_Managment_System.entities.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface RefreshTokenMapper {

    RefreshTokenSummary toSummary(RefreshToken entity);
}
