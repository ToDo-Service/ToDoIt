package com.service.todoit.domain.user.dto;


import com.service.todoit.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String email;

    private String name;

    private String avatar;

    private String accessToken;

    public static UserDto From(User user, String accessToken) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .accessToken(accessToken)
                .build();
    }
}
