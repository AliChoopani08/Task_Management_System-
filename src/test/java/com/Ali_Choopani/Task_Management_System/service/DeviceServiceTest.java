package com.Ali_Choopani.Task_Management_System.service;

import com.Ali_Choopani.Task_Management_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Management_System.entities.Device;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.mappers.DeviceMapper;
import com.Ali_Choopani.Task_Management_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Management_System.services.user.device.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.Ali_Choopani.Task_Management_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository repository;
    @Mock
    private DeviceMapper mapper;

    @InjectMocks
    private DeviceServiceImpl service;

    private User user;
    private Device device;
    private DeviceSummary summary;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(30L)
                .email("ali12choopani@gmail.com")
                .password("Ali@12345")
                .role(ROLE_USER)
                .build();
        device = Device.builder()
                .id(25L)
                .deviceUuid(randomUUID())
                .userAgent("fake.user.agent.12345")
                .build();
        summary = new DeviceSummary(25L, device.getDeviceUuid());
    }

    @Test
    void shouldCreateNewDevice_whenItDoesNotExist() {
        whenHelper(repository.findByUserIdAndDeviceUuid(anyLong(), any(UUID.class)), empty());
        whenHelper(repository.save(any(Device.class)), device);
        whenHelper(mapper.toSummary(any(Device.class)), summary);

        final DeviceSummary response = service.findOrCreateDevice(user, device.getDeviceUuid(), device.getUserAgent());

        assertThat(response)
                .extracting(DeviceSummary::id, DeviceSummary::deviceUuid)
                .containsExactly(25L, device.getDeviceUuid());
    }
}
