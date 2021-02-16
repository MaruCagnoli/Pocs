package com.poc.PocJPAyRedis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class CacheService {
    @Autowired
    private RedisTemplate< String, Object > template;

    public String getValue( final String key ) {

        return (String)template.opsForValue().get( key );
    }

    public void setValue( final String key, final String value ) {

        template.opsForValue().set( key, value );
    }
}
