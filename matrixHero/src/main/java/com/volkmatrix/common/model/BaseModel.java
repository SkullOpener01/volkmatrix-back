package com.volkmatrix.common.model;


import com.volkmatrix.common.enums.TimeZone;
import com.volkmatrix.common.utils.WapDateTimeUtils;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BaseModel {

  public Long tmstmp = WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue());
  public String crTmUtc = WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.GMT.getValue());
  public String crTm = WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.IST.getValue());
  public Long tmstmpIst = WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue()) + 19800000l;
  public String upTmDsply = WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.GMT.getValue());
  public Long upTm = WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue());
  private LocalDateTime isoDateTime = LocalDateTime.now();
  private LocalDateTime isoUpTm = LocalDateTime.now();
  private LocalDate isoDate = LocalDate.now();


}
