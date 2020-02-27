package edu.cooper.ee.ece366.groceries.store;

import edu.cooper.ee.ece366.groceries.Handler;
import edu.cooper.ee.ece366.groceries.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroceryStoreJdbc implements GroceryStore {

  private final Connection connection;

  public GroceryStoreJdbc(Connection connection) {
    this.connection = connection;
  }

  public void populateDb() {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(
          "create table items (id bigint auto_increment, name varchar(255), cost double );");
      preparedStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException("failed to init db", e);
    }
  }

  @Override
  public List<Item> getItems() {
    try {
      ResultSet rs =
          connection.prepareStatement("select id, name, cost from items;").executeQuery();
      return mapToItems(rs);
    } catch (SQLException e) {
      e.printStackTrace();
      return List.of();
    }
  }

  private List<Item> mapToItems(final ResultSet rs) throws SQLException {
    ArrayList<Item> items = new ArrayList<>();
    while (rs.next()) {
      Item item = new Item(rs.getLong("id"), rs.getString("name"), rs.getDouble("cost"));
      items.add(item);
    }
    return items;
  }

  @Override
  public List<Item> searchItems(final String name) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("select id, name, cost from items where name like ?;");
      preparedStatement.setString(1, String.format("%%%s%%", name));
      ResultSet resultSet = preparedStatement.executeQuery();
      return mapToItems(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
      return List.of();
    }
  }

  @Override
  public Item getItem(final Long id) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("select id, name, cost from items where id = ?;");
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<Item> items = mapToItems(resultSet);
      return items.stream().findFirst().orElse(null);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Item addItem(final Handler.CreateItemRequest createItemRequest) {
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "insert into items (name, cost) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, createItemRequest.getName());
      preparedStatement.setDouble(2, createItemRequest.getCost());
      int rowsUpdated = preparedStatement.executeUpdate();
      if (rowsUpdated == 0) {
        throw new RuntimeException("error inserting item");
      }
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        return new Item(
            generatedKeys.getLong(1), createItemRequest.getName(), createItemRequest.getCost());
      } else {
        throw new RuntimeException("error getting key after inserting item");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
