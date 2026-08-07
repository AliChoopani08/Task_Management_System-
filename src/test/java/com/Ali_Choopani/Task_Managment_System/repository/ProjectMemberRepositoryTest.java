package com.Ali_Choopani.Task_Managment_System.repository;

import com.Ali_Choopani.Task_Managment_System.entities.Project;
import com.Ali_Choopani.Task_Managment_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Managment_System.entities.ProjectRole;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.ProjectRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.Ali_Choopani.Task_Managment_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProjectMemberRepositoryTest {

    @Autowired
    private ProjectMemberRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private User member;
    private Project project;
    private ProjectMember projectMember;

    @BeforeEach
    void setUp() {
        User member = User.builder()
                .password("Ali.12345.ch")
                .phoneNumber("09876543210")
                .role(ROLE_USER)
                .build();
        this.member = userRepository.save(member);

       Project project = Project.builder()
                .title("Implementation a store site")
                .description("Completing the initial version of site")
                .startDate(now())
                .dueDate(of(2026,7,20))
                .build();
        this.project = projectRepository.save(project);

        ProjectMember projectMember = ProjectMember.builder()
                .role(ROLE_MANAGER)
                .build();
        projectMember.addProjectMember(member, project);

       this.projectMember = repository.save(projectMember);
    }

    @Test
    void shouldFindByMemberIdAndProjectId_whenExists() {
        final Long memberId = member.getId();
        final Long projectId = project.getId();

        final Optional<ProjectMember> foundEntity = repository.findByProjectIdAndMemberId(projectId, memberId);

        assertThat(foundEntity.isPresent()).isTrue();
        foundEntity.ifPresent(pm -> assertThat(pm)
                .extracting(p -> p.getMember().getId(), p-> p.getProject().getId(), ProjectMember::getRole)
                .containsExactly(memberId, projectId, ROLE_MANAGER));
    }

    @Test
    void shouldFindByMemberIdAndRoleAndProjectTitle() {
        final Long memberId = member.getId();
        final String projectTitle = project.getTitle();
        final ProjectRole role = projectMember.getRole();

        final Optional<ProjectMember> foundEntity = repository.existsByMemberIdAndRoleAndProjectTitle(memberId, role, projectTitle);

        assertThat(foundEntity.isPresent()).isTrue();
        foundEntity.ifPresent(pm -> assertThat(pm)
                .extracting(p -> p.getMember().getId(), p-> p.getProject().getTitle(), ProjectMember::getRole)
                .containsExactly(memberId, projectTitle, ROLE_MANAGER));
    }
}
