package com.Ali_Choopani.Task_Management_System.service;

import com.Ali_Choopani.Task_Management_System.dto.project.*;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.exceptions.project.DuplicateProjectMemberException;
import com.Ali_Choopani.Task_Management_System.mappers.ProjectMapper;
import com.Ali_Choopani.Task_Management_System.mappers.ProjectMemberMapper;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Management_System.services.project.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.Ali_Choopani.Task_Management_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectMapper mapper;
    @Mock
    private ProjectMemberMapper projectMemberMapper;

    @InjectMocks
    private ProjectServiceImpl service;

    private User member;
    private Project project;
    private ProjectMember projectManager;
    private ProjectMember projectMember;
    private ProjectSummary summaryManager;
    private ProjectSummary summaryMember;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(1L)
                .title("Company Website")
                .description("Create a responsive company website to showcase services and contact information.")
                .startDate(of(2026, 3, 2))
                .dueDate(of(2026, 11, 2))
                .build();

       projectManager = createMember(2L, "09876543210", "Ali Choopani", ROLE_MANAGER, project);
       projectMember = createMember(3L, "09123456789", "Mohammad Majd", ROLE_DEVELOPER, project);

        summaryManager = ProjectSummary.builder()
                .title("Company Website")
                .description("Create a responsive company website to showcase services and contact information")
                .startDate(of(2026, 3, 2))
                .dueDate(of(2026, 11, 2))
                .manager(new MemberSummary(1L, "Ali Choopani", ROLE_MANAGER))
                .build();
        summaryMember = summaryManager.toBuilder()
                .manager(new MemberSummary(3L, "Mohammad Majd", ROLE_DEVELOPER))
                .build();
    }

    @Test
    void shouldCreateANewProject_whenProfileHasBeenCompletedAndUserExists() {
        CreateProjectRequest request = CreateProjectRequest.builder()
                .title("Company Website")
                .description("Create a responsive company website to showcase services and contact information")
                .dueDate(of(2026, 11, 2))
                .build();
        final Long memberId = member.getId();

        whenHelper(userRepository.findById(anyLong()), Optional.of(member));
        whenHelper(mapper.toEntity(any(CreateProjectRequest.class)), project);
        whenHelper(projectMemberRepository.existsByMemberIdAndRoleAndProjectTitle(anyLong(),
                any(ProjectRole.class), anyString()), Optional.empty());
        whenHelper(projectMemberRepository.save(any(ProjectMember.class)), projectManager);
        whenHelper(projectMemberMapper.toSummary(any(ProjectMember.class)), summaryManager);

        final ProjectSummary response = service.createAProject(request, memberId);

        assertThat(response)
                .extracting(ProjectSummary::title, p -> p.manager().name(), ProjectSummary::dueDate)
                .containsExactly("Company Website", "Ali Choopani", of(2026, 11, 2));
    }

    @Test
    void shouldAddProjectMember_whenProjectAndMemberExist() {
        final User member = projectMember.getMember();
        AddNewProjectMemberRequest request = AddNewProjectMemberRequest.builder()
                .memberRole(ROLE_DEVELOPER.name())
                .build();
        final User manager = projectManager.getMember();
        final Long projectId = project.getId();
        final Set<MemberSummary> expectedMembersOfProject = Set.of(new MemberSummary(2L, "Mohammad Majd", ROLE_DEVELOPER));

        whenHelper(projectMemberRepository.findByProjectIdAndMemberIdAndRole(anyLong(), anyLong(), any(ProjectRole.class)), Optional.of(projectManager));
        whenHelper(userRepository.findById(anyLong()), Optional.of(member));
        whenHelper(projectMemberRepository.existsByProjectIdAndMemberId(anyLong(), anyLong()), false);
        whenHelper(projectMemberRepository.save(any(ProjectMember.class)), projectMember);
        whenHelper(projectMemberMapper.toSummary(any(ProjectMember.class)), summaryManager);
        whenHelper(projectMemberRepository.findMembersOfProjectByProjectId(anyLong()), expectedMembersOfProject);

        final ProjectDetails response = service.addProjectMember(projectId, manager.getId(), member.getId(), request);

        assertThat(response)
                .extracting(p -> p.project().title(), p -> p.project().manager().name(),
                        p -> p.members().iterator().next().name())
                .containsExactly("Company Website", "Ali Choopani", "Mohammad Majd");
    }

    @Test
    void shouldThrowException_whenUserWantsToAddADuplicateProjectMember() {
        final User member = projectMember.getMember();
        AddNewProjectMemberRequest request = AddNewProjectMemberRequest.builder()
                .memberRole(ROLE_DEVELOPER.name())
                .build();
        final User manager = projectManager.getMember();
        final Long projectId = project.getId();

        whenHelper(projectMemberRepository.findByProjectIdAndMemberIdAndRole(anyLong(), anyLong(), any(ProjectRole.class)), Optional.of(projectManager));
        whenHelper(userRepository.findById(anyLong()), Optional.of(member));
        whenHelper(projectMemberRepository.existsByProjectIdAndMemberId(anyLong(), anyLong()), true);

        assertThatThrownBy(() -> service.addProjectMember(projectId, manager.getId(), member.getId(), request))
                .isInstanceOf(DuplicateProjectMemberException.class)
                .hasMessage("User with id [3] is already an active member of project with id  [1] !");

    }

    private ProjectMember createMember(Long id, String phoneNumber, String fullName, ProjectRole role, Project project) {
        User member = User.builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .role(ROLE_USER)
                .build();
        Profile profile = Profile.builder()
                .firstName(fullName.split(" ") [0])
                .surname(fullName.split(" ") [1])
                .build();
        profile.addProfileToUser(member);
        member.setProfileCompleted(true);

        ProjectMember projectMember = ProjectMember.builder()
                .role(role)
                .build();
        projectMember.addProjectMember(member, project);

        return projectMember;
    }
}
