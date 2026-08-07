package com.Ali_Choopani.Task_Managment_System.repositories;

import com.Ali_Choopani.Task_Managment_System.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
