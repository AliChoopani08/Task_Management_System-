package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.util.HashSet;
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

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(STRING)
    private ProjectRole role;

    @OneToMany(mappedBy = "assignee", cascade = ALL, fetch = LAZY)
    @Default
    public Set<Task> tasks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "author", cascade = ALL, fetch = LAZY)
    @Default
    public Set<WorkLog> workLogs = new HashSet<>();

    public void addProjectMember(User member , Project project) {
        this.setMember(member);
        this.setProject(project);

        member.getProjectMembers().add(this);
        project.getProjectMembers().add(this);
    }
}
