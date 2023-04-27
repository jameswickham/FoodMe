package com.james;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseExecutor {

  private static final String SELECT_STATEMENT = "SELECT * FROM my_recipes";
  private static final String INSERT_STATEMENT  = "INSERT INTO my_recipes (name, url) VALUES (?,?)";
  private static final String ENTRY_SAVED_MESSAGE  = "Recipe sucessfully saved";

  public HashMap<String, String> queryDatabase(Connection con) throws SQLException {
    HashMap<String, String> resultList = new HashMap<String, String>();
    ResultSet rs = con.prepareStatement(SELECT_STATEMENT).executeQuery();
    while (rs.next()) {
      resultList.put(rs.getString("name"), rs.getString("url"));
    }
    return resultList;
  }

  public void queryDatabase(Connection con, String name, String url) throws SQLException {
    PreparedStatement ps = con.prepareStatement(INSERT_STATEMENT);
    ps.setString(1, name);
    ps.setString(2, url);
    ps.executeUpdate();
    System.out.println(ENTRY_SAVED_MESSAGE);
  }
}
