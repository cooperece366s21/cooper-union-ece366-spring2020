package edu.cooper.ee.ece366.groceries.store;

import com.google.gson.Gson;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheStatus;
import edu.cooper.ee.ece366.groceries.Handler;
import edu.cooper.ee.ece366.groceries.model.Item;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

public class GroceryStoreMemcached implements GroceryStore {

  private static final AtomicLong nextId = new AtomicLong(0);

  private final MemcacheClient<String> client;
  private final Gson gson;

  public GroceryStoreMemcached(MemcacheClient<String> client, Gson gson) {
    this.client = client;
    this.gson = gson;
  }

  @Override
  public List<Item> getItems() {
    throw new RuntimeException("cannot implement");
  }

  @Override
  public List<Item> searchItems(final String name) {
    throw new RuntimeException("cannot implement");
  }

  @Override
  public Item getItem(final Long id) {
    try {
      return client
          .get(id.toString())
          .thenApply(Optional::ofNullable)
          .thenApply(
              maybeItemJson ->
                  maybeItemJson.map(itemJson -> gson.fromJson(itemJson, Item.class)).orElse(null))
          .toCompletableFuture()
          .get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Item addItem(final Handler.CreateItemRequest createItemRequest) {
    Long id = nextId.getAndIncrement();
    Item item = new Item(id, createItemRequest.getName(), createItemRequest.getCost());
    try {
      return client
          .add(id.toString(), gson.toJson(item), 10000)
          .thenApply(
              memcacheStatus -> {
                if (!MemcacheStatus.OK.equals(memcacheStatus)) {
                  throw new RuntimeException("error");
                } else {
                  return item;
                }
              })
          .toCompletableFuture()
          .get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("error");
    }
  }
}
