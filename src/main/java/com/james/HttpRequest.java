package com.james;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequest {

  private static final String SCHEME = "https";
  private static final String HOST = "api.spoonacular.com";
  private static final String PATH = "/recipes/%s";
  private static final String API_KEY = "8a95d4a356be44c1abe7f1d2362f2d79";

  CloseableHttpClient httpClient = HttpClients.createDefault();
  URIBuilder builder = new URIBuilder();

  public String makeRequest(URI request) throws IOException, URISyntaxException {
    HttpGet httpGet = new HttpGet(request);
    System.out.println("Executing request " + httpGet.getRequestLine());
    CloseableHttpResponse response = httpClient.execute(httpGet);
    try {
      // int responseCode = response.getStatusLine().getStatusCode();
      HttpEntity entity = response.getEntity();
      String responsePayload = EntityUtils.toString(entity);
      httpGet.releaseConnection();
      return responsePayload;
    } finally {
      response.close();
    }
  }

  public URI buildRequest(String subPath, String paramName, String param) throws URISyntaxException {
    URI uri = new URIBuilder().setScheme(SCHEME).setHost(HOST).setPath(String.format(PATH, subPath))
        .setParameter(paramName, param).setParameter("apiKey", API_KEY).build();
    return uri;
  }
}