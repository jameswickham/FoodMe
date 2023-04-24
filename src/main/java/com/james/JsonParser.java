package com.james;

import java.util.ArrayList;
import com.jayway.jsonpath.JsonPath;

public class JsonParser {

  private String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ArrayList<Integer> extractId(String responsePayload) {
    ArrayList<Integer> idList = JsonPath.read(responsePayload, getPath());

    return idList;
  }

  public ArrayList<String> extractUrls(String responsePayload) {
    ArrayList<String> urlList = JsonPath.read(responsePayload, getPath());

    return urlList;
  }
}
