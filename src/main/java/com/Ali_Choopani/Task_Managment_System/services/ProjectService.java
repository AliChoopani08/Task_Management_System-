package com.Ali_Choopani.Task_Managment_System.services;

import com.Ali_Choopani.Task_Managment_System.dto.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.ProjectMemberSummary;

public interface ProjectService {

    ProjectMemberSummary createAProject(CreateProjectRequest request, Long managerId);
}
