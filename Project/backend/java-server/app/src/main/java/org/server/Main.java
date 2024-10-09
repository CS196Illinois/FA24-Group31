package org.server;

public class Main {

    public static void main(String[] args) {
        //start server listening on port 8080:
        DBController dbController = new DBController("jdbc:sqlite:main.db");
        System.out.println(
            dbController.getUser("yzk7", true).toJSON().toString()
        );
    }
}
