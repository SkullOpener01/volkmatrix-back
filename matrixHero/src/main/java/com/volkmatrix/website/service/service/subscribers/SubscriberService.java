package com.volkmatrix.website.service.service.subscribers;

import com.volkmatrix.common.model.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface SubscriberService {

  ResponseEntity<BaseResponse> addSubscriber(String email);
}
