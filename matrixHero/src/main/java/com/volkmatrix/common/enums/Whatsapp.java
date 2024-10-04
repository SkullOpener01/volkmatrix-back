package com.volkmatrix.common.enums;

public enum Whatsapp {

  // message by
BUSINESS("business"),USER("user"),
  // Media
  IMAGE("image"),AUDIO("audio"),VIDEO("video"),DOCUMENT("document"),

  // Message template type
MARKETING("marketing"),UTILITY("utility"),AUTHENTICATION("authentication"),SERVICE("service");
  ;


  private final String value ;

  Whatsapp(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
