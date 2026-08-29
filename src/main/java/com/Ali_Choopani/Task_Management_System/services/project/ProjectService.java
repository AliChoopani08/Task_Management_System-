package com.Ali_Choopani.Task_Management_System.services.project;

import com.Ali_Choopani.Task_Management_System.dto.project.*;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ProjectService {

    ProjectSummary createAProject(CreateProjectRequest request, Long managerId);
    ProjectMembersDetails addProjectMember(Long projectId, Long managerId, Long newMemberId, AddNewProjectMemberRequest request, Pageable pageable);
    Set<MyProjectsSummary> getMyProjectsSummary(Long memberId);
    ProjectMembersDetails getProjectMembersDetails(Long projectId, Pageable pageable);

}
