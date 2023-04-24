package com.james;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FoodMe {

  private static final int MIN_INGREDIENTS = 0;
  private static final int MAX_INGREDIENTS = 4;
  private static final String JSON_ID_PATH = "$[*].id";
  private static final String JSON_TITLE_PATH = "$[*].id";

  Scanner sc = new Scanner(System.in);
  
  public void start() throws IOException, URISyntaxException {
    
    ArrayList<String> ingredientsList = inputIngredients();
    HttpRequest httpRequest = new HttpRequest();
    String responsePayload = httpRequest.makeRequest("findByIngredients", "ingredients", ingredientsList);
    JsonParser jsonParser = new JsonParser();
    jsonParser.setPath(JSON_ID_PATH);
    ArrayList<Integer> idList = jsonParser.extractId(responsePayload);
    displayRecipeResults(idList);
    
  }

  public ArrayList<String> inputIngredients() {
    ArrayList<String> ingredientslist = new ArrayList<String>();

    int count = 0;

    while (count >= MIN_INGREDIENTS && count < MAX_INGREDIENTS) {
      int order = count + 1;

      System.out.println("Enter ingrdient #" + order + ":");

      String input = sc.next();
      ingredientslist.add(input);
      count++;
    }

    return ingredientslist;
  }

  public void selectRecipe() {

  }

  public void saveRecipe() {

  }

  public void displayRecipeResults(ArrayList<Integer> idList) {
    
    for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
      Integer integer = (Integer) iterator.next();
      
    }

  }

  public void displayRecipeInformation() {

  }

  public void displayRandomRecipe() {

  }
}
