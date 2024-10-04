package com.volkmatrix.whatsapp.service.service;

import com.volkmatrix.common.enums.TimeZone;
import com.volkmatrix.common.utils.WapDateTimeUtils;
import com.volkmatrix.whatsapp.service.dto.WapMessageSentReposne;
import com.volkmatrix.whatsapp.service.model.whatsapp.UserWhatsappMessages;
import com.volkmatrix.whatsapp.service.repo.UserWapMsgRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizzMsgServiceImpl implements BizzMsgService {

  private static final Logger log = LoggerFactory.getLogger(BizzMsgServiceImpl.class);
  @Autowired
  private UserWapMsgRepo userWapMsgRepo;

  @Override
  public void createBusinessMsg(WapMessageSentReposne messageSentReposne, UserWhatsappMessages userWhatsappMessages) {

    userWhatsappMessages.setUserMobile(messageSentReposne.getContacts().get(0).getWa_id());
    userWhatsappMessages.setResponseMsgId(messageSentReposne.getMessages().get(0).getId());
    userWhatsappMessages.setAcceptedStatusFlg(true);
    userWhatsappMessages.setCurrentMsgStatus(messageSentReposne.getMessages().get(0).getMessage_status());
    userWhatsappMessages.setAcceptedTime(WapDateTimeUtils.getCurrentEpochDateTime(TimeZone.GMT.getValue()));

    userWapMsgRepo.save(userWhatsappMessages);
    log.info("after saving business msg to userMessages");



  }
}
