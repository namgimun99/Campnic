package com.lec.spring.service;

import com.lec.spring.domain.Weather;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String API_KEY = "bf91f0d9592cd579411d4f97f1550d09";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";

    public Weather getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Weather> response = restTemplate.getForEntity(API_URL, Weather.class, city, API_KEY);
        return response.getBody();
    }
}
