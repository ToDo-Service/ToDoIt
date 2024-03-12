package com.service.todoit.sercurity.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    @Value("${token.secret.key}")
    private String secretKey;

    private final List<String> excepList = List.of("/api/auth","/login","/main","/profile","/favicon.ico","/actuator/health");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(!noFilter(request)){

            String accessToken = parseToken(request);

            if(accessToken != null && validateToken(accessToken, request)){
                log.info("인증객체 생성");

                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(String token, HttpServletRequest request) {

        try{
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            if(e instanceof MalformedJwtException){
                // 토큰이 유효하지 않을 떄
                log.error("유효하지 않은 토큰입니다.");
                request.setAttribute("error","MalformedJwtException");
            }else if(e instanceof ExpiredJwtException){
                // 만료된 토큰
                log.error("만료된 토큰입니다.");
                request.setAttribute("error","ExpiredJwtException");
            }else {
                // 그외 에러
                log.error("잘못된 인증정보입니다.");
                request.setAttribute("error","MalformedJwtException");
            }
            return false;
        }
    }

    private String parseToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.info(bearerToken);

        try{
            if(bearerToken.startsWith("Bearer")){
                return bearerToken.substring(7);
            }

        }catch (NullPointerException e){
            log.error("",e);
            log.error("AccessToken이 존재하지 않습니다.");
            request.setAttribute("exception","AccessToken이 존재하지 않습니다.");
        }

        return null;
    }

    //특정 URI 필터 동작 막기
    private boolean noFilter(HttpServletRequest request){
//        log.info(request.getRequestURI());
        return excepList.stream().anyMatch(uri -> request.getRequestURI().contains(uri));

    }
}
