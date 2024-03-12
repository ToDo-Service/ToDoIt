package com.service.todoit.service;

import com.service.todoit.common.code.ResponseCode;
import com.service.todoit.common.exception.ApiException;
import com.service.todoit.domain.user.User;
import com.service.todoit.domain.user.dto.OauthUser;
import com.service.todoit.domain.user.enums.Social;
import com.service.todoit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ResponseCode.BAD_REQUEST));
    }

    @Transactional
    public User saveAndGetUser(OauthUser oauthUser, String type) {
        Social social = getSocial(type);
        User user = userRepository.findByEmail(oauthUser.getEmail())
                .orElse(OauthUser.toEntity(oauthUser, social));

        user.setLoginAt(LocalDateTime.now());


        return userRepository.save(user);
    }

    public OauthUser getUserInfo(String accessToken, String type){
        if(type.equals("google")) {
            return getGoogleUserInfo(accessToken);
        } else if (type.equals("kakao")) {
            return getKakaoUserInfo(accessToken);
        } else {
            return getNaverUserInfo(accessToken);
        }
    }

    private OauthUser getNaverUserInfo(
            String accessToken
    ){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JSONObject response = webClient.get()
                .uri(uri -> uri.path("/v1/nid/me").build())
                .header("Authorization","Bearer " + accessToken)
                .retrieve().bodyToMono(JSONObject.class).block();

        JSONParser jsonParser = new JSONParser();

        Map<String, Object> jsonObject = (Map<String, Object>) response.get("response");
        String email = (String) jsonObject.get("email");
        email = email.replace("jr.","");
        String profileImage = (String) jsonObject.get("profile_image");
        String name = (String) jsonObject.get("name");

        log.info("Naver 로그인 성공 : {}", email);

        return new OauthUser(name, email, profileImage);
    }

    private OauthUser getGoogleUserInfo(
            String accessToken
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JSONObject response = webClient.get()
                .uri(uri -> uri.path("/oauth2/v1/userinfo").build())
                .header("Authorization","Bearer " + accessToken)
                .retrieve().bodyToMono(JSONObject.class).block();

        String email = (String) response.get("email");
        String name = (String) response.get("name");
        String picture = (String) response.get("picture");

        log.info("Google 로그인 성공 : {}", email);

        return new OauthUser(name, email, picture);
    }

    private OauthUser getKakaoUserInfo(
            String accessToken
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JSONObject response = webClient.get()
                .uri(uri -> uri.path("/v2/user/me").build())
                .header("Authorization","Bearer " + accessToken)
                .retrieve().bodyToMono(JSONObject.class).block();

        Map<String, Object> account=(Map<String, Object>) response.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        String email = (String) account.get("email");
        String name = (String) profile.get("nickname");
        String avatar = (String) profile.get("profile_image_url");
        log.info("Kakao 로그인 성공 : {}", email);

        return new OauthUser(name, email, avatar);

    }



    private Social getSocial(String type){
        Social social = null;
        if(type.equals("google")) {
            social = Social.GOOGLE;
        } else if (type.equals("nanver")) {
            social = Social.NAVER;
        } else {
            social = Social.KAKAO;
        }
        return social;
    }


}
