package org.server.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** This class represents the configuration for the RestTemplate. {@code @Author} adhit2 */
@Configuration
public class RestTemplateConfig {

  /**
   * Returns a new {@link RestTemplate}.
   *
   * @return a new {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setMessageConverters(Collections.singletonList(new FormHttpMessageConverter()));
    return restTemplate;
  }
}
