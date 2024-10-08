package org.server;

import java.util.HashSet;

/**
This class contains the information of a {@link User} object. One user is a row in the database, and contains the following fields:
identifer, first name, last name, riot ID, a {@link HashSet} for determining the users that this user has matched with but not the other way around, a {@link HashSet} for determining the users that this user has matched with that have also matched with them,
and discord ID.
@author adhit2
*/
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
     * The ids of users that this user has matched with but not the other way around.
     */
    private HashSet<String> oneWayMatched = new HashSet<>();

    /**
     * The ids of users that this user has matched with that have also matched with them.
     */
    private HashSet<String> twoWayMatched = new HashSet<>();

    /**
     * Constructs a new User with the specified details.
     *
     * @param identifier the unique identifier for the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param riotID the Riot ID of the user
     * @param discordID the Discord ID of the user
     * @param oneWayMatched the array of strings ids of users that this user has matched with but not the other way around
     * @param twoWayMatched the array of strings ids of users that this user has matched with that have also matched with them
     */
    public User(
        String identifier,
        String firstName,
        String lastName,
        String riotID,
        String discordID,
        String[] oneWayMatched,
        String[] twoWayMatched
    ) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.riotID = riotID;
        this.discordID = discordID;
        for (String s : oneWayMatched) {
            this.oneWayMatched.add(s);
        }
        for (String s : twoWayMatched) {
            this.twoWayMatched.add(s);
        }
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
     * Sets the Discord ID of the user.
     * @param discordID the discord ID to be set.
     */
    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    /**
     * Adds a user to the oneWayMatched set.
     *
     * @param user the user to be added
     * @return true if the user was added, false if the user was already in the set
     */
    public boolean addOneWayMatched(String user) {
        if (oneWayMatched == null) {
            oneWayMatched = new HashSet<>();
        }
        return oneWayMatched.add(user);
    }

    /**
     * Deletes a user from the oneWayMatched set.
     *
     * @param user the user to be deleted
     * @return true if the user was removed, false if the user was not in the set
     */
    public boolean deleteOneWayMatched(String user) {
        if (oneWayMatched != null) {
            return oneWayMatched.remove(user);
        }
        return false;
    }

    /**
     * Checks if a user is in the oneWayMatched set.
     *
     * @param user the user to be checked
     * @return true if the user is in the set, false otherwise
     */
    public boolean isOneWayMatched(String user) {
        if (oneWayMatched != null) {
            return oneWayMatched.contains(user);
        }
        return false;
    }

    /**
     * Adds a user to the twoWayMatched set.
     * @param user the user to be added
     * @return true if the user was added, false if the user was already in the set
     */
    public boolean moveToTwoWayMatched(String user) {
        if (oneWayMatched != null) {
            if (oneWayMatched.remove(user)) {
                if (twoWayMatched == null) {
                    twoWayMatched = new HashSet<>();
                }
                return twoWayMatched.add(user);
            }
        }
        return false;
    }

    /**
     * Deletes a user from the twoWayMatched set.
     *
     * @param user the user to be deleted
     * @return true if the user was removed, false if the user was not in the set
     */
    public boolean deleteTwoWayMatched(String user) {
        if (twoWayMatched != null) {
            return twoWayMatched.remove(user);
        }
        return false;
    }

    /**
     * Checks if a user is in the twoWayMatched set.
     *
     * @param user the user to be checked
     * @return true if the user is in the set, false otherwise
     */
    public boolean isTwoWayMatched(String user) {
        if (twoWayMatched != null) {
            return twoWayMatched.contains(user);
        }
        return false;
    }

    /**
     * Prints out all value of the user to the console
     * @return a string representation of the user
     */
    @Override
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
            ", oneWayMatched=" +
            oneWayMatched +
            ", twoWayMatched=" +
            twoWayMatched +
            '}'
        );
    }
}
