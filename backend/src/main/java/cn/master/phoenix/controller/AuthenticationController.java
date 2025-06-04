package cn.master.phoenix.controller;

import cn.master.phoenix.payload.request.AuthenticationRequest;
import cn.master.phoenix.service.AuthenticationService;
import cn.master.phoenix.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by 11's papa on 2025/4/27
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationServiceImpl.AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
