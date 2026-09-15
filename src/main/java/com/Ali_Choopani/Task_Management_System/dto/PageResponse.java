package com.Ali_Choopani.Task_Management_System.dto;

import java.util.List;

public record PageResponse<T> (List<T> content,
                               int page,
                               int size,
                               long totalElement,
                               int totalPages) {

}
