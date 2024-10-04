package com.volkmatrix.whatsapp.service.model.whatsapp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("REACTION")
@Data
public class ReactionMessage extends UserWhatsappMessages {
  private String reactedOnMsgId; // responseMsgId of message on which reaction is given
  private String emoji; // reaction emoji
}