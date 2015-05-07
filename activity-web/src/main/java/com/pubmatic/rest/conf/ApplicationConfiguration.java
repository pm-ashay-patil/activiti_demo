package com.pubmatic.rest.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
  @PropertySource(value = "classpath:properties/db.properties", ignoreResourceNotFound = true),
  @PropertySource(value = "classpath:properties/engine.properties", ignoreResourceNotFound = true),
})
@ComponentScan(basePackages = {
        "com.pubmatic.rest.conf"})
public class ApplicationConfiguration {
  
}
