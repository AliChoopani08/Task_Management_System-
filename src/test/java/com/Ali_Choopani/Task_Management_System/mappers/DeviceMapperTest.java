package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Management_System.entities.Device;
import com.Ali_Choopani.Task_Management_System.entities.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

public class DeviceMapperTest {

    private final DeviceMapper mapper = Mappers.getMapper(DeviceMapper.class);

    @Test
    void shouldMapEntityToSummary() {
        Device device = Device.builder()
                .id(25L)
                .deviceUuid(randomUUID())
                .available(true)
                .build();
        User user = User.builder()
                .id(30L)
                .build();
        device.addDeviceToUser(user);

        final DeviceSummary summary = mapper.toSummary(device);

        assertThat(summary)
                .extracting(DeviceSummary::id)
                .isEqualTo(25L);

    }
}
