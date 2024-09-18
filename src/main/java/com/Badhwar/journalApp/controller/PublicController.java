package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/health-check") //GET Call
    public String healthCheck()
    {
        return "OK";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }

    // Trying to Hit Eleven Labs API
//    @GetMapping("/download")
//    public void downloadFile()
//    {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("xi-api-key","sk_5eb879caa22a26435372684f64855482d568c67559e8c579");
//
//
//        BodyEntity elevenMultilingualV2 = BodyEntity.builder().text("नमस्ते, आप कैसे हैं").model_id("eleven_multilingual_v2").build();
//
//        HttpEntity<BodyEntity> httpEntity = new HttpEntity(elevenMultilingualV2);
//        String finalAPI = "https://api.elevenlabs.io/v1/text-to-speech/bIHbv24MWmeRgasZH58o";
//        restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, )
//    }
//
//    @Data
////    @NoArgsConstructor
//    @Builder
//    public class BodyEntity
//    {
//        private String text;
//
//        private String model_id;
//    }
}
