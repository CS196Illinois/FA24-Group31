package org.server;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Class for the main server, starts the server and sets up the routes. Prints out the beans provided by Spring Boot in the CLI on startup.
 *
 * @author adhit2
 */
@SpringBootApplication
public class Main {

  /**
   * Main method to start the server.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  /**
   * Prints out the beans provided by Spring Boot in the CLI on startup.
   *
   * @param ctx The application context.
   * @return A command line runner that prints out the beans provided by Spring Boot in the CLI on startup.
   */
  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {
      System.out.println(
              "Let's inspect the beans provided by Spring Boot:"
      );

      String[] beanNames = ctx.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        System.out.println(beanName);
      }
    };
  }
}
