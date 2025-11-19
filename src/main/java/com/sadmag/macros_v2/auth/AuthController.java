package com.sadmag.macros_v2.auth;

import com.sadmag.macros_v2.user.User;
import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody AuthDto authDto) {
        var token = authService.authenticate(authDto);

        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody UserDto newUser) {
        userService.saveUser(newUser);

        return ResponseEntity.created(null).build();
    }
}