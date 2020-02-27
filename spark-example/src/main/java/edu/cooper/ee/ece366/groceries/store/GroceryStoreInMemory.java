package edu.cooper.ee.ece366.groceries.store;

import edu.cooper.ee.ece366.groceries.Handler.CreateItemRequest;
import edu.cooper.ee.ece366.groceries.model.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class GroceryStoreInMemory implements GroceryStore {

  private static final ConcurrentMap<Long, Item> itemsById;
  private static final AtomicLong nextId = new AtomicLong(4);

  static {
    itemsById =
        List.of(
                new Item(1L, "Chicken Soup", 2.22),
                new Item(2L, "Milk", 100.0),
                new Item(3L, "Eggs", 5.19),
                new Item(4L, "Butter", 3.49))
            .stream()
            .collect(Collectors.toConcurrentMap(item -> item.getId(), item -> item));
  }

  @Override
  public List<Item> getItems() {
    return new ArrayList<>(itemsById.values());
  }

  @Override
  public List<Item> searchItems(final String name) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public Item getItem(final Long id) {
    return itemsById.getOrDefault(id, null);
  }

  @Override
  public Item addItem(final CreateItemRequest createItemRequest) {
    long id = nextId.getAndIncrement();
    Item item = new Item(id, createItemRequest.getName(), createItemRequest.getCost());
    itemsById.put(id, item);
    return item;
  }
}
