package org.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** This class represents the application configuration. */
@Configuration
public class AppConfig {
  /**
   * Returns a new {@link RestTemplate}.
   *
   * @return a new {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
