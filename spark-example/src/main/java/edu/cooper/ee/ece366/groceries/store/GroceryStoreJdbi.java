package edu.cooper.ee.ece366.groceries.store;

import edu.cooper.ee.ece366.groceries.Handler;
import edu.cooper.ee.ece366.groceries.model.Item;
import java.util.List;
import org.jdbi.v3.core.Jdbi;

public class GroceryStoreJdbi implements GroceryStore {

  private final Jdbi jdbi;

  public GroceryStoreJdbi(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public void populateDb() {
    jdbi.withHandle(
        handle ->
            handle.execute(
                "create table items (id bigint auto_increment, name varchar(255), cost double );"));
  }

  @Override
  public List<Item> getItems() {
    return jdbi.withHandle(
        handle ->
            handle.createQuery("select id, name, cost from items").mapToBean(Item.class).list());
  }

  @Override
  public List<Item> searchItems(final String name) {
    return jdbi.withHandle(
        handle ->
            handle
                .createQuery("select id, name, cost from items where name like :name")
                .bind("name", String.format("%%%s%%", name))
                .mapToBean(Item.class)
                .list());
  }

  @Override
  public Item getItem(final Long id) {
    return jdbi.withHandle(
        handle ->
            handle
                .createQuery("select id, name, cost from items where id = :id")
                .bind("id", id)
                .mapToBean(Item.class)
                .one());
  }

  @Override
  public Item addItem(final Handler.CreateItemRequest createItemRequest) {
    return jdbi.withHandle(
        handle ->
            handle
                .createUpdate("insert into items (name, cost) values (:name, :cost)")
                .bind("name", createItemRequest.getName())
                .bind("cost", createItemRequest.getCost())
                .executeAndReturnGeneratedKeys("id")
                .mapToBean(Item.class)
                .one());
  }
}
