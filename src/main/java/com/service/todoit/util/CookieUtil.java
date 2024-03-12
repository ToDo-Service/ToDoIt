package com.service.todoit.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .maxAge(60 * 60 * 12)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void addAccessCookie(HttpServletResponse response, String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .maxAge(60)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
