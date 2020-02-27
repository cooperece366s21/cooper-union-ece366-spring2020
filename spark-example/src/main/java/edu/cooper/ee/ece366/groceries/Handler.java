package edu.cooper.ee.ece366.groceries;

import com.google.gson.Gson;
import edu.cooper.ee.ece366.groceries.model.Cart;
import edu.cooper.ee.ece366.groceries.model.Item;
import edu.cooper.ee.ece366.groceries.model.User;
import java.util.List;
import spark.Request;

public class Handler {

  private final Service service;
  private final Gson gson;

  public Handler(Service service, Gson gson) {
    this.service = service;
    this.gson = gson;
  }

  public Cart getCart(Request request) {
    Long userId = getUserId(request);
    User user = new User(userId, "name not implemented");
    return service.getCart(user);
  }

  public Cart addItemToCart(Request request) {
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

  public Item createItem(final Request req) {
    CreateItemRequest createItemRequest = gson.fromJson(req.body(), CreateItemRequest.class);
    return service.createItem(createItemRequest);
  }

  public Object getItem(final Request req) {
    Long itemId = Long.valueOf(req.params(":itemId"));
    return service.getItem(itemId);
  }

  public class CreateItemRequest {
    private final String name;
    private final Double cost;

    public CreateItemRequest(String name, Double cost) {
      this.name = name;
      this.cost = cost;
    }

    public String getName() {
      return name;
    }

    public Double getCost() {
      return cost;
    }
  }
}
