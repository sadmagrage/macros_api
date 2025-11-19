package com.sadmag.macros_v2.auth;

import com.sadmag.macros_v2.token.TokenDto;
import com.sadmag.macros_v2.token.TokenService;
import com.sadmag.macros_v2.user.User;
import com.sadmag.macros_v2.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public TokenDto authenticate(AuthDto authDto) {
        var usernamePwd = new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());

        var auth = authenticationManager.authenticate(usernamePwd);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new TokenDto(token);
    }
}