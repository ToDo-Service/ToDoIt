package com.service.todoit.domain.user.dto;

import com.service.todoit.domain.user.User;
import com.service.todoit.domain.user.enums.Role;
import com.service.todoit.domain.user.enums.Social;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthUser {
    private String nickname;
    private String email;
    private String picture;

    public static User toEntity(OauthUser oauthUser, Social social){
        return User.builder()
                .email(oauthUser.getEmail())
                .name(oauthUser.getNickname())
                .avatar(oauthUser.getPicture())
                .social(social)
                .role(Role.USER)
                .build();
    }
}
