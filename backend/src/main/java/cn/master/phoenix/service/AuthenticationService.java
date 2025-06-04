package cn.master.phoenix.service;

import cn.master.phoenix.payload.request.AuthenticationRequest;
import cn.master.phoenix.service.impl.AuthenticationServiceImpl;

/**
 * @author Created by 11's papa on 2025/4/27
 */
public interface AuthenticationService {
    AuthenticationServiceImpl.AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationServiceImpl.AuthenticationResponse refreshToken(AuthenticationServiceImpl.AuthenticationResponse token);
}
