package com.Ali_Choopani.Task_Managment_System.entities;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "Board_User")
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
    @JoinColumn(name = "member_id")
    private User member;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(STRING)
    private ProjectRole role;

    public void addProjectMember(User member , Project project) {
        this.setMember(member);
        this.setProject(project);

        member.getProjectMembers().add(this);
        project.getProjectMembers().add(this);
    }

}
