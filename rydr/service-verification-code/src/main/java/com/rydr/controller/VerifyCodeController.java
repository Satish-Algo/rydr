package com.rydr.controller;

import com.rydr.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rydr.dto.ResponseResult;
import com.rydr.request.CodeVerifyRequest;

/**
 * Controller endpoint for handling SMS verification code generation and verification.
 *
 * @author Rydr Team
 */
@RestController
@RequestMapping("/verify-code")
@Slf4j
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * Generate verification code based on identity type and phone number.
     *
     * @param identity    user role/identity type (1: passenger, 2: driver)
     * @param phoneNumber target mobile phone number
     * @return ResponseResult wrapping the generated code payload
     */
    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity, @PathVariable("phoneNumber") String phoneNumber) {
        log.info("[VerifyCode] Generating code for identity: {}, phone: {}", identity, phoneNumber);
        return verifyCodeService.generate(identity, phoneNumber);
    }

    /**
     * Verify the code submitted by passenger or driver.
     *
     * @param request payload containing identity, phone number, and verification code
     * @return ResponseResult status indicating validation result
     */
    @PostMapping("/verify")
    public ResponseResult verify(@RequestBody CodeVerifyRequest request) {
        log.info("[VerifyCode] Verifying code request: {}", JSONObject.fromObject(request));
        String phoneNumber = request.getPhoneNumber();
        int identity = request.getIdentity();
        String code = request.getCode();

        return verifyCodeService.verify(identity, phoneNumber, code);
    }
}

