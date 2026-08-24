package com.Ali_Choopani.Task_Management_System.services.project;

import com.Ali_Choopani.Task_Management_System.dto.project.*;

import java.util.Set;

public interface ProjectService {

    ProjectSummary createAProject(CreateProjectRequest request, Long managerId);
    ProjectDetails addProjectMember(Long projectId, Long managerId, Long newMemberId, AddNewProjectMemberRequest request);
    Set<MyProjectsSummary> getMyProjects(Long memberId);

}
