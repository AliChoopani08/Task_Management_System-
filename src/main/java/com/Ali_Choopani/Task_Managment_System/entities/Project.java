package com.Ali_Choopani.Task_Managment_System.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String description;
    @CreatedDate
    private LocalDate startDate;

    private LocalDate dueDate;

    @OneToMany(mappedBy = "project", fetch = LAZY)
    private Set<ProjectMember> projectMembers = new HashSet<>();


    public Set<ProjectMember> getProjectMembers() {
       return this.projectMembers == null ? new HashSet<>() : this.projectMembers;
    }
}
