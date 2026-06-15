package com.rydr.controller;

import com.rydr.common.dto.sms.SmsSendRequest;
import com.rydr.service.AliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rydr.dto.ResponseResult;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * Controller endpoint for handling outgoing SMS message delivery requests.
 *
 * @author Rydr Team
 */
@RestController
@RequestMapping("/send")
@Slf4j
public class SendController {

    @Autowired
    private AliService aliService;

    /**
     * Send SMS notification via Aliyun SMS template service.
     *
     * @param smsSendRequest request containing recipient phone numbers, template code, and parameters
     * @return ResponseResult status indicating dispatch outcome
     */
    @PostMapping("/alisms-template")
    public ResponseResult send(@RequestBody SmsSendRequest smsSendRequest) {
        JSONObject param = JSONObject.fromObject(smsSendRequest);
        log.info("[SMS Gateway] Dispatching SMS template request: {}", param);
        aliService.sendSms(smsSendRequest);
        return ResponseResult.success("");
    }
}

