package edu.cooper.ee.ece366.groceries.model;

import com.google.gson.annotations.Expose;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {

  @Expose private final List<CartItem> items;

  private final Map<Item, Integer> cart;

  public Cart(Map<Item, Integer> cart) {
    this.cart = cart;
    this.items =
        cart.entrySet().stream()
            .map(entry -> new CartItem(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
  }

  public Map<Item, Integer> getCart() {
    return cart;
  };

  public List<CartItem> getItems() {
    return items;
  }

  public static class CartItem {

    @Expose private final Item item;
    @Expose private final Integer quantity;

    public CartItem(Item item, Integer quantity) {
      this.item = item;
      this.quantity = quantity;
    }

    public Item getItem() {
      return item;
    }

    public Integer getQuantity() {
      return quantity;
    }
  }
}
