package com.volkmatrix.whatsapp.service.service.chat;

import com.volkmatrix.common.CommonConstants;
import com.volkmatrix.common.exception.ApiServiceException;
import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.common.model.DatalistResponse;
import com.volkmatrix.whatsapp.service.model.whatsapp.UserWhatsappMessages;
import com.volkmatrix.whatsapp.service.repo.UserWapMsgRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

  private static final Logger log = LoggerFactory.getLogger(ChatMessageServiceImpl.class);
  @Autowired
  private UserWapMsgRepo userWapMsgRepo;


  @Override
  public ResponseEntity<BaseResponse> fetchUniqueUsers(String phoneNumberId) {
    try {
      log.info("Fetching users from business phone number {}", phoneNumberId);

      DatalistResponse response = new DatalistResponse();
     List<String> users =  userWapMsgRepo.findDistinctUsersOfBusiness(phoneNumberId);
     log.info("Found {} users", users.size());

     response.setMessage(CommonConstants.DATA_FETCHED_SUCCESSFULLY);
     response.setDatalist(users);

     return ResponseEntity.status(HttpStatus.OK).body(response);


    }catch (Exception e) {
      e.printStackTrace();
      throw new ApiServiceException(e.getMessage());
    }
  }

  @Override
  public ResponseEntity<BaseResponse> fetchAllChatByUser(String phoneNumberId, String userMobile) {
    try {
      log.info("Fetching users chat from business number {} And user Mobile {}", phoneNumberId, userMobile);
      DatalistResponse response = new DatalistResponse();

     List<UserWhatsappMessages> userChat =  userWapMsgRepo.fetchUserChatMessage(userMobile,phoneNumberId);
     log.info("after fetching users chat, chat size: {}", userChat.size());
     response.setMessage(CommonConstants.DATA_FETCHED_SUCCESSFULLY);
//      Collections.reverse(userChat);
     response.setDatalist(userChat);
     return ResponseEntity.status(HttpStatus.OK).body(response);

    }catch (Exception e) {
      e.printStackTrace();
      throw new ApiServiceException(e.getMessage());
    }
  }
}
