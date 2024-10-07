/**
This class contains the information of a {@link User} object. One user is a row in the database, and contains the following fields:
indentifer, first name, last name, riot ID, and discord ID.
@author adhit2
*/
package org.server;

public class User {

    /**
     * The unique identifier for the user.
     */
    private String identifier;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The Riot ID of the user.
     */
    private String riotID;

    /**
     * The Discord ID of the user.
     */
    private String discordID;

    /**
     * Constructs a new User with the specified details.
     *
     * @param identifier the unique identifier for the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param riotID the Riot ID of the user
     * @param discordID the Discord ID of the user
     */
    public User(
        String identifier,
        String firstName,
        String lastName,
        String riotID,
        String discordID
    ) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.riotID = riotID;
        this.discordID = discordID;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param identifier the new identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the Riot ID of the user.
     *
     * @return the Riot ID
     */
    public String getRiotID() {
        return riotID;
    }

    /**
     * Sets the Riot ID of the user.
     *
     * @param riotID the new Riot ID
     */
    public void setRiotID(String riotID) {
        this.riotID = riotID;
    }

    /**
     * Gets the Discord ID of the user.
     *
     * @return the Discord ID
     */
    public String getDiscordID() {
        return discordID;
    }

    /**
     * Prints out all value of the user to the console
     * @return a string representation of the user
     */
    public String toString() {
        return (
            "User{" +
            "identifier='" +
            identifier +
            '\'' +
            ", firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            ", riotID='" +
            riotID +
            '\'' +
            ", discordID='" +
            discordID +
            '\'' +
            '}'
        );
    }
}
