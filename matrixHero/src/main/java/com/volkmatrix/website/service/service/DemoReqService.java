package com.volkmatrix.website.service.service;


import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.common.model.DataResponse;
import com.volkmatrix.website.service.dto.DemoReqNewDto;
import com.volkmatrix.website.service.dto.DemoReqUpdateDtp;
import com.volkmatrix.website.service.dto.FetchDemoReqDto;
import com.volkmatrix.website.service.model.DemoRequests;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DemoReqService {

  ResponseEntity<BaseResponse> createDemoRequest(@Valid DemoReqNewDto requestDto);

  ResponseEntity<BaseResponse> updateDemoRequest(DemoReqUpdateDtp request);

  ResponseEntity<BaseResponse> deleteDemoRequest(Long id);

  ResponseEntity<DataResponse> getDemoRequest(Long id);

  List<DemoRequests> getAllDemoRequests();

  List<DemoRequests> getAllDemoRequestsBy(FetchDemoReqDto fetchDemoReqDto);

  ResponseEntity<BaseResponse> sendEmailToDemoRequester(String email);
}
