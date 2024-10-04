package com.volkmatrix.whatsapp.controller;

import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.whatsapp.service.service.chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/chat")
public class ChatController {

  @Autowired
  ChatMessageService chatMessageService;

  @GetMapping("/fetch/users")
  public ResponseEntity<BaseResponse> fetchAllUniqueUsers(@RequestParam String phoneNumberId) {
    return chatMessageService.fetchUniqueUsers(phoneNumberId);
  }

  @GetMapping("/fetch/users/chat")
  public ResponseEntity<BaseResponse> fetchFullUsersChat(@RequestParam String phoneNumberId,
                                                         @RequestParam String userMobile) {
    return chatMessageService.fetchAllChatByUser(phoneNumberId, userMobile);

  }
}
