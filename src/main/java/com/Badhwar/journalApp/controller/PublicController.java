package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    //Hitting Eleven Labs API
//    @GetMapping("/download")
//    public void downloadFile() throws IOException {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("xi-api-key","sk_5eb879caa22a26435372684f64855482d568c67559e8c579");
//
//        BodyEntity elevenMultilingualV2 = BodyEntity.builder().text("नमस्ते, आप कैसे हैं").model_id("eleven_multilingual_v2").build();
//
//        HttpEntity<BodyEntity> httpEntity = new HttpEntity(elevenMultilingualV2,httpHeaders);
//        String finalAPI = "https://api.elevenlabs.io/v1/text-to-speech/bIHbv24MWmeRgasZH58o";
//        ResponseEntity<byte[]> response = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, byte[].class);
//
//        byte[] audioBytes = response.getBody();
//
//        if(audioBytes != null)
//        {
//            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("output.mp3")))
//            {
//                bos.write(audioBytes);
//            } catch(IOException e)
//            {
//                e.printStackTrace();
//                throw new IOException("Invalid Path");
//            }
//        }
//    }

}

// -> Had to put the class outside, as Lombok only put @Builder on the static or Main Outer Classes
//@Data
//@Builder
//class BodyEntity
//{
//    private String text;
//    private String model_id;
//}
