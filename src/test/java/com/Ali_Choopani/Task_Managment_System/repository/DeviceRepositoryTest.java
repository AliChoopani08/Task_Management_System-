package com.Ali_Choopani.Task_Managment_System.repository;

import com.Ali_Choopani.Task_Managment_System.entities.Device;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DeviceRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository repository;

    private User savedUser;


    @BeforeEach
    void setUp() {
        User user = User.builder()
                .phoneNumber("09876543210")
                .password("Asjd@choo")
                .build();
        Device device = Device.builder()
                .deviceUuid(randomUUID())
                .available(true)
                .build();
       savedUser =  userRepository.save(user);

        device.addDeviceToUser(savedUser);
       repository.save(device);
    }

    @Test
    @Transactional(readOnly = true)
    void shouldFindByUserIdAndDeviceUuid_whenExists() {
        final Long userId = savedUser.getId();
        final Device device = savedUser.getDevices().iterator().next();

        final Optional<Device> foundDevice = repository.findByUserIdAndDeviceUuid(userId, device.getDeviceUuid());

        assertThat(foundDevice.isPresent()).isTrue();
        foundDevice.ifPresent(d -> assertThat(d)
                .extracting(Device::getDeviceUuid)
                .isEqualTo(device.getDeviceUuid()));
    }

    @Test
    @Transactional(readOnly = true)
    void shouldFindByIdAndBeAvailable_wheExits() {
        final Long deviceId = savedUser.getDevices().iterator().next().getId();

        final Optional<Device> foundDevice = repository.findByIdAndBeAvailable(deviceId);

        assertThat(foundDevice.isPresent()).isTrue();
        foundDevice.ifPresent(d -> assertThat(d.isAvailable())
                .isTrue());
    }
}
