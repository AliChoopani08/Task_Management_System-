package com.Ali_Choopani.Task_Managment_System.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"phoneNumber", "email", "role", "devices"})
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @Enumerated(STRING)
    private UserRole role;

    @CreatedDate
    private Instant createAt;

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = ALL)
    private Profile profile;
    private boolean isProfileCompleted;

    @OneToMany(mappedBy = "member", fetch = LAZY, cascade = ALL)
    private Set<ProjectMember> projectMembers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY)
    public Set<Device> devices = new HashSet<>();


    public Set<ProjectMember> getProjectMembers() {
        return initializeIfNull(this.projectMembers, new HashSet<>());
    }

    public Set<Task> getTasks() {
        return initializeIfNull(this.tasks, new HashSet<>());
    }

    public Set<Comment> getComments() {
        return initializeIfNull(this.comments, new HashSet<>());
    }

    public Set<Device> getDevices() {
        return initializeIfNull(this.devices, new HashSet<>());
    }

    private <T> T initializeIfNull(T field, T value) {
       return (field == null) ? value : field;
    }
}

