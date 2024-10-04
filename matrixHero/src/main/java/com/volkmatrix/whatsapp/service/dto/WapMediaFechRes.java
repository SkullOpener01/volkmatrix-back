package com.volkmatrix.whatsapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WapMediaFechRes {
  private String url;
  @JsonProperty("mime_type")
  private String mimeType;
  private String sha256;
  @JsonProperty("file_size")
  private long fileSize; // Using long to accommodate larger file sizes
  private String id;
  @JsonProperty("messaging_product")
  private String messagingProduct;
}
