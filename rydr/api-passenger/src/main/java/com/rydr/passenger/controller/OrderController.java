package com.rydr.passenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rydr.dto.ResponseResult;
import com.rydr.common.dto.order.ForecastRequest;
import com.rydr.common.dto.order.ForecastResponse;
import com.rydr.passenger.feign.ServiceForecast;

import net.sf.json.JSONObject;

/**
 * Passenger-facing order management and fare estimation endpoints.
 *
 * @author Rydr Team
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ServiceForecast serviceForecast;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Calculate price forecast estimation using OpenFeign declarative HTTP client.
     *
     * @param forecastRequest ride pickup and destination coordinates
     * @return ResponseResult containing estimated fare price details
     */
    @PostMapping("/forecast")
    public ResponseResult<ForecastResponse> forecast(@RequestBody ForecastRequest forecastRequest) {
        ResponseResult<ForecastResponse> result = serviceForecast.forecast(forecastRequest);
        return ResponseResult.success(result.getData());
    }

    /**
     * Price forecast endpoint via ribbon load-balanced RestTemplate.
     *
     * @param forecastRequest ride pickup and destination coordinates
     * @return ResponseResult containing estimated fare price details
     */
    @PostMapping("/forecast-test")
    public ResponseResult forecastTest(@RequestBody ForecastRequest forecastRequest) {
        String destination = "service-valuation";
        String url = "http://" + destination + "/forecast/single";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestParam = new JSONObject();
        requestParam.put("startLatitude", "1");
        requestParam.put("startLongitude", "1");
        requestParam.put("endLatitude", "1");
        requestParam.put("endLongitude", "1");

        HttpEntity entity = new HttpEntity(requestParam, headers);
        ResponseResult result = restTemplate.exchange(url, HttpMethod.POST, entity, ResponseResult.class).getBody();

        return ResponseResult.success(result != null ? result.getData() : null);
    }
}

