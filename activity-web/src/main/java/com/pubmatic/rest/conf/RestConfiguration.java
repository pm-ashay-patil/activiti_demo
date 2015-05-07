package com.pubmatic.rest.conf;

import com.pubmatic.rest.service.api.ActivitiRestResponseFactory;
import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.common.application.DefaultContentTypeResolver;
import org.activiti.rest.service.api.RestResponseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ashay Patil
 */
@Configuration
public class RestConfiguration {

    @Bean()
    public RestResponseFactory restResponseFactory() {
      RestResponseFactory restResponseFactory = new ActivitiRestResponseFactory();
      return restResponseFactory;
    }

    @Bean()
    public ContentTypeResolver contentTypeResolver() {
      ContentTypeResolver resolver = new DefaultContentTypeResolver();
      return resolver;
    }
}
