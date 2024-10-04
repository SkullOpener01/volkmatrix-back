package com.volkmatrix.whatsapp.service.service;

import com.volkmatrix.whatsapp.service.dto.WapMessageSentReposne;
import com.volkmatrix.whatsapp.service.model.whatsapp.UserWhatsappMessages;

public interface BizzMsgService {

  void createBusinessMsg(WapMessageSentReposne messageSentReposne, UserWhatsappMessages userWhatsappMessages);
}
