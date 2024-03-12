package com.service.todoit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
public class ProfileCheckController {
    private final Environment env;

    @GetMapping("/profile")
    public String test(){
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }

}
