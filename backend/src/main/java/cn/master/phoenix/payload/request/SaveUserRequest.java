package cn.master.phoenix.payload.request;

import lombok.Data;

import java.util.List;

/**
 * @author Created by 11's papa on 2025/6/13
 */
@Data
public class SaveUserRequest {
    private String id;
    private String username;
    private String nickname;
    private String email;
    private List<String> roleIds;
}
