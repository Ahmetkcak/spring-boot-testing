package com.odeon.odeonDemo.business.abstracts;

import com.odeon.odeonDemo.entities.RefreshToken;
import com.odeon.odeonDemo.entities.User;

import java.sql.Ref;

public interface RefreshTokenService {
    RefreshToken create(User user);

    RefreshToken verify(String token);

    RefreshToken rotate(RefreshToken token, String ipAddress);

    void revokeOldTokens(User user, String ipAddress);
}
