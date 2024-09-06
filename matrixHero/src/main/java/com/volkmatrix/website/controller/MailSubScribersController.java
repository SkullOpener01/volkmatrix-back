package com.volkmatrix.website.controller;

import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.website.service.service.subscribers.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/subscriber")
public class MailSubScribersController {

  @Autowired
  private SubscriberService subscriberService;

  @GetMapping("/add")
  public ResponseEntity<BaseResponse> subscribersAdd(@RequestParam String email){
   return subscriberService.addSubscriber(email);
  }
}
