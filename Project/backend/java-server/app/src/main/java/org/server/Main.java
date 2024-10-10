package org.server;

public class Main {

    public static void main(String[] args) {
        //start server listening on port 8080:
        DBController dbController = new DBController("jdbc:sqlite:main.db");
        System.out.println(dbController.getUser("yzk5", true).toString());
        String[] one = { "aaksljfhaksjdfh", "alkdsfjhalksdjfh" };
        String[] two = { "sdkfjhsdjf", "skdjfhsdjfh" };
        User me = new User(
            "sdlfkjsdlfkjsdlfkjsdlkfj",
            "Adhi",
            "Thirumala",
            "uptonsinclairthe#jngl",
            "adhiadhiadhiadhi",
            one,
            two
        );
        dbController.addUser(me);
        System.out.println(me);
        System.out.println(
            dbController.getUser("sdlfkjsdlfkjsdlfkjsdlkfj", false).toString()
        );
    }
}
