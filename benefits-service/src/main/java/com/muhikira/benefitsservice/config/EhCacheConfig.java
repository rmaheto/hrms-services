package com.muhikira.benefitsservice.config;

import static com.muhikira.benefitsservice.util.AppConstants.JWT_TOKEN_CACHE;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfig {

  @Bean
  public JCacheCacheManager cacheManager() {
    // Get the EhCache caching provider
    CachingProvider cachingProvider = Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
    CacheManager cacheManager = cachingProvider.getCacheManager();

    // Define a simple cache configuration
    MutableConfiguration<Object, Object> config = new MutableConfiguration<>()
        .setStoreByValue(false)
        .setStatisticsEnabled(true);

    // Create jwtTokenCache if not already available
    cacheManager.createCache(JWT_TOKEN_CACHE, config);

    // Return JCacheCacheManager
    return new JCacheCacheManager(cacheManager);
  }
}