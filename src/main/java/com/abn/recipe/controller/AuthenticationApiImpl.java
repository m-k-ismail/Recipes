package com.abn.recipe.controller;

import com.abn.api.AuthenticationApi;

import com.abn.model.AuthenticationREQ;
import com.abn.model.AuthenticationRES;
import com.abn.model.User;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.exception.ErrorType;
import com.abn.recipe.security.service.ApplicationUserDetailsService;
import com.abn.recipe.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthenticationApiImpl implements AuthenticationApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final ApplicationUserDetailsService userDetailsService;

    @Autowired
    AuthenticationApiImpl(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, ApplicationUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public ResponseEntity<AuthenticationRES> authenticate(AuthenticationREQ authenticationREQ) {
        User user = authenticationREQ.getData();
        String username = user.getUsername();
        String password = user.getPassword();

        try {
            authenticate(username, password);

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(username);

            user.setToken(jwtTokenUtil.generateToken(userDetails));

            return ResponseEntity.ok(new AuthenticationRES().data(user));
        } catch (Exception e) {
            throw new ErrorException(ErrorType.AUTHENTICATION_FAILED);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
