package com.cos.jwtex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      //config.addAllowedOrigin("*"); // e.g. http://domain1.com
     // config.addAllowedHeader("*"); //??
      //config.addAllowedMethod("*"); //put delete post 허용하는것
      //cors
      //source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }

}
