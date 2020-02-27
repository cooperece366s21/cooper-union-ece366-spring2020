package edu.cooper.ee.ece366.groceries;

import com.google.gson.Gson;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheClientBuilder;
import edu.cooper.ee.ece366.groceries.store.CartStore;
import edu.cooper.ee.ece366.groceries.store.CartStoreImpl;
import edu.cooper.ee.ece366.groceries.store.GroceryStoreJdbc;
import edu.cooper.ee.ece366.groceries.util.JsonTransformer;
import java.sql.Connection;
import java.sql.DriverManager;
import spark.Spark;

public class Main {

  public static void main(String[] args) throws Exception {
    // initialize dependency tree
    Gson gson = new Gson();
    CartStore cartStore = new CartStoreImpl();
    MemcacheClient<String> memcacheClient =
        MemcacheClientBuilder.newStringClient().withAddress("localhost").connectAscii();
    // make we wait until the client has connected to the server
    ConnectFuture.connectFuture(memcacheClient).toCompletableFuture().get();

    String url = "jdbc:h2:~/testdb";
    //    GroceryStore groceryStore = new GroceryStoreInMemory();
    //    GroceryStore groceryStore = new GroceryStoreMemcached(memcacheClient, gson);
    Connection connection = DriverManager.getConnection(url);
    GroceryStoreJdbc groceryStore = new GroceryStoreJdbc(connection);
    groceryStore.populateDb();
    //    Jdbi jdbi = Jdbi.create(url);
    //    GroceryStoreJdbi groceryStore = new GroceryStoreJdbi(jdbi);
    Service service = new Service(cartStore, groceryStore);
    Handler handler = new Handler(service, gson);

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
    Spark.get("/items/:itemId", (req, res) -> handler.getItem(req), jsonTransformer);
    Spark.post("/items/create", (req, res) -> handler.createItem(req), jsonTransformer);
  }
}
