package org.server;

/** This class represents a user. {@code @Author} adhit2 */
public class User {
  private final PrivateUser privateUser;
  private final PublicUser publicUser;

  /**
   * Constructs a new {@link User} with the specified private user and public user.
   *
   * @param privateUser the private user info
   * @param publicUser the public user info
   */
  public User(PrivateUser privateUser, PublicUser publicUser) {
    this.privateUser = privateUser;
    this.publicUser = publicUser;
  }

  /**
   * Returns the private user info.
   *
   * @return the private user info
   */
  public PrivateUser getPrivateUser() {
    return privateUser;
  }

  /**
   * Returns the public user info.
   *
   * @return the public user info
   */
  public PublicUser getPublicUser() {
    return publicUser;
  }
}
