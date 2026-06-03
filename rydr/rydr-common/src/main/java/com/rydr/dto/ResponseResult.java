package com.rydr.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import com.rydr.constatnt.BusinessInterfaceStatus;

import java.io.Serializable;

/**
 * Generic unified response wrapper for all microservice APIs.
 *
 * @param <T> Payload data type
 * @author Rydr Team
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unchecked")
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;

    /**
     * Return success response with data payload (status: 200).
     *
     * @param data response payload content
     * @param <T>  payload type
     * @return ResponseResult instance wrapping the payload
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>()
                .setCode(BusinessInterfaceStatus.SUCCESS.getCode())
                .setMessage(BusinessInterfaceStatus.SUCCESS.getValue())
                .setData(data);
    }

    /**
     * Return default empty success response (status: 200).
     *
     * @param <T> payload type
     * @return ResponseResult instance without payload
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * Return internal server error response (status: 500).
     *
     * @param data error detail payload
     * @param <T>  payload type
     * @return ResponseResult instance wrapping error details
     */
    public static <T> ResponseResult<T> fail(T data) {
        return new ResponseResult<T>()
                .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .setData(data);
    }

    /**
     * Custom error response with specific error code and message.
     *
     * @param code    error status code
     * @param message human-readable error description
     * @param <T>     payload type
     * @return ResponseResult instance
     */
    public static <T> ResponseResult<T> fail(int code, String message) {
        return new ResponseResult<T>().setCode(code).setMessage(message);
    }

    /**
     * Custom error response with code, message, and additional error data payload.
     *
     * @param code    error status code
     * @param message human-readable error description
     * @param data    error detail data
     * @param <T>     payload type
     * @return ResponseResult instance
     */
    public static <T> ResponseResult<T> fail(int code, String message, T data) {
        return new ResponseResult<T>().setCode(code).setMessage(message).setData(data);
    }
}

