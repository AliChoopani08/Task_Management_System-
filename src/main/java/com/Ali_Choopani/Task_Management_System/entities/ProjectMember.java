package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "project_member")
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private User member;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(STRING)
    private ProjectRole role;

    @OneToMany(mappedBy = "assignee", cascade = ALL, fetch = LAZY)
    public Set<Task> tasks = new LinkedHashSet<>();

    public void addProjectMember(User member , Project project) {
        this.setMember(member);
        this.setProject(project);

        member.getProjectMembers().add(this);
        project.getProjectMembers().add(this);
    }

    public Set<Task> getTasks() {
        return this.tasks == null ? new LinkedHashSet<>() : this.tasks;
    }
}
