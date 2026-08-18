package com.Ali_Choopani.Task_Management_System;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@Profile("test")
@EnableJpaAuditing
public class TestAppConfig {
}
