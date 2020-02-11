package edu.cooper.ee.ece366.groceries.store;

import edu.cooper.ee.ece366.groceries.model.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroceryStoreImpl implements GroceryStore {

  private static final Map<Long, Item> itemsById;

  static {
    itemsById =
        List.of(
                new Item(1L, "Chicken Soup", 2.22),
                new Item(2L, "Milk", 100.0),
                new Item(3L, "Eggs", 5.19),
                new Item(4L, "Butter", 3.49))
            .stream()
            .collect(Collectors.toMap(item -> item.getId(), item -> item));
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
}
