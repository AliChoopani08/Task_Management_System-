package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.entities.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMapperTest {

    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    private Project entity;

    @BeforeEach
    void setUp() {
        entity = Project.builder()
                .id(1L)
                .title("Implementation a store site")
                .description("Completing the initial version of site")
                .startDate(of(2026,4,20))
                .dueDate(of(2026,7,20))
                .build();
    }

    @Test
    void shouldMapToEntity_fromRequest() {
        CreateProjectRequest request = CreateProjectRequest.builder()
                .title("Implementation a store site")
                .description("Completing the initial version of site")
                .dueDate(of(2026,7,20))
                .build();

        final Project response = mapper.toEntity(request);

        assertThat(response)
                .extracting(Project::getTitle, Project::getDueDate)
                .containsExactly("Implementation a store site", of(2026,7,20));
    }
}
