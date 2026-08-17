package com.Ali_Choopani.Task_Management_System.repository;

import com.Ali_Choopani.Task_Management_System.dto.project.MemberSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@ActiveProfiles("test")
public class ProjectMemberRepositoryTest {

    @Autowired
    private ProjectMemberRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private User manager;
    private User developer;
    private Project project;
    private ProjectMember projectManager;
    private ProjectMember projectDeveloper;


    @BeforeEach
    void setUp() {
       Project project = Project.builder()
                .title("Implementation a store site")
                .description("Completing the initial version of site")
                .startDate(now())
                .dueDate(of(2026,7,20))
                .build();
        this.project = projectRepository.save(project);

        projectManager = addManagerToProject(this.project);
        projectDeveloper = addDeveloperToProject(project);

    }

    @Test
    void shouldFindByMemberIdAndProjectIdAndRole_whenExists() {
        final Long memberId = manager.getId();
        final Long projectId = project.getId();

        final Optional<ProjectMember> foundEntity = repository.findByMemberIdAndProjectIdAndRole(memberId,projectId, ROLE_MANAGER);

        assertThat(foundEntity.isPresent()).isTrue();
        foundEntity.ifPresent(pm -> assertThat(pm)
                .extracting(p -> p.getMember().getId(), p-> p.getProject().getId(), ProjectMember::getRole)
                .containsExactly(memberId, projectId, ROLE_MANAGER));
    }

    @Test
    void shouldFindByMemberIdAndRoleAndProjectTitle() {
        final Long memberId = manager.getId();
        final String projectTitle = project.getTitle();
        final ProjectRole role = projectManager.getRole();

        final Optional<ProjectMember> foundEntity = repository.existsByMemberIdAndRoleAndProjectTitle(memberId, role, projectTitle);

        assertThat(foundEntity.isPresent()).isTrue();
        foundEntity.ifPresent(pm -> assertThat(pm)
                .extracting(p -> p.getMember().getId(), p-> p.getProject().getTitle(), ProjectMember::getRole)
                .containsExactly(memberId, projectTitle, ROLE_MANAGER));
    }

    @Test
    void shouldFindAllMembersByProjectId_whenProjectExistsAndHasMembers() {
        final Long projectId = project.getId();

        final Set<MemberSummary> membersOfProject = repository.findMembersOfProjectByProjectId(projectId);

        assertThat(membersOfProject)
                .hasSize(1)
                .extracting(MemberSummary::name, MemberSummary::role)
                .containsExactly(tuple("Zahra Asadi", ROLE_DEVELOPER));
    }

    private ProjectMember addManagerToProject(Project project){
        User manager = User.builder()
                .password("Ali.12345.ch")
                .phoneNumber("09876543210")
                .role(ROLE_USER)
                .build();
        this.manager = userRepository.save(manager);

        ProjectMember projectMember = ProjectMember.builder()
                .role(ROLE_MANAGER)
                .build();
        projectMember.addProjectMember(manager, project);

        return repository.save(projectMember);
    }

    private ProjectMember addDeveloperToProject(Project project) {
        User developer = User.builder()
                .password("Zahra.Asadi@123")
                .phoneNumber("zahra_12345@gmail.com")
                .role(ROLE_USER)
                .build();
        Profile profile = Profile.builder()
                .firstName("Zahra")
                .surname("Asadi")
                .build();
        profile.addProfileToUser(developer);
        this.developer = userRepository.save(developer);

        ProjectMember projectMember = ProjectMember.builder()
                .role(ROLE_DEVELOPER)
                .build();
        projectMember.addProjectMember(developer, project);

        return repository.save(projectMember);
    }
}
