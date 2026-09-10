package com.Ali_Choopani.Task_Management_System.security.customeAuthorization;

import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;

@Component("projectAuthorization")
@RequiredArgsConstructor
public class ProjectAuthorization {

    private final ProjectMemberRepository repository;

    public boolean isManager(Long projectId, Authentication authentication) {
        final Long userId = ((UserDetailImpl) authentication.getPrincipal())
                .getId();

        return repository.findByProjectIdAndMemberIdAndRole(projectId, userId, ROLE_MANAGER)
                .isPresent();
    }
}
