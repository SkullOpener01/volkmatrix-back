package com.volkmatrix.common.model;

import lombok.Data;

@Data
public class BaseResponse {

  private String message;
  private int statusCode = 200;

}
