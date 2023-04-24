package com.james;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequest {

  private static final String BASE_URL = "https://api.spoonacular.com%s";
  private static final String SCHEME = "https";
  private static final String HOST = "api.spoonacular.com";
  private static final String PATH = "/recipes/%s";
  private static final String API_KEY = "8a95d4a356be44c1abe7f1d2362f2d79";
  private static final String QUERY_STRING = "";

  CloseableHttpClient httpClient = HttpClients.createDefault();
  URIBuilder builder = new URIBuilder();

  public String makeRequest(String subPath, String paramName, ArrayList<String> ingredientsList) throws IOException, URISyntaxException {
    try {
      URI uri = new URIBuilder().setScheme(SCHEME).setHost(HOST).setPath(String.format(PATH, subPath))
          .setParameter(paramName, API_KEY).setParameter("apiKey", API_KEY).build();
      HttpGet httpGet = new HttpGet(uri);
      System.out.println("Executing request " + httpGet.getRequestLine());
      CloseableHttpResponse response = httpClient.execute(httpGet);
      try {
        // int responseCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String responsePayload = EntityUtils.toString(entity);
        return responsePayload;
      } finally {
        response.close();
      }
    } finally {
      httpClient.close();
    }
  }
}