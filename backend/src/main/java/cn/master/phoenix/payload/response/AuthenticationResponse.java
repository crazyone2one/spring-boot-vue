package cn.master.phoenix.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Created by 11's papa on 2025/6/5
 */
@Data
@Builder
public class AuthenticationResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("refresh_token")
    String refreshToken;
    UserDTO user;
}
