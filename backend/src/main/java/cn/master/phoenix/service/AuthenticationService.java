package cn.master.phoenix.service;

import cn.master.phoenix.payload.request.AuthenticationRequest;
import cn.master.phoenix.payload.response.AuthenticationResponse;

/**
 * @author Created by 11's papa on 2025/4/27
 */
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(AuthenticationResponse token);
}
