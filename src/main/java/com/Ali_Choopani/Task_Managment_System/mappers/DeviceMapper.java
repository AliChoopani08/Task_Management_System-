package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.DeviceSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = IGNORE)
public interface DeviceMapper {

    DeviceSummary toSummary(Device entity);
}
