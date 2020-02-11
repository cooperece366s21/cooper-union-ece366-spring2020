package edu.cooper.ee.ece366.groceries.store;

import edu.cooper.ee.ece366.groceries.model.Cart;
import edu.cooper.ee.ece366.groceries.model.Item;
import edu.cooper.ee.ece366.groceries.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CartStoreImpl implements CartStore {

  private final Map<User, Map<Item, Integer>> cart;

  public CartStoreImpl() {
    this.cart = new HashMap<>();
  }

  public CartStoreImpl(Map<User, Map<Item, Integer>> cart) {
    this.cart = cart;
  }

  public CartStoreImpl(User user, Collection<Item> items) {
    cart =
        Map.of(user, items.stream()
            .collect(Collectors.groupingBy(item -> item, Collectors.summingInt(t -> 1))));
  }

  @Override
  public Cart getCart(User user) {
    return new Cart(cart.getOrDefault(user, Map.of()));
  }

  @Override
  public void addItem(User user, Item item) {
    addItem(user, item, 1);
  }

  @Override
  public void addItem(User user, Item item, Integer quantity) {
    Map<Item, Integer> userCart = cart.getOrDefault(user, new HashMap<>());
    userCart.put(item, userCart.getOrDefault(item, 0) + quantity);
    cart.put(user, userCart);
  }

  @Override
  public Long checkout() {
    throw new UnsupportedOperationException("not yet implemented");
  }
}
