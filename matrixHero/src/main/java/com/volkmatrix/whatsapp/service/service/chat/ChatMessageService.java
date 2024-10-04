package com.volkmatrix.whatsapp.service.service.chat;


import com.volkmatrix.common.model.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface ChatMessageService {

  public ResponseEntity<BaseResponse> fetchUniqueUsers(String phoneNumberId);

  public ResponseEntity<BaseResponse> fetchAllChatByUser(String phoneNumberId, String userMobile);
}
