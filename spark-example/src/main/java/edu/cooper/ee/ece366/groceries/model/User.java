package edu.cooper.ee.ece366.groceries.model;

public class User {

  private final Long id;
  private final String name;

  public User(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User) obj;
    return super.equals(obj) || (this.id.equals(other.getId()) && this.name
        .equals(other.getName()));
  }
}
