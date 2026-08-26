package com.Ali_Choopani.Task_Management_System.testFactories;

import com.Ali_Choopani.Task_Management_System.dto.project.MemberSummary;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;

public class ProjectMemberTestFactory {

    public static ProjectMember createProjectMember(Long id, User user, ProjectRole role, Project project) {
        ProjectMember projectMember = ProjectMember.builder()
                .id(id)
                .role(role)
                .build();
        projectMember.addProjectMember(user, project);

        return projectMember;
    }

    public static ProjectSummary createProjectSummary(ProjectMember projectMember) {
        final Project project = projectMember.getProject();
        final User manager = projectMember.getMember();

        return ProjectSummary.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .dueDate(project.getDueDate())
                .manager(new MemberSummary(manager.getId(), manager.getProfile().getFullName(),  projectMember.getRole()))
                .build();
    }
}
