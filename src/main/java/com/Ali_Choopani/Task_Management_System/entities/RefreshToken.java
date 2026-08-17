package com.Ali_Choopani.Task_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID token;

    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @CreatedDate
    private Instant createAt;
    @Column(nullable = false)
    private Instant expiresAt;

    private boolean revoked;


    public void addRefreshTokenToDevice(Device device) {
        this.setDevice(device);
        device.setRefreshToken(this);
    }
}
