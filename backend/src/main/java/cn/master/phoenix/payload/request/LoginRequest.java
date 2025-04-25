package cn.master.phoenix.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by 11's papa at 2025/4/25-11:44 @version v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    private String username;
    private String password;
}
