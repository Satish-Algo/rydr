package com.rydr.order.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import com.rydr.common.entity.OrderLock;

import lombok.Data;

/**
 * Custom distributed lock implementation using Redis key-value expiration and Lua atomic release.
 * Ensures only one driver can grab an order concurrently.
 *
 * @author Rydr Team
 */
@Service
@Data
public class RedisLock implements Lock {

    private static final long DEFAULT_EXPIRE_SECONDS = 50;

    @Resource
    private RedisTemplate<Integer, Integer> redisTemplate;

    private OrderLock orderLock;

    /**
     * Acquire lock, retrying recursively with short polling sleep intervals until acquired.
     */
    @Override
    public void lock() {
        if (tryLock()) {
            return;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        lock();
    }

    /**
     * Non-blocking lock attempt using Redis setIfAbsent (SETNX).
     *
     * @return true if lock was acquired successfully, false otherwise
     */
    @Override
    public boolean tryLock() {
        int orderId = orderLock.getOrderId();
        int driverId = orderLock.getDriverId();

        Boolean success = redisTemplate.opsForValue().setIfAbsent(orderId, driverId, DEFAULT_EXPIRE_SECONDS, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    /**
     * Release lock atomically using Lua script to verify lock ownership before deletion.
     */
    @Override
    public void unlock() {
        DefaultRedisScript<List> getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(List.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/lock.lua")));

        redisTemplate.execute(getRedisScript, Collections.singletonList(orderLock.getOrderId()), Collections.singletonList(orderLock.getDriverId()));
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

