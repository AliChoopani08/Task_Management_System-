package com.Ali_Choopani.Task_Managment_System.service;

import com.Ali_Choopani.Task_Managment_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.project.ProjectMemberSummary;
import com.Ali_Choopani.Task_Managment_System.entities.*;
import com.Ali_Choopani.Task_Managment_System.mappers.ProjectMapper;
import com.Ali_Choopani.Task_Managment_System.mappers.ProjectMemberMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Managment_System.services.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.Ali_Choopani.Task_Managment_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Managment_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
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
    private ProjectMember projectMember;
    private ProjectMemberSummary summary;

    @BeforeEach
    void setUp() {
    member = User.builder()
            .id(2L)
            .email("AliCh1387_12@gmail.com")
            .role(ROLE_USER)
            .build();
        Profile profile = Profile.builder()
                .firstName("Ali")
                .surname("Choopani")
                .build();
        profile.addProfileToUser(member);
        member.setProfileCompleted(true);

        project = Project.builder()
                .title("Company Website")
                .description("Create a responsive company website to showcase services and contact information.")
                .startDate(of(2026, 3, 2))
                .dueDate(of(2026, 11, 2))
                .build();

        projectMember = ProjectMember.builder()
                .role(ROLE_DEVELOPER)
                .build();
        projectMember.addProjectMember(member, project);

        summary = ProjectMemberSummary.builder()
                .title("Company Website")
                .description("Create a responsive company website to showcase services and contact information")
                .startDate(of(2026, 3, 2))
                .dueDate(of(2026, 11, 2))
                .managerName("Ali Choopani")
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
        whenHelper(projectMemberRepository.save(any(ProjectMember.class)), projectMember);
        whenHelper(projectMemberMapper.toSummary(any(ProjectMember.class)), summary);

        final ProjectMemberSummary response = service.createAProject(request, memberId);

        assertThat(response)
                .extracting(ProjectMemberSummary::title, ProjectMemberSummary::managerName, ProjectMemberSummary::dueDate)
                .containsExactly("Company Website", "Ali Choopani", of(2026, 11, 2));
    }
}
