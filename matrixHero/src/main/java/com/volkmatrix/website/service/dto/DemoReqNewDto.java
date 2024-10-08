package com.volkmatrix.website.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DemoReqNewDto {

//  private Long id;
  private String name;
  private String email;
  private String mobile;
  private String message;
  private String demoTime;
  private String demoDate;
  private boolean wapNotification;    // this is for getting opt-in for whatsapp notification
  private boolean connectExpert;  // this is by default true, if user wants our agent to connect with them
  private boolean keepInformed;   // send mail to customers about products and new offers
  private boolean botPenguinReq = false;  // it's true when request is generated from botpenguin chatbot else it must
  // be false

}
