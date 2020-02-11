package edu.cooper.ee.ece366.groceries;

import edu.cooper.ee.ece366.groceries.store.CartStore;
import edu.cooper.ee.ece366.groceries.store.CartStoreImpl;
import edu.cooper.ee.ece366.groceries.store.GroceryStore;
import edu.cooper.ee.ece366.groceries.store.GroceryStoreImpl;
import edu.cooper.ee.ece366.groceries.util.JsonTransformer;
import spark.Spark;

public class Main {

  public static void main(String[] args) {
    // initialize dependency tree
    CartStore cartStore = new CartStoreImpl();
    GroceryStore groceryStore = new GroceryStoreImpl();
    Service service = new Service(cartStore, groceryStore);
    Handler handler = new Handler(service);

    JsonTransformer jsonTransformer = new JsonTransformer();

    // initialize routes
    Spark.get("/ping", (req, res) -> "OK");
    Spark.get("/cart/:userId", (req, res) -> handler.getCart(req), jsonTransformer);
    Spark.put(
        "/cart/:userId/item/:itemId/add",
        (req, res) -> handler.addItemToCart(req),
        jsonTransformer);
    Spark.get("/groceries", (req, res) -> handler.getGroceries(req), jsonTransformer);
    Spark.get(
        "/user/:userId/recommendations",
        (req, res) -> handler.getUserRecommendations(req),
        jsonTransformer);
  }
}
