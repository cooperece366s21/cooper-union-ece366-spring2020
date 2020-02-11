package edu.cooper.ee.ece366.groceries.model;

public class Item {

  private final Long id;
  private final String name;
  private final Double cost;

  public Item(Long id, String name, Double cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Item)) {
      return false;
    }
    Item other = (Item) obj;
    return super.equals(other) || (this.id.equals(other.getId()) && this.name
        .equals(other.getName()) && this.cost.equals(other.getCost()));
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getCost() {
    return cost;
  }
}
