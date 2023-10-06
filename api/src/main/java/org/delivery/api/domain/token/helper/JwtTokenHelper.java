package org.delivery.api.domain.token.helper;

import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;
    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;
    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        return null;
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        return null;
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        return null;
    }
}
