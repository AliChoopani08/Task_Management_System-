package com.Ali_Choopani.Task_Management_System.services;

import com.Ali_Choopani.Task_Management_System.dto.project.AddNewProjectMemberRequest;
import com.Ali_Choopani.Task_Management_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectMemberSummary;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;

public interface ProjectService {

    ProjectSummary createAProject(CreateProjectRequest request, Long managerId);
    ProjectMemberSummary addProjecetMember(Long projectId, Long managerId, Long newMemberId, AddNewProjectMemberRequest request);

}
