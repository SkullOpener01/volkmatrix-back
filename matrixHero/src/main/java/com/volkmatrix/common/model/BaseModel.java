package com.volkmatrix.common.model;


import com.volkmatrix.common.enums.TimeZone;
import com.volkmatrix.common.utils.WapDateTimeUtils;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BaseModel {

  @Column(name = "tmstmp")
  public Long tmstmp = WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue());
  @Column(name = "crTmUtc")
  public String crTmUtc = WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.GMT.getValue());
  @Column(name = "crTm")
  public String crTm = WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.IST.getValue());
  @Column(name = "tmstmpIst")
  public Long tmstmpIst = WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue()) + 19800000l;
  @Column(name = "upTmDsply")
  public String upTmDsply = WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.GMT.getValue());
  @Column(name = "upTm")
  public Long upTm = WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue());
  @Column(name = "isoDateTime")
  private LocalDateTime isoDateTime = LocalDateTime.now();
  @Column(name = "isoUpTm")
  private LocalDateTime isoUpTm = LocalDateTime.now();
  @Column(name = "isoDate")
  private LocalDate isoDate = LocalDate.now();


}
