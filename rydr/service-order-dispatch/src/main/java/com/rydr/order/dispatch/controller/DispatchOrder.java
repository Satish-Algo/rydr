package com.rydr.order.dispatch.controller;

import com.rydr.dto.ResponseResult;
import com.rydr.order.dispatch.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Controller endpoint for triggering spatial driver dispatch algorithms for ride orders.
 *
 * @author Rydr Team
 */
@RestController
@RequestMapping("/dispatch")
public class DispatchOrder {

    @Autowired
    private DispatchService dispatchService;

    /**
     * Trigger vehicle dispatch and order matching for a specific order.
     *
     * @param orderId unique ID of the target ride order
     * @return ResponseResult outcome of the dispatch attempt
     */
    @GetMapping("/call/{orderId}")
    public ResponseResult callCar(@PathVariable("orderId") int orderId) {
        // Select eligible nearby drivers
        List<Integer> driverList = Collections.singletonList(1);
        return dispatchService.dispatch(orderId, driverList);
    }
}

