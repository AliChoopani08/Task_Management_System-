package com.Ali_Choopani.Task_Managment_System.services.user.device;

import com.Ali_Choopani.Task_Managment_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Device;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.mappers.DeviceMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository repository;
    private final DeviceMapper mapper;

    @Override
    public DeviceSummary createNewDevice(User user, String userAgent) {
        Device device = Device.builder()
                .deviceUuid(randomUUID())
                .userAgent(userAgent)
                .available(true)
                .build();
        device.addDeviceToUser(user);

        final Device savedDevice = repository.save(device);

        return mapper.toSummary(savedDevice);
    }

    @Override
    public DeviceSummary findOrCreateDevice(User user, UUID deviceUuid, String userAgent) {
        if (deviceUuid != null) {
            final Optional<Device> foundDevice = repository.findByUserIdAndDeviceUuid(user.getId(), deviceUuid);

            if (foundDevice.isPresent()) {
                return mapper.toSummary(foundDevice.get());
            } else {
                return createNewDevice(user, userAgent);
            }
        }
        else
            return createNewDevice(user, userAgent);
    }

    @Override
    public DeviceSummary getByUserIdAndDeviceUuid(Long userId, UUID deviceUuid) {
        repository.findByUserIdAndDeviceUuid(userId, deviceUuid);
        return null;
    }



    @Override
    public void deactivateDevice(Long userId, UUID deviceUuid) {

    }
}
