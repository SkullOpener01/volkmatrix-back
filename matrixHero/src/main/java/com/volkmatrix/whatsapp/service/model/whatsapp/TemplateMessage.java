package com.volkmatrix.whatsapp.service.model.whatsapp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("TEMPLATE")
@Data
public class TemplateMessage extends UserWhatsappMessages {

  private String messageTempType;
  private String templateName;
  private String templateText;
  private String finalTempText;
}
