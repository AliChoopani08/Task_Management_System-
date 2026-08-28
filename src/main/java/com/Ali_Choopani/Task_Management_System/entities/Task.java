package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String description;
    @Enumerated(STRING)
    private TaskStatus status;
    @CreatedDate
    private LocalDate createAt;

    private LocalDate dueDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "assignee_id")
    public ProjectMember assignee;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "task", fetch = LAZY)
    private Set<Comment> comments = new HashSet<>();

    public void assignTaskToMember(ProjectMember projectMember) {
        this.setAssignee(projectMember);
        projectMember.getTasks().add(this);
    }

    public void addTaskProject(Project project) {
        this.setProject(project);
        project.getTasks().add(this);
    }
}
