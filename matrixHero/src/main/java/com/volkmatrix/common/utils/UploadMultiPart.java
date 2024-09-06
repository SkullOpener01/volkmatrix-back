package com.volkmatrix.common.utils;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.volkmatrix.common.exception.ApiServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UploadMultiPart {
  private static final Logger LOGGER= LoggerFactory.getLogger(UploadMultiPart.class);


  public static void uploadSingleFile(File file, String path, Environment env) {
    try {

      LOGGER.info("inside upload single file ::: ");
      HttpHeaders headers = new HttpHeaders();


      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      MultiValueMap<String, Object> body
          = new LinkedMultiValueMap<>();
      LOGGER.info("Uploading File to CDN");



      body.add("file", new FileSystemResource(file));
      body.add("path",path);

//			HttpEntity<MultiValueMap<String, Object>> requestEntity
//			 = new HttpEntity<>(body, createHeaders("masteruser","00Vj4242411"));
      HttpEntity<MultiValueMap<String, Object>> requestEntity
          = new HttpEntity<>(body);
      String serverUrl = env.getProperty("CDN_URL");
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate
          .postForEntity(serverUrl, requestEntity, String.class);
      LOGGER.info("Uploading File to CDN:::"+response);
    }catch(Exception e) {
      throw new ApiServiceException(e.getMessage());
    }
  }
}
