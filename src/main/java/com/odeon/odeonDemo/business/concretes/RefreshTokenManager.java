package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.RefreshTokenService;
import com.odeon.odeonDemo.core.services.JwtService;
import com.odeon.odeonDemo.dataAccess.abstracts.RefreshTokenRepository;
import com.odeon.odeonDemo.entities.RefreshToken;
import com.odeon.odeonDemo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenManager implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    @Value("${jwt.refresh.days}")
    private int refreshTokenExpiryDays;


    @Override
    public RefreshToken create(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(jwtService.randomRefreshToken());
        refreshToken.setExpirationDate(LocalDateTime.now().plusDays(refreshTokenExpiryDays));
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verify(String token) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByToken(token);
        RefreshToken refreshToken = optionalRefreshToken.get();
        return refreshToken;
    }

    @Override
    public RefreshToken rotate(RefreshToken token, String ipAddress) {
        RefreshToken createdToken = create(token.getUser());
        revokeToken(token, ipAddress, createdToken.getToken());
        return createdToken;
    }

    @Override
    public void revokeOldTokens(User user, String ipAddress) {
        List<RefreshToken> oldTokens = refreshTokenRepository.findAllByUserAndRevokedDateIsNullAndExpirationDateBefore(user, LocalDateTime.now());
        if (oldTokens.isEmpty()) {
            return;
        }
        oldTokens = oldTokens.stream().map(r -> {
            r.setRevokedDate(LocalDateTime.now());
            r.setRevokedByIp(ipAddress);
            r.setRevokeReason("Token revoked because of expiration date.");
            return r;
        }).toList();
        refreshTokenRepository.saveAll(oldTokens);
    }

    private void revokeToken(RefreshToken token, String ipAddress, String replacedByToken) {
        token.setRevokedDate(LocalDateTime.now());
        token.setRevokedByIp(ipAddress);
        token.setRevokeReason("Token rotated by token: " + replacedByToken);
        refreshTokenRepository.save(token);
    }
}
