package edu.cooper.ee.ece366.groceries.model;

import com.google.gson.annotations.Expose;

public class Item {

  @Expose private final Long id;
  @Expose private final String name;
  @Expose private final Double cost;

  public Item(Long id, String name, Double cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id.intValue();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Item)) {
      return false;
    }
    Item other = (Item) obj;
    return super.equals(other)
        || (this.id.equals(other.getId())
            && this.name.equals(other.getName())
            && this.cost.equals(other.getCost()));
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
