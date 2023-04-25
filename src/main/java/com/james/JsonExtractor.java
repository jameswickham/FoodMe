package com.james;

import java.util.ArrayList;
import com.jayway.jsonpath.JsonPath;

public class JsonExtractor {

  public static ArrayList<?> extractJsonData(String responsePayload, String path) {
    return JsonPath.read(responsePayload, path);
  }
}
