package com.volkmatrix.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkmatrix.common.exception.ApiServiceException;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ApplicationUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtils.class);

  public ApplicationUtils() {
  }

  public static String uploadMultipartFileCommon(MultipartFile mfile, Environment env, String dir, String name) {
    StringBuilder stringB = new StringBuilder();

    try {
      LOGGER.info("inside upload multipart file ::: ");
      System.out.println("file size.." + mfile.getSize());
      String ext = FilenameUtils.getExtension(mfile.getOriginalFilename());
      String urlToDownld = env.getProperty("file") + dir + name + "." + ext;
      String uplddir = env.getProperty("DCMNT_UPLOAD_PATH") + env.getProperty("file") + dir + name + "." + ext;
      Path pth = Paths.get(uplddir);
      String var10002 = env.getProperty("DCMNT_UPLOAD_PATH");
      File file = new File(var10002 + env.getProperty("file") + dir + name + "." + ext);
      if (file.exists()) {
        file.delete();
      }

      stringB.append(mfile.getOriginalFilename());

      try {
        Files.write(pth, mfile.getBytes(), new OpenOption[0]);
        if (file != null) {
          LOGGER.info("before upload single File method ::");
          UploadMultiPart.uploadSingleFile(file, uplddir, env);
          LOGGER.info("After upload single File method ::");
        }
      } catch (IOException var11) {
        IOException e = var11;
        e.printStackTrace();
      }

      return urlToDownld;
    } catch (Exception var12) {
      Exception e = var12;
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static int getGenerateOtp() {
    Random random = new Random();
    return random.ints(100002, 999999).findFirst().getAsInt();
  }

  public static String generateUserNameAndPass(String emailId, long id) {
    try {
      String name = emailId.substring(0, 4);
      String SALTCHARS = "FNBT" + name + id;
      SALTCHARS = SALTCHARS.replace(" ", "");
      StringBuilder salt = new StringBuilder();
      Random rnd = new Random();

      while(salt.length() < 8) {
        int index = (int)(rnd.nextFloat() * (float)SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
      }

      String saltStr = salt.toString();
      LOGGER.info("" + saltStr);
      return saltStr;
    } catch (Exception var8) {
      Exception e = var8;
      LOGGER.error(e.getMessage());
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static double getDoubleValue(double value) {
    String amt;
    DecimalFormat df;
    if (value % 1.0 == 0.0) {
      df = new DecimalFormat("##.00");
      amt = df.format(value);
    } else {
      df = new DecimalFormat("0.00");
      amt = df.format(value);
    }

    return Double.parseDouble(amt);
  }

/*  public static Loc getLocDetail(String lat, String lng) {
    String finalRes = "";
    Loc loc = new Loc();

    try {
      LOGGER.info("Fetching GeoCity");
      URL oracle = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyAofpKPqFYZFQ4tfh3pHmxPYGt9jgsB828");
      URLConnection yc = oracle.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

      String inputLine;
      String jsonString;
      for(jsonString = ""; (inputLine = in.readLine()) != null; jsonString = jsonString + inputLine) {
      }

      JSONParser p = new JSONParser();
      JSONObject json = (JSONObject)p.parse(jsonString);
      JSONArray array = (JSONArray)json.get("results");
      String result = array.get(0).toString();
      JSONObject json1 = (JSONObject)p.parse(result);
      JSONArray adrsComponent = (JSONArray)json1.get("address_components");
      loc.setPlace((String)json1.get("formatted_address"));

      for(int i = 0; i < adrsComponent.size(); ++i) {
        String result2 = adrsComponent.get(i).toString();
        JSONObject json2 = (JSONObject)p.parse(result2);
        String address_type = json2.get("types").toString();
        address_type = address_type.replace("[", "");
        address_type = address_type.replace("]", "");
        address_type = address_type.replace("\"", "");
        String[] types = address_type.split(",");
        List<String> fixedLenghtList = Arrays.asList(types);
        if (fixedLenghtList.contains("locality") && fixedLenghtList.contains("political")) {
          finalRes = json2.get("long_name").toString();
          loc.setCity(finalRes);
        } else if (fixedLenghtList.contains("administrative_area_level_2") && fixedLenghtList.contains("political")) {
          finalRes = json2.get("long_name").toString();
          loc.setCity(finalRes);
        }

        if (fixedLenghtList.contains("country") && fixedLenghtList.contains("political")) {
          loc.setCountry(json2.get("long_name").toString());
        }

        if (fixedLenghtList.contains("administrative_area_level_1") && fixedLenghtList.contains("political")) {
          loc.setState(json2.get("long_name").toString());
        }

        if (fixedLenghtList.contains("postal_code")) {
          loc.setPincode(json2.get("long_name").toString());
        }
      }

      loc.setCountryFlag(getFlag(loc.getCountry()));
    } catch (Exception var21) {
      Exception e = var21;
      LOGGER.info("Error" + e.getMessage());
    }

    return loc;
  }*/

  public static String getFlag(String country) {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://restcountries.com/v3/name/" + country + "?fullText=true";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, new Object[0]);
    String json = (String)response.getBody();
    String flag = null;
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode root = objectMapper.readTree(json);
      JsonNode flags = root.get(0).path("flags");
      if (flags.isArray() && flags.size() > 0) {
        flag = flags.get(0).asText();
      }
    } catch (IOException var9) {
      IOException e = var9;
      e.printStackTrace();
    }

    return flag;
  }

  public static boolean checkFileTyp(MultipartFile file) {
    boolean flag = true;
    if (file != null && !file.isEmpty()) {
      String type = file.getContentType();
      if (type.contains(".")) {
        String[] starr = file.getContentType().split("\\.");
        type = starr[1];
      }

      if (!"ms-excel".equalsIgnoreCase(type) && !"text/csv".equalsIgnoreCase(type) && !"openxmlformats-officedocument".equalsIgnoreCase(type)) {
        flag = false;
      } else {
        flag = true;
      }
    }

    return flag;
  }



  public static int varCount(String msg) {
    try {
      int i = 0;
      Pattern p = Pattern.compile("\\{#var#}");

      for(Matcher m = p.matcher(msg); m.find(); ++i) {
      }

      return i;
    } catch (Exception var4) {
      Exception e = var4;
      LOGGER.error(e.getMessage());
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static String changeSmsContent(String[] records, int varCount, String text) {
    String smsText = "";
    smsText = getTempSms(records, varCount, text);
    smsText = smsText.replaceAll("\r", "");
    smsText = smsText.replaceAll("“", "\"");
    smsText = smsText.replaceAll("”", "\"");
    smsText = smsText.replaceAll("‘", "'");
    smsText = smsText.replaceAll("’", "'");
    return smsText;
  }

  public static String getTempSms(String[] line, int i, String msg) {
    try {
      for(int j = 1; j <= i; ++j) {
        if (line[j] != null) {
          String value = line[j];
          String squareBoxChar = String.valueOf('�');
          value = value.replace(squareBoxChar, "'");
          msg = msg.replaceFirst("\\{#var#}", value);
        }
      }

      return msg;
    } catch (Exception var6) {
      Exception e = var6;
      LOGGER.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public static String generateRandomNumOnLength(int otpLength) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < otpLength; ++i) {
      sb.append(random.nextInt(10));
    }

    String otp = sb.toString();
    return otp;
  }

  public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper) {
    return (List)source.stream().map((element) -> {
      return modelMapper.map(element, targetClass);
    }).collect(Collectors.toList());
  }
}
