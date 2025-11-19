package com.sadmag.macros_v2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody AuthDto authDto) {
        var token = authService.authenticate(authDto);

        return ResponseEntity.ok(token);
    }
}