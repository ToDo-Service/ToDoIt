package com.service.todoit.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Social {
    KAKAO("kakao"),
    GOOGLE("google"),
    NAVER("naver");

    private final String name;

}
