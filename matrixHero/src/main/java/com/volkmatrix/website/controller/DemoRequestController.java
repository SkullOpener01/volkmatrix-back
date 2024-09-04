package com.volkmatrix.website.controller;

import com.volkmatrix.common.CommonConstants;
import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.common.model.DataResponse;
import com.volkmatrix.common.model.DatalistResponse;
import com.volkmatrix.website.service.service.DemoReqService;
import com.volkmatrix.website.service.dto.DemoReqNewDto;
import com.volkmatrix.website.service.dto.DemoReqUpdateDtp;
import com.volkmatrix.website.service.dto.FetchDemoReqDto;
import com.volkmatrix.website.service.model.DemoRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/web")
public class DemoRequestController {

  private static final Logger log = LoggerFactory.getLogger(DemoRequestController.class);
  @Autowired
  private DemoReqService demoReqService;

  @GetMapping("/test")
  public String test(){
    return "Demo req controller Working";
  }

  @PostMapping("/demo/req")
  public ResponseEntity<BaseResponse> createDemoRequest(@RequestBody DemoReqNewDto demoReqNewDto) {
    log.info("Create Demo Request {}", demoReqNewDto);
    return demoReqService.createDemoRequest(demoReqNewDto);
  }

  @PostMapping("/demo/req/update")
  public ResponseEntity<BaseResponse> createDemoRequest(@RequestBody DemoReqUpdateDtp reqUpdateDtp) {
    log.info("update req {}",reqUpdateDtp);
    return demoReqService.updateDemoRequest(reqUpdateDtp);
  }

  @GetMapping("/delete/id/{id}")
  public ResponseEntity<BaseResponse> deleteDemoRequest(@PathVariable  Long id) {
    log.info("Delete Demo Request {}", id);
    return demoReqService.deleteDemoRequest(id);
  }

  @GetMapping("/fetch/id/{id}")
  public ResponseEntity<DataResponse> fetchDemoReqById(@PathVariable  Long id) {
    log.info("Fetch Demo Request {}", id);
    return demoReqService.getDemoRequest(id);
  }

  @GetMapping("/fetch/all")
  public ResponseEntity<DatalistResponse> fetchDemoReqById() {;
    DatalistResponse response = new DatalistResponse();
    List<DemoRequests> allDemoRequests = demoReqService.getAllDemoRequests();
    response.setDatalist(allDemoRequests);
    response.setMessage(CommonConstants.DATA_DELETED_SUCCESSFULLY);

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PostMapping("/fetch/custom.data")
  public ResponseEntity<DatalistResponse> fetchDemoReqByData(@RequestBody FetchDemoReqDto demoReqDto) {
    DatalistResponse response = new DatalistResponse();
    List<DemoRequests> allDemoRequests = demoReqService.getAllDemoRequestsBy(demoReqDto);
    response.setDatalist(allDemoRequests);
    response.setMessage(CommonConstants.DATA_DELETED_SUCCESSFULLY);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
