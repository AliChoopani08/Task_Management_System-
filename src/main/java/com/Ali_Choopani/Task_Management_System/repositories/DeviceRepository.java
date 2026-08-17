package com.Ali_Choopani.Task_Management_System.repositories;

import com.Ali_Choopani.Task_Management_System.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByUserIdAndDeviceUuid(Long userId, UUID deviceUuid);

    @Query("""
            SELECT d\s
            FROM Device d
            WHERE d.id = :deviceId AND d.available = true
           \s""")
    Optional<Device> findByIdAndBeAvailable(@Param("deviceId") Long deviceId);
}
