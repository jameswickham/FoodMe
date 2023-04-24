package com.james;

import java.io.IOException;
import java.util.ArrayList;


public class Main {
  
  public static void main(String[] args) throws IOException {
    
    new FoodMe().start();
    
    HttpRequest httpRequest = new HttpRequest();
    JsonParser jsonParser = new JsonParser("$[*].id");
    
    String responsePayload = httpRequest.makeRequest("/recipes/findByIngredients?ingredients=chicken,%2Bleek,%2B&number=2&apiKey=8a95d4a356be44c1abe7f1d2362f2d79");
    ArrayList<Integer> resutString = jsonParser.extractId(responsePayload);
    
    System.out.println(resutString);
    
  }

}
