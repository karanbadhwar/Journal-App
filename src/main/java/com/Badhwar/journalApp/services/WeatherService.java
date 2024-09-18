package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.api.response.WeatherResponse;
import com.Badhwar.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class WeatherService {
    private static final String apiKey= "fe67890b57176c3dc7bf588b505c5f68";
    private static final String API= "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    // Rest Template helps us to hit(Process) http request from our own backend
    @Autowired
    private RestTemplate restTemplate;

    //Get CAll to External API
    public WeatherResponse getWeather(String city)
    {
       String finalAPI =  API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

    //POST Call To External API
    public WeatherResponse postWeather(String city)
    {
        String finalAPI =  API.replace("CITY", city).replace("API_KEY", apiKey);

        User user = User.builder().userName("Karan").password("Karan").roles(List.of("User")).build();
        //HttpEntity -> contains Body and Header of the request.
            //Headers depends on the API, if it has asked for it.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Key", "Value");
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}

