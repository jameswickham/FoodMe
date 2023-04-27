package com.james;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

  private String username;
  private String password;
  private String connectionUrl = "jdbc:mysql://localhost:3306/interlokuidb?serverTimezone=UTC";

  public DatabaseConnection() throws FileNotFoundException, IOException {
    Properties login = new Properties();
    try (FileReader in = new FileReader("resources/login.properties")) {
      login.load(in);
    }
    this.username = login.getProperty("username");
    this.password = login.getProperty("password");
  }

  public Connection createConnection() throws SQLException {
    return DriverManager.getConnection(connectionUrl, username, password);
  }

  public void closeConnection(Connection dbc) throws SQLException {
    dbc.close();
  }
}