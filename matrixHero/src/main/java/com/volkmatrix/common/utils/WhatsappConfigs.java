package com.volkmatrix.common.utils;

import com.volkmatrix.common.enums.Whatsapp;
import com.volkmatrix.whatsapp.service.dto.WapMediaFechRes;
import com.volkmatrix.whatsapp.service.dto.WapMessageSentReposne;
import com.volkmatrix.whatsapp.service.model.whatsapp.MediaMessage;
import com.volkmatrix.whatsapp.service.model.whatsapp.TemplateMessage;
import com.volkmatrix.whatsapp.service.model.whatsapp.UserWhatsappMessages;
import com.volkmatrix.whatsapp.service.service.BizzMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WhatsappConfigs {

  private static final String WHATSAPP_API_URL = "https://graph.facebook.com/v20.0/397649540105559/messages";
  private static final String AUTHORIZATION_HEADER = "Bearer EAGHfjF9KLjcBO5nkhPMg221zZB12g9CyzbTw3IFYJCQb51V8Lg4RvZA3KsIZA7xkCipp34gAAK8C2iqAZB199F60TXDrKLFNO4JYdAv4f46v2vNZCKlrKNy2TuCaPXZCSL8sTEJ5KZBSws0FkL0uk1ZBgUu9Kyqm9CUZB8vNJuYZCx4AZAn6SAi7ZCFp81BCbUYIWTWniAZDZD"; // Keep this secure!

  private final RestTemplate restTemplate;

  public WhatsappConfigs(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Autowired
  BizzMsgService bizzMsgService;

  public void sendDemoToBizz(String recipient, String name, String mobile, String email, String dateTime) {
    String requestBody = String.format("{\n" +
        "    \"messaging_product\": \"whatsapp\",\n" +
        "    \"recipient_type\": \"individual\",\n" +
        "    \"to\": \"%s\",\n" +
        "    \"type\": \"template\",\n" +
        "    \"template\": {\n" +
        "        \"name\": \"demo_sch_biz\",\n" +
        "        \"language\": {\n" +
        "            \"code\": \"en\"\n" +
        "        },\n" +
        "        \"components\": [\n" +
        "            {\n" +
        "                \"type\": \"body\",\n" +
        "                \"parameters\": [\n" +
        "                    {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"text\": \"%s\"\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"text\": \"%s\"\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"text\": \"%s\"\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"text\": \"%s\"\n" +
        "                    }\n" +
        "                ]\n" +
        "            }\n" +
        "        ]\n" +
        "    }\n" +
        "}", recipient, name, mobile, email, dateTime);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", AUTHORIZATION_HEADER);

    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    UserWhatsappMessages userWhatsappMessages = new TemplateMessage();
    userWhatsappMessages.setPhone_number_id("397649540105559");
    userWhatsappMessages.setMessageBy(Whatsapp.BUSINESS.getValue());
    userWhatsappMessages.setUserMobile(mobile);
    ((TemplateMessage) userWhatsappMessages).setMessageTempType(Whatsapp.MARKETING.getValue());
    ((TemplateMessage) userWhatsappMessages).setTemplateName("demo_sch_biz");



    ResponseEntity<WapMessageSentReposne> response = restTemplate.postForEntity(WHATSAPP_API_URL, entity, WapMessageSentReposne.class);


    bizzMsgService.createBusinessMsg(response.getBody(),userWhatsappMessages);

    if (response.getStatusCode().is2xxSuccessful()) {
      System.out.println("Message sent successfully: " + response.getBody());
    } else {
      System.out.println("Failed to send message: " + response.getStatusCode());
    }
  }




  public void sendDemoToCustomer(String recipient, String name,  String dateTime) {
    String requestBody = String.format("{\n" +
        "    \"messaging_product\": \"whatsapp\",\n" +
        "    \"recipient_type\": \"individual\",\n" +
        "    \"to\": \"%s\",\n" +
        "    \"type\": \"template\",\n" +
        "    \"template\": {\n" +
        "        \"name\": \"demo_cust_sch\",\n" +
        "        \"language\": {\n" +
        "            \"code\": \"en\"\n" +
        "        },\n" +
        "        \"components\": [\n" +
        "            {\n" +
        "                \"type\": \"body\",\n" +
        "                \"parameters\": [\n" +
        "                    {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"text\": \"%s\"\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"text\": \"%s\"\n" +
        "                    },\n" +
        "                ]\n" +
        "            }\n" +
        "        ]\n" +
        "    }\n" +
        "}", recipient, name,  dateTime);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", AUTHORIZATION_HEADER);

    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    UserWhatsappMessages userWhatsappMessages = new TemplateMessage();
    userWhatsappMessages.setPhone_number_id("397649540105559");
    userWhatsappMessages.setMessageBy(Whatsapp.BUSINESS.getValue());
    userWhatsappMessages.setUserMobile(recipient);
    ((TemplateMessage) userWhatsappMessages).setMessageTempType(Whatsapp.MARKETING.getValue());
    ((TemplateMessage) userWhatsappMessages).setTemplateName("demo_sch_biz");

//    ResponseEntity<String> response = restTemplate.postForEntity(WHATSAPP_API_URL, entity, String.class);
    ResponseEntity<WapMessageSentReposne> response = restTemplate.postForEntity(WHATSAPP_API_URL, entity, WapMessageSentReposne.class);
    bizzMsgService.createBusinessMsg(response.getBody(),userWhatsappMessages);

    if (response.getStatusCode().is2xxSuccessful()) {
      System.out.println("Message sent successfully: " + response.getBody());
    } else {
      System.out.println("Failed to send message: " + response.getStatusCode());
    }
  }

  public WapMediaFechRes getMediaById(String mediaId) {
    String url = String.format("https://graph.facebook.com/v20.0/%s?phone_number_id=%s", mediaId, "397649540105559");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", AUTHORIZATION_HEADER);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<WapMediaFechRes> response = restTemplate.exchange(url, HttpMethod.GET, entity, WapMediaFechRes.class);

    return response.getBody();
  }

}