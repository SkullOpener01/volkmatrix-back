package com.volkmatrix.website.service.dto;

import lombok.Data;

@Data
public class FetchDemoReqDto {
  private Long id;
  private String byWhom;
  private boolean isContacted;
  private boolean isProposalSent;
}
