package cn.master.phoenix.payload.response;

import java.util.List;

/**
 * @author Created by 11's papa on 2025/6/5
 */
public record UserDTO(String id, String name, List<String> roles) {
}
