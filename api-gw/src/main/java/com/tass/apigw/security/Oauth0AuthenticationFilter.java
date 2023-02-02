//package com.tass.apigw.security;
//
//import com.google.gson.Gson;
//import com.tass.apigw.utils.JWTUtil;
//import com.tass.common.model.dto.user.CredentialDTO;
//import com.tass.common.model.dto.user.LoginDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//public class Oauth0AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            String jsonData =  request.getReader().lines().collect(Collectors.joining());
//            Gson gson = new Gson();
//            LoginDTO loginDTO= gson.fromJson(jsonData, LoginDTO.class);
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // login success trả về access token
//    @Override
//    protected void successfulAuthentication(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain chain,
//            Authentication authResult) throws IOException {
//        User user = (User) authResult.getPrincipal();
//        String accessToken = JWTUtil.generateToken(user.getUsername(),
//                user.getAuthorities().iterator().next().getAuthority(),
//                request.getRequestURI(),
//                JWTUtil.ONE_DAY * 7);
//
//        String refreshToken = JWTUtil.generateToken(user.getUsername(),
//                user.getAuthorities().iterator().next().getAuthority(),
//                request.getRequestURI(),
//                JWTUtil.ONE_DAY * 14);
//        CredentialDTO credentialDTO = new CredentialDTO(accessToken, refreshToken);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        Gson gson = new Gson();
//        response.getWriter().println(gson.toJson(credentialDTO));
//    }
//
//    // login fail trả về error message dạng json
//    @Override
//    protected void unsuccessfulAuthentication(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException failed) throws IOException {
//        HashMap<String, String> errors = new HashMap<>();
//        errors.put("message", "wrong information");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        Gson gson = new Gson();
//        response.getWriter().println(gson.toJson(errors));
//    }
//}
