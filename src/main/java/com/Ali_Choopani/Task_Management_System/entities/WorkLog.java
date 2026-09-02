package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private ProjectMember author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public void addCommentTask(ProjectMember author, Task task) {
        this.setAuthor(author);
        author.getComments().add(this);

        this.setTask(task);
        task.getComments().add(this);
    }
}
