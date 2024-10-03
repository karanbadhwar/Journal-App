package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserDetailsServicesImpl;
import com.Badhwar.journalApp.services.UserService;
import com.Badhwar.journalApp.utils.JwtUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServicesImpl userDetailsServices;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/health-check") //GET Call
    public String healthCheck()
    {
        return "OK";
    }
    @PostMapping("/signup")
    public void signup(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user)
    {
        try{
            //This will authenticate user by internally using our UserDetailsServiceImpl and use the PassEncoder Bean we created
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsServices.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch(Exception e)
        {
            log.error("Exception occurred while createAuthenticationToken " + e);
            return  new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
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
