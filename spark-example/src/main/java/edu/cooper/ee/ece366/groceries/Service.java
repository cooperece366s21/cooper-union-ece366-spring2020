package edu.cooper.ee.ece366.groceries;

import edu.cooper.ee.ece366.groceries.model.Cart;
import edu.cooper.ee.ece366.groceries.model.Item;
import edu.cooper.ee.ece366.groceries.model.User;
import edu.cooper.ee.ece366.groceries.store.CartStore;
import edu.cooper.ee.ece366.groceries.store.GroceryStore;
import java.util.List;
import java.util.stream.Collectors;

public class Service {

  private final CartStore cartStore;
  private final GroceryStore groceryStore;

  public Service(CartStore cartStore, GroceryStore groceryStore) {
    this.cartStore = cartStore;
    this.groceryStore = groceryStore;
  }

  public Cart getCart(User user) {
    return cartStore.getCart(user);
  }

  public Cart addItemToCart(User user, Item item) {
    cartStore.addItem(user, item);
    return cartStore.getCart(user);
  }

  public List<Item> getGroceries() {
    return groceryStore.getItems();
  }

  public List<Item> getUserRecommendations(User user) {
    List<Item> items = groceryStore.getItems();
    Cart cart = cartStore.getCart(user);
    return items.stream()
        .filter(item -> !cart.getCart().containsKey(item))
        .collect(Collectors.toList());
  }

  public Item createItem(final Handler.CreateItemRequest createItemRequest) {
    return groceryStore.addItem(createItemRequest);
  }

  public Item getItem(final Long itemId) {
    return groceryStore.getItem(itemId);
  }
}
