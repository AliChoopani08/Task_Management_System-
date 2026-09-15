package com.Ali_Choopani.Task_Management_System.dto.user.profile;

import java.time.LocalDate;

public interface ProfileFields {
    String getFirstName();
    String getSurname();
    LocalDate getBirthDate();
    String getBiography();

}
