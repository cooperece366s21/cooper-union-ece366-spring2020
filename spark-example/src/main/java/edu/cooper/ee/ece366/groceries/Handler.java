package edu.cooper.ee.ece366.groceries;

import edu.cooper.ee.ece366.groceries.model.Item;
import edu.cooper.ee.ece366.groceries.model.User;
import java.util.List;
import java.util.Map;
import spark.Request;

public class Handler {

  private final Service service;

  public Handler(Service service) {
    this.service = service;
  }

  public Map<Item, Integer> getCart(Request request) {
    Long userId = getUserId(request);
    User user = new User(userId, "name not implemented");
    return service.getCart(user);
  }

  public Map<Item, Integer> addItemToCart(Request request) {
    Long userId = getUserId(request);
    Long itemId = getItemId(request);
    User user = new User(userId, "name not implemented");
    Item item = new Item(itemId, "name not implemented", 0.00);
    return service.addItemToCart(user, item);
  }

  public List<Item> getGroceries(Request request) {
    return service.getGroceries();
  }

  public List<Item> getUserRecommendations(Request request) {
    Long userId = getUserId(request);
    User user = new User(userId, "name not implemented");
    return service.getUserRecommendations(user);
  }

  private Long getUserId(final Request request) {
    return Long.valueOf(request.params(":userId"));
  }

  private Long getItemId(final Request request) {
    return Long.valueOf(request.params(":itemId"));
  }
}
