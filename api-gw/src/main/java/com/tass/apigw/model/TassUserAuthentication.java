package com.tass.apigw.model;

import com.google.gson.Gson;
import com.tass.apigw.security.UserDetailExtend;
import com.tass.common.model.dto.user.LoginDTO;
import com.tass.common.model.userauthen.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TassUserAuthentication extends UsernamePasswordAuthenticationToken {

    public TassUserAuthentication(UserDetailExtend userDetailExtend  ) {
        super(userDetailExtend, null , new ArrayList<>());
    }

}
