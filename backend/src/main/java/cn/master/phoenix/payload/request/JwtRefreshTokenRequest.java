package cn.master.phoenix.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author Created by 11's papa on 2025/6/6
 */
@Getter
public class JwtRefreshTokenRequest {

    @NotBlank(message = "Refresh token can't be empty")
    private String refreshToken;
}
