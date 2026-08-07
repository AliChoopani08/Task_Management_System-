package com.Ali_Choopani.Task_Managment_System.services;

import com.Ali_Choopani.Task_Managment_System.dto.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.ProjectMemberSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Project;
import com.Ali_Choopani.Task_Managment_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.exceptions.DuplicateProjectMemberException;
import com.Ali_Choopani.Task_Managment_System.exceptions.NotFoundUser;
import com.Ali_Choopani.Task_Managment_System.exceptions.ProfileNotCompletedException;
import com.Ali_Choopani.Task_Managment_System.mappers.ProjectMapper;
import com.Ali_Choopani.Task_Managment_System.mappers.ProjectMemberMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.Ali_Choopani.Task_Managment_System.entities.ProjectRole.ROLE_MANAGER;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectMapper mapper;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    @Transactional
    public ProjectMemberSummary createAProject(CreateProjectRequest request, Long managerId) {
        final User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundUser(managerId));
        if (!manager.isProfileCompleted()) {
            throw new ProfileNotCompletedException(manager.getId());}
        final Project project = mapper.toEntity(request);

        projectMemberRepository.existsByMemberIdAndRoleAndProjectTitle(manager.getId(), ROLE_MANAGER, project.getTitle())
                .ifPresent(__ -> {
                    throw new DuplicateProjectMemberException(manager.getId(), project.getId());});
        ProjectMember projectMember = ProjectMember.builder()
                .role(ROLE_MANAGER)
                .build();
        projectMember.addProjectMember(manager, project);
        final ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

        return projectMemberMapper.toSummary(savedProjectMember);
    }
}
