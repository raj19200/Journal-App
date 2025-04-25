package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.api.apiResponse.WeatherResponse;
@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final String API= "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> res = restTemplate.exchange(finalApi,HttpMethod.GET,null,WeatherResponse.class);
        WeatherResponse body = res.getBody();
        return body;
    }
}
