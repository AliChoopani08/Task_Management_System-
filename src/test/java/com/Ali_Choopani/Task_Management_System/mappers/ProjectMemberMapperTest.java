package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.project.MyProjectsSummary;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory.createProjectMember;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory.createProject;
import static com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory.createUser;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ProjectMemberMapperTest {

    private final ProjectMemberMapper mapper = Mappers.getMapper(ProjectMemberMapper.class);

    private Set<ProjectMember> memberProjects = new HashSet<>();

    @BeforeEach
    void setUp() {
        Project mobileProject = createProject(1L,"Mobile Application",null,of(2026,8,30));
        Project taskManagementProject = createProject(2L,"Task Management System",null,of(2026,12,10));

        User user =  createUser(2L, "Ali_ch_1234@gami.com",null, "Ali Choopani");

        memberProjects.add(createProjectMember(3L, user, ROLE_DEVELOPER, mobileProject));
        memberProjects.add(createProjectMember(4L, user, ROLE_MANAGER, taskManagementProject));

    }

    @Test
    void shouldMapEntityToSummary() {
        final ProjectMember getFirstProject = memberProjects.stream()
                .filter(pr -> pr.getProject().getTitle().equalsIgnoreCase("Mobile Application"))
                .findFirst()
                .orElse(null);

        final ProjectSummary summary = mapper.toSummary(getFirstProject);

        assertThat(summary)
                .extracting(ProjectSummary::title, ProjectSummary::dueDate, p -> p.manager().name())
                .containsExactly("Mobile Application", of(2026,8,30),"Ali Choopani");
        }

    @Test
    void shouldMapProjectMembersToMyProjectsSummary() {
        final Set<MyProjectsSummary> myProjectsSummary = mapper.toMyProjectsSummary(memberProjects);

        assertThat(myProjectsSummary)
                .hasSize(2)
                .extracting(MyProjectsSummary::title, MyProjectsSummary::role)
                .containsExactly(tuple("Mobile Application", ROLE_DEVELOPER),
                                 tuple("Task Management System", ROLE_MANAGER));
    }
}
