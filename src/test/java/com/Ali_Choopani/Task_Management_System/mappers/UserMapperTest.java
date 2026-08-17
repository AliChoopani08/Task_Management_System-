package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RegisterRequest;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private final UserMapper mapper= Mappers.getMapper(UserMapper.class);
    private RegisterRequest request;

    @BeforeEach
    void setUp() {

        request = RegisterRequest.builder()
                .phoneNumber("09876543210")
                .email("Shdhfh123@gmai.com")
                .build();
    }

    @Test
    void shouldMapRequestToEntity() {
        final User entity = mapper.toEntity(request);

        System.out.println(entity);
        assertThat(entity)
                .extracting(User::getPhoneNumber, User::getRole)
                .containsExactly("09876543210", ROLE_MANAGER);
    }

    @Test
    void shouldMapUserDetailsToEntity() {
        UserDetailImpl userDetail = UserDetailImpl.builder()
                .id(2L)
                .username("09123456523")
                .password("Ali@ch123")
                .authorities(Collections.singleton(new SimpleGrantedAuthority(ROLE_MANAGER.name())))
                .build();

        final User entity = mapper.toEntity(userDetail);

        assertThat(entity)
                .extracting(User::getId, User::getPhoneNumber, User::getRole)
                .containsExactly(2L, "09123456523", ROLE_MANAGER);
    }
}
