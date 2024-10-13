package com.hangman.adapter.rest.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.hangman.adapter.rest")
class FeignConfiguration {
}
