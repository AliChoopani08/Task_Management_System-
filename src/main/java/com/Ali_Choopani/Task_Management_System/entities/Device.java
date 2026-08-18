package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Device {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID deviceUuid;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private Instant ceratedAt;

    private boolean available;
    private String userAgent;
    @OneToOne(mappedBy = "device", cascade = ALL)
    private RefreshToken refreshToken;

    public void addDeviceToUser(User user) {
        this.setUser(user);
        if (user.devices == null) {
            user.devices = new HashSet<>();
        }
        user.getDevices().add(this);
    }

}
