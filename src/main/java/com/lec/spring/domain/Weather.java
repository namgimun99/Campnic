package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private String name;
    private WeatherInfo[] weather;

}

class WeatherInfo {
    @JsonProperty("icon")
    private String icon;
}

