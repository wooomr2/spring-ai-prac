package com.woo.ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Tool(name = "getWeather", description = "도시 이름을 기반으로 현재 날씨 정보를 반환합니다.")
    public String getWeather(String city) {
        // 여기서는 예시로 고정값을 주지만, 실제로는 WeatherService 호출 가능
        return city + "- 날씨: 맑음, 기온: 999도, 바람: 적음.";
    }
}
