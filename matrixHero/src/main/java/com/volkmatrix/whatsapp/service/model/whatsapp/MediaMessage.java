package com.volkmatrix.whatsapp.service.model.whatsapp;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("MEDIA")
@Data
public class MediaMessage extends UserWhatsappMessages {
  private boolean mediaMsg = false; // true if message is image, video, audio, etc.

  private String mediaType; // e.g., image, video, audio, document

  private String mediaFileUrl; // URL of the media file
  private String caption;
  private String mimeType;
  private String sha256;
  private String mediaFileId;
  private String filename;
  private boolean voice = false;
}
