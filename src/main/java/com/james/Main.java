package com.james;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main {
  
  public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
    new FoodMe().start();
  }
}
