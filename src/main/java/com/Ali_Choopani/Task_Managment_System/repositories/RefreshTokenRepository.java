package com.Ali_Choopani.Task_Managment_System.repositories;

import com.Ali_Choopani.Task_Managment_System.entities.RefreshToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("""
            SELECT rf 
            FROM RefreshToken rf
            WHERE rf.device.id = :deviceId AND rf.expiresAt > :now AND rf.revoked != true
            """)
    Optional<RefreshToken> findTheValidToken(@Param("deviceId") Long deviceId, @Param("now") Instant now);

    @EntityGraph(attributePaths = {"device", "device.user"})
    Optional<RefreshToken> findByTokenAndDevice_DeviceUuid(UUID refreshToken, UUID deviceUuid);
}
