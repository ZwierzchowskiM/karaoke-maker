package com.karaoke.karaokemaker.config;

import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CachingConfig {


//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        Cache songsCache = new ConcurrentMapCache("Songs");
//        cacheManager.setCaches(Arrays.asList(songsCache));
//        return cacheManager;
//    }
}
