package org.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for the main server, starts the server and sets up the routes. Prints out the beans
 * provided by Spring Boot in the CLI on startup.
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
  public static void main(String[] args) {SpringApplication.run(Main.class, args);}
}
