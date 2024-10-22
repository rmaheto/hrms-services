package org.muhikira.attendanceservice.config;

import static org.muhikira.attendanceservice.util.AppConstants.JWT_TOKEN_CACHE;

import javax.cache.configuration.MutableConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;

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