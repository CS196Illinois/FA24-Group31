package org.server.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

  @Bean
  public FilterRegistrationBean<CommonsRequestLoggingFilter> loggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(10000);
    filter.setIncludeHeaders(false);
    FilterRegistrationBean<CommonsRequestLoggingFilter> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setFilter(filter);
    registrationBean.setOrder(1);
    return registrationBean;
  }
}
