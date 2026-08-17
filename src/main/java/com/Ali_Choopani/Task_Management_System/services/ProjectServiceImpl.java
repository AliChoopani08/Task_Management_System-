package com.Ali_Choopani.Task_Management_System.services;

import com.Ali_Choopani.Task_Management_System.dto.project.*;
import com.Ali_Choopani.Task_Management_System.entities.Project;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.exceptions.project.DuplicateProjectMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.project.NotFoundProjectAndMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.NotFoundUserException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.profile.ProfileNotCompletedException;
import com.Ali_Choopani.Task_Management_System.mappers.ProjectMapper;
import com.Ali_Choopani.Task_Management_System.mappers.ProjectMemberMapper;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.valueOf;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository repository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectMapper mapper;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    @Transactional
    public ProjectSummary createAProject(CreateProjectRequest request, Long managerId) {
        final User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundUserException(managerId));
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

    @Override
    @Transactional
    public ProjectMemberSummary addProjecetMember(Long projectId, Long managerId, Long newMemberId, AddNewProjectMemberRequest request) {
        final ProjectMember projectMember = projectMemberRepository.findByMemberIdAndProjectIdAndRole(managerId, projectId, ROLE_MANAGER)
                .orElseThrow(() -> new NotFoundProjectAndMemberException(projectId, managerId, ROLE_MANAGER));
        final Project project = projectMember.getProject();
        final User newMember = userRepository.findById(newMemberId)
                .orElseThrow(() -> new NotFoundUserException(newMemberId));

        ProjectMember newProjectMember = ProjectMember.builder()
                .role(valueOf(request.getMemberRole()))
                .build();
        newProjectMember.addProjectMember(newMember, project);
        final ProjectMember savedNewProjectMember = projectMemberRepository.save(newProjectMember);

        final ProjectSummary projectSummary = projectMemberMapper.toSummary(savedNewProjectMember);
        final Set<MemberSummary> members = projectMemberRepository.findMembersOfProjectByProjectId(project.getId());

        return new ProjectMemberSummary(projectSummary, members);
    }


}
