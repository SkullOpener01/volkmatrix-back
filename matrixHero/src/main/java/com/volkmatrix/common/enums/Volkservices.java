package com.volkmatrix.common.enums;

public enum Volkservices {
  SMS_SERVICE("SMS"), VOICE_SERVICE("VOICE"), WHATSAPP_SERVICE("WHATSAPP"), EMAIL_SERVICE("EMAIL");

  public final String value;

  private Volkservices(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;

  }
}
