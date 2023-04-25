package com.james;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodMe {

  private static final int MAX_INGREDIENTS = 4;
  private static final String JSON_ID_PATH = "$[*].id";
  private static final String JSON_TITLE_PATH = "$[*].title";
  private static final String JSON_RECIPE_URL_PATH = "$[*].spoonacularSourceUrl";
  private static final String INGREDIENTS_INPUT_MESSAGE = "Enter ingredient #%s%s";
  private static final String RECIPE_RESULTS_MESSAGE = "The results are: \n";
  private static final String SEE_RECIPE_DETAILS_MESSAGE = " \nChoose a receipe to get the information";
  private static final String RECIPE_RESULTS_LIST = "[%s]%s";

  private int selection;

  public int getSelection() {
    return selection;
  }

  public void setSelection(int selection) {
    this.selection = selection;
  }

  Scanner sc = new Scanner(System.in);

  @SuppressWarnings("unchecked")
  public void start() throws IOException, URISyntaxException {
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
    DatabaseHandler dbh = new DatabaseHandler();
    dbh.connectToDb();
  }

  public String inputIngredients() {
    List<String> ingredientslist = new ArrayList<String>();
    int i = 0;
    while (i <= MAX_INGREDIENTS) {
      int order = i + 1;
      System.out.println(String.format(INGREDIENTS_INPUT_MESSAGE, order, ":"));
      String input = sc.next();
      ingredientslist.add(input);
      i++;
    }
    return String.join(",+", ingredientslist);
  }

  public void selectSavedRecipe() {

  }

  public void saveRecipe() {

  }

  public void displayRecipeResults(List<String> titleList) {

    System.out.println(titleList.size());

    System.out.println(RECIPE_RESULTS_MESSAGE);
    for (String title : titleList) {
      System.out.println(String.format(RECIPE_RESULTS_LIST, titleList.indexOf(title), title));
    }
    System.out.println(SEE_RECIPE_DETAILS_MESSAGE);
    int choice = sc.nextInt();
    setSelection(choice);
  }

  public void displayRecipeInformation(List<String> recipeUrlList) {

    System.out.println(recipeUrlList.get(0));

  }

  public void displayRandomRecipe() {

  }
}
