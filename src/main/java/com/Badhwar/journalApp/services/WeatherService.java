package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.Cache.AppCache;
import com.Badhwar.journalApp.api.response.WeatherResponse;
import com.Badhwar.journalApp.constants.PlaceHolders;
import com.Badhwar.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class WeatherService {
    //The @Value annotation in Spring Boot is used to inject values from configuration files (like application.properties or application.yml) into fields of Spring-managed beans.
    @Value("${Weather.Api.Key}") //Remember, it won't work with STATIC Variables, as Static variables are associated with the Class, Same Instances can be shared across.
    private String apiKey;

    // Rest Template helps us to hit(Process) http request from our own backend
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCacheC;

    //Get CAll to External API
    public WeatherResponse getWeather(String city)
    {
        AppCache.Keys weatherApi = AppCache.Keys.WEATHER_API;
        String finalAPI =  appCacheC.appCache.get(AppCache.Keys.WEATHER_API.toString()).replace(PlaceHolders.city, city).replace(PlaceHolders.apiKey, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

    //POST Call To External API
//    public WeatherResponse postWeather(String city)
//    {
//        String finalAPI =  API.replace("CITY", city).replace("API_KEY", apiKey);
//
//        User user = User.builder().userName("Karan").password("Karan").roles(List.of("User")).build();
//        //HttpEntity -> contains Body and Header of the request.
//            //Headers depends on the API, if it has asked for it.
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Key", "Value");
//        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);
//        WeatherResponse body = response.getBody();
//        return body;
//    }
}

