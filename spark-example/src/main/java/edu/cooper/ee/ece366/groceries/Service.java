package edu.cooper.ee.ece366.groceries;

import edu.cooper.ee.ece366.groceries.model.Item;
import edu.cooper.ee.ece366.groceries.model.User;
import edu.cooper.ee.ece366.groceries.store.CartStore;
import edu.cooper.ee.ece366.groceries.store.GroceryStore;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {

  private final CartStore cartStore;
  private final GroceryStore groceryStore;

  public Service(CartStore cartStore, GroceryStore groceryStore) {
    this.cartStore = cartStore;
    this.groceryStore = groceryStore;
  }

  public Map<Item, Integer> getCart(User user) {
    return cartStore.getCart(user);
  }

  public Map<Item, Integer> addItemToCart(User user, Item item) {
    cartStore.addItem(user, item);
    return cartStore.getCart(user);
  }

  public List<Item> getGroceries() {
    return groceryStore.getItems();
  }

  public List<Item> getUserRecommendations(User user) {
    List<Item> items = groceryStore.getItems();
    Map<Item, Integer> cart = cartStore.getCart(user);
    return items.stream().filter(item -> !cart.containsKey(item)).collect(Collectors.toList());
  }
}
