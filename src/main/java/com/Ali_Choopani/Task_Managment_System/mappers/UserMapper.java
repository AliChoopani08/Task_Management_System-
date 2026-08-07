package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.RegisterRequest;
import com.Ali_Choopani.Task_Managment_System.dto.UserSummary;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.entities.UserRole;
import com.Ali_Choopani.Task_Managment_System.security.UserDetailImpl;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.GrantedAuthority;

import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.valueOf;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface UserMapper {

    User toEntity(RegisterRequest request);
    UserSummary toSummary(User entity);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    User toEntity(UserDetailImpl userDetail);
    @AfterMapping
    default void setRole(UserDetailImpl userDetail, @MappingTarget User user) {
        if (!userDetail.getAuthorities().isEmpty()) {
            final GrantedAuthority next = userDetail.getAuthorities().iterator().next();

            user.setRole(valueOf(next.getAuthority()));
        }
    }
    @AfterMapping
    default void setEmailOrPhone(UserDetailImpl detail, @MappingTarget User user) {
        if (detail.getUsername().contains("@")) {
            user.setEmail(detail.getUsername());
        }
        else
            user.setPhoneNumber(detail.getUsername());
    }
}
