package edu.cooper.ee.ece366.groceries.store;

import edu.cooper.ee.ece366.groceries.Handler.CreateItemRequest;
import edu.cooper.ee.ece366.groceries.model.Item;
import java.util.List;

public interface GroceryStore {

  List<Item> getItems();

  List<Item> searchItems(String name);

  Item getItem(Long id);

  Item addItem(CreateItemRequest createItemRequest);
}
