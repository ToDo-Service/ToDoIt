package com.service.todoit.sercurity.jwt;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtTokens {

    private String accessToken;
    private String refreshToken;
}
