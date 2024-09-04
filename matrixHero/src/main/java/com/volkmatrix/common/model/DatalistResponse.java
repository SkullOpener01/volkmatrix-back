package com.volkmatrix.common.model;

import lombok.Data;

import java.util.List;

@Data
public class DatalistResponse extends BaseResponse {

  private List<?> datalist;

}
