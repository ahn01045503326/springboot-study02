package org.delivery.api.domain.token.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpired;
    private String refreshToken;
    private LocalDateTime refreshTokenExpired;

}
