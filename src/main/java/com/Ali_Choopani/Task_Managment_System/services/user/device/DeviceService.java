package com.Ali_Choopani.Task_Managment_System.services.user.device;

import com.Ali_Choopani.Task_Managment_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Managment_System.entities.User;

import java.util.UUID;

public interface DeviceService {

    DeviceSummary createNewDevice(User user, String userAgent);
    DeviceSummary findOrCreateDevice(User user, UUID deviceUuid, String userAgent);
    DeviceSummary getByUserIdAndDeviceUuid(Long userId, UUID deviceUuid);
    void deactivateDevice(Long userId, UUID deviceUuid);
}
