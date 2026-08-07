package com.Ali_Choopani.Task_Managment_System.exceptions;

import static java.lang.String.format;

public class DuplicateRefreshToken extends RuntimeException {
    public DuplicateRefreshToken(Long deviceId ,Long tokenId) {
        super(format("This device [%s] has a valid refresh token [%s], Pls enter with this refresh token !" ,deviceId, tokenId));
    }
}
