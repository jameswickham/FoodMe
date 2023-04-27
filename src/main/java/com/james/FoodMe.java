package com.james;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FoodMe {

  private static final int MAX_INGREDIENTS = 4;
  private static final String JSON_ID_PATH = "$[*].id";
  private static final String JSON_TITLE_PATH = "$[*].title";
  private static final String JSON_RECIPE_URL_PATH = "$[*].spoonacularSourceUrl";
  private static final String INGREDIENTS_INPUT_MESSAGE = "Enter ingredient #%s%s";
  private static final String RECIPE_RESULTS_MESSAGE = "The results are: \n";
  private static final String SEE_RECIPE_DETAILS_MESSAGE = " \nChoose a receipe to get the information";
  private static final String RECIPE_RESULTS_LIST = "[%s] %s";
  private static final String SAVE_MESSAGE = "Do you want to save this recipe? [Y/N]";
  private static final String WELCOME_MESSAGE = "Welcome, would you like to view you previously saved recipes? [Y/N]";

  Scanner sc = new Scanner(System.in);
  private int selection;
  private String title;

  public int getSelection() {
    return selection;
  }

  public void setSelection(int selection) {
    this.selection = selection;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @SuppressWarnings("unchecked")
  public void start() throws IOException, URISyntaxException, SQLException {
    DatabaseConnection dc = new DatabaseConnection();
    DatabaseExecutor de = new DatabaseExecutor();
    String userDecision = userDecision(WELCOME_MESSAGE);
    if (userDecision.equals("Y")) {
      HashMap<String, String> savedRecipesList = de.queryDatabase(dc.createConnection());
      displaySavedRecipes(savedRecipesList);
    }
    String ingredientsList = inputIngredients();
    HttpRequest httpRequest = new HttpRequest();
    URI request = httpRequest.buildRequest("findByIngredients", "ingredients", ingredientsList);
    String responsePayload = httpRequest.makeRequest(request);
    List<Integer> idList = (List<Integer>) JsonExtractor.extractJsonData(responsePayload, JSON_ID_PATH);
    List<String> titleList = (List<String>) JsonExtractor.extractJsonData(responsePayload, JSON_TITLE_PATH);
    displayRecipeResults(titleList);
    request = httpRequest.buildRequest("informationBulk", "ids", String.valueOf(idList.get(getSelection())));
    responsePayload = httpRequest.makeRequest(request);
    List<String> recipeUrlList = (List<String>) JsonExtractor.extractJsonData(responsePayload, JSON_RECIPE_URL_PATH);
    displayRecipeInformation(recipeUrlList);
    userDecision = userDecision(SAVE_MESSAGE);
    if (userDecision.equals("Y")) {
      de.queryDatabase(dc.createConnection(), getTitle(), recipeUrlList.get(0));
    }
    dc.closeConnection(dc.createConnection());
  }

  public String inputIngredients() {
    List<String> ingredientslist = new ArrayList<String>();
    int i = 0;
    while (i < MAX_INGREDIENTS) {
      int order = i + 1;
      System.out.println(String.format(INGREDIENTS_INPUT_MESSAGE, order, ":"));
      String input = sc.next();
      ingredientslist.add(input);
      i++;
    }
    return String.join(",+", ingredientslist);
  }

  public void displaySavedRecipes(HashMap<String, String> savedRecipesList) {
    for (Map.Entry<String, String> entry : savedRecipesList.entrySet()) {
      System.out.println(String.format(RECIPE_RESULTS_LIST, entry.getKey(), entry.getValue()));
    }
  }

  public String userDecision(String message) {
    String input;
    do {
      System.out.println(message);
      input = sc.next();
    } while (!(input.equals("Y")) && !(input.equals("N")));
    return input;
  }

  public void displayRecipeResults(List<String> titleList) {
    int input;
    System.out.println(titleList);
    System.out.println(RECIPE_RESULTS_MESSAGE);
    for (String title : titleList) {
      System.out.println(String.format(RECIPE_RESULTS_LIST, titleList.indexOf(title), title));
    }
    System.out.println(SEE_RECIPE_DETAILS_MESSAGE);
    input = sc.nextInt();
    setSelection(input);
    setTitle(titleList.get(input));
  }

  public void displayRecipeInformation(List<String> recipeUrlList) {
    System.out.println(recipeUrlList.get(0));
  }

  public void displayRandomRecipe() {
  }
}