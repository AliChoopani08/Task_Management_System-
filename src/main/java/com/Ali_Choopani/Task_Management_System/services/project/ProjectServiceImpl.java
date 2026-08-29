package com.Ali_Choopani.Task_Management_System.services.project;

import com.Ali_Choopani.Task_Management_System.dto.project.*;
import com.Ali_Choopani.Task_Management_System.entities.Project;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.exceptions.project.DuplicateProjectMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.project.NotFoundProjectAndMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.project.NotFoundProjectException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.NotFoundUserException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.profile.ProfileNotCompletedException;
import com.Ali_Choopani.Task_Management_System.mappers.ProjectMapper;
import com.Ali_Choopani.Task_Management_System.mappers.ProjectMemberMapper;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProjectMapper projectMapper;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    @Transactional
    public ProjectSummary createAProject(CreateProjectRequest request, Long managerId) {
        final User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundUserException(managerId));
        if (!manager.isProfileCompleted()) {
            throw new ProfileNotCompletedException(manager.getId());}
        final Project project = projectMapper.toEntity(request);

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
    public ProjectMembersDetails addProjectMember(Long projectId, Long managerId, Long newMemberId, AddNewProjectMemberRequest request, Pageable pageable) {
        final ProjectMember projectManager = projectMemberRepository.findByProjectIdAndMemberIdAndRole(projectId, managerId, ROLE_MANAGER)
                .orElseThrow(() -> new NotFoundProjectAndMemberException(projectId, managerId, ROLE_MANAGER));
        final Project project = projectManager.getProject();
        final User newMember = userRepository.findById(newMemberId)
                .orElseThrow(() -> new NotFoundUserException(newMemberId));

        if (projectMemberRepository.existsByProjectIdAndMemberId(project.getId(), newMember.getId())) {
            throw new DuplicateProjectMemberException(newMember.getId(), project.getId());
        }

        ProjectMember newProjectMember = ProjectMember.builder()
                .role(valueOf(request.getMemberRole()))
                .build();
        newProjectMember.addProjectMember(newMember, project);
        projectMemberRepository.save(newProjectMember);

        final Page<MemberSummary> members = projectMemberRepository.findMembersOfProjectByProjectId(project.getId(), pageable);

        return new ProjectMembersDetails(project.getId(), project.getTitle(), members);
    }

    @Override
    public Set<MyProjectsSummary> getMyProjectsSummary(Long memberId) {
        final Set<ProjectMember> memberProjects = projectMemberRepository.findByMemberId(memberId);

        return projectMemberMapper.toMyProjectsSummary(memberProjects);
    }

    @Override
    public ProjectMembersDetails getProjectMembersDetails(Long projectId, Pageable pageable) {
        final Project project = repository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));
        final Page<MemberSummary> projectMembers = projectMemberRepository.findMembersOfProjectByProjectId(projectId, pageable);

        return new ProjectMembersDetails(project.getId(), project.getTitle(), projectMembers);
    }


}
