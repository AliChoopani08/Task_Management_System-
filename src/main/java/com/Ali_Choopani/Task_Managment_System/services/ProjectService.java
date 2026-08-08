package com.Ali_Choopani.Task_Managment_System.services;

import com.Ali_Choopani.Task_Managment_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.project.ProjectMemberSummary;

public interface ProjectService {

    ProjectMemberSummary createAProject(CreateProjectRequest request, Long managerId);
}
