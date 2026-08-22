package com.Ali_Choopani.Task_Management_System.testFactories;

import com.Ali_Choopani.Task_Management_System.entities.Project;

import java.time.LocalDate;

public class ProjectTestFactory {

    public static Project createProject(Long id ,String title, String description, LocalDate dueDate) {
        return Project.builder()
                .id(id)
                .title(title)
                .description(description)
                .dueDate(dueDate)
                .build();
    }
}
