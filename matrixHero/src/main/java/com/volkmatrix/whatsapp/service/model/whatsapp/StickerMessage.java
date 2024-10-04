package com.volkmatrix.whatsapp.service.model.whatsapp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("STICKER")
@Data
public class StickerMessage extends UserWhatsappMessages {
  private String stickerId; // ID of the sticker
  private String stickerType; // Type of the sticker (e.g., animated, static)
}
