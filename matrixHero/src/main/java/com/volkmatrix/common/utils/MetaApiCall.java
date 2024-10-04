package com.volkmatrix.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Map;

public class MetaApiCall {


  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String args[]) {


  }


  public static <T> T apiCall(String url, String method, String authorizationHeader, String requestBodyJson, Map<String, String> requestParams, Map<String, String> pathVariables, Class<T> responseType) {
    try {
      HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
      String apikey = "Bearer " + authorizationHeader;
      // Add request parameters to the URL
      if (requestParams != null && !requestParams.isEmpty()) {
        StringBuilder queryParams = new StringBuilder();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
          queryParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String queryString = queryParams.toString();
        url += "?" + queryString.substring(0, queryString.length() - 1);
      }
      // Add path variables to the URL
      if (pathVariables != null && !pathVariables.isEmpty()) {
        for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
          url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
      }
      requestBuilder.uri(URI.create(url))
          .header("Authorization", apikey)
          .header("Content-Type", "application/json");
      if (method.equalsIgnoreCase("GET")) {
        requestBuilder.GET();
      } else if (method.equalsIgnoreCase("POST")) {
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBodyJson));
      } else if (method.equalsIgnoreCase("PUT")) {
        requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestBodyJson));
      } else if (method.equalsIgnoreCase("DELETE")) {
        requestBuilder.DELETE();
      }
      HttpRequest request = requestBuilder.build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        return objectMapper.readValue(response.body(), responseType);
      } else {
        throw new RuntimeException("API call failed with status code: " + response.statusCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
