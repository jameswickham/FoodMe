package com.james;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

  private String connectionUrl = "jdbc:mysql://localhost:3306/interlokuidb?serverTimezone=UTC";
  private String selectStatement = "SELECT * FROM my_recipes";

  public void connectToDb() {
    try {
      Connection connection = DriverManager.getConnection(connectionUrl, "interlokuidb", "int3rL0cku1DB");
      PreparedStatement ps = connection.prepareStatement(selectStatement);
      ResultSet rs = ps.executeQuery();
      while(rs.next()) {
        System.out.println(rs.getInt(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
      }
    } 
    catch (SQLException e) {
      System.out.println(e);
    }
  }
}