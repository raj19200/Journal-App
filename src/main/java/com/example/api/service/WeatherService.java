package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.api.apiResponse.WeatherResponse;
import com.example.api.cache.AppCache;
@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("Weather_of"+ city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<city>", city).replace("<apiKey>", apiKey);
            ResponseEntity<WeatherResponse> res = restTemplate.exchange(finalApi,HttpMethod.GET,null,WeatherResponse.class);
            WeatherResponse body = res.getBody();
            if(body !=null){
                redisService.set("Weather_of"+ city, body, 300l);
            }
            return body;   
        }
    }
}
