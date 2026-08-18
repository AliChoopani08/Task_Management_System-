package com.Ali_Choopani.Task_Management_System.TestMocksHelper;

import org.mockito.Mockito;

public class MockWhenHelper {

    public static <T> void whenHelper(T callMethod, T respectedResponse) {
        Mockito.when(callMethod)
                .thenReturn(respectedResponse);
    }
}
