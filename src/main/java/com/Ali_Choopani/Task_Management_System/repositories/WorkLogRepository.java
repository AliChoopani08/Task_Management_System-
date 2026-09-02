package com.Ali_Choopani.Task_Management_System.repositories;

import com.Ali_Choopani.Task_Management_System.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
