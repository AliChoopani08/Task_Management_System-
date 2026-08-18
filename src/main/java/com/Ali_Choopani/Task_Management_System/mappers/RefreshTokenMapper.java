package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.Ali_Choopani.Task_Management_System.entities.RefreshToken;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface RefreshTokenMapper {

    RefreshTokenSummary toSummary(RefreshToken entity);
}
