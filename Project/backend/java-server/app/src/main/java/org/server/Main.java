package org.server;

import io.github.cdimascio.dotenv.Dotenv;
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

public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    System.setProperty(
        "SPRING_DATASOURCE_URL", "jdbc:postgresql://" + dotenv.get("PGURL") + "/main");
    System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("PGUSER"));
    System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("PGPASSWORD"));
  }
}
