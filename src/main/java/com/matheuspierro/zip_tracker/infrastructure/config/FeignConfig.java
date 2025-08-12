package com.matheuspierro.zip_tracker.infrastructure.config;

import com.matheuspierro.zip_tracker.infrastructure.client.CepErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CepErrorDecoder();
    }
}
