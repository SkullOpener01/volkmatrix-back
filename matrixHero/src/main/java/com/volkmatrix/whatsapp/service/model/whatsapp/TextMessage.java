package com.volkmatrix.whatsapp.service.model.whatsapp;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

// Class for Text messages
@Entity
@DiscriminatorValue("TEXT")
@Data
public class TextMessage extends UserWhatsappMessages {
  private String msgText; // The text of the message
}