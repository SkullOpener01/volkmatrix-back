package com.volkmatrix.website.service.service.subscribers;

import com.volkmatrix.common.CommonConstants;
import com.volkmatrix.common.StatusCodes;
import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.common.utils.EmailConfiguration;
import com.volkmatrix.common.utils.EmailTemplates;
import com.volkmatrix.website.service.model.VolkMailSubscribers;
import com.volkmatrix.website.service.repo.VolkSubscribersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private static final Logger log = LoggerFactory.getLogger(SubscriberServiceImpl.class);

    @Autowired
    private VolkSubscribersRepo subscribersRepo;

    @Autowired
    private EmailConfiguration emailConfiguration;

  @Override
  public ResponseEntity<BaseResponse> addSubscriber(String email) {
    BaseResponse response = new BaseResponse();

    log.info("checking subscriber with email: {}", email.toLowerCase());

    VolkMailSubscribers byEmail = subscribersRepo.findByEmailAndActiveFlgTrue(email.toLowerCase());
    if (byEmail != null) {
      response.setMessage(CommonConstants.ErrorMessages.ALREADY_SUBSCRIBED);
      response.setStatusCode(StatusCodes.REQUEST_FAILURE);
    }else {
      VolkMailSubscribers subscriber = new VolkMailSubscribers();
      subscriber.setEmail(email.toLowerCase());
      subscriber.setActiveFlg(true);
      subscribersRepo.save(subscriber);

      String welcomeMailSubscriber = EmailTemplates.WELCOME_MAIL_SUBSCRIBER_NEW;
      String[] recipients = {email.toLowerCase()};
      new Thread(() -> {
        log.info("sending message to email subscriber :: {} ",recipients[0]);
        emailConfiguration.sendAttchMimeEmail(recipients, null,"Thanks for Subscribing! Welcome to the Volkmatrix " +
            "Community",welcomeMailSubscriber);

      }).start();

      response.setMessage(CommonConstants.MAIL_SUBSCRIBER_ADDED);
      response.setStatusCode(StatusCodes.REQUEST_SUCCESS);
    }
    return ResponseEntity.status(HttpStatus.OK).body(response);

  }
}
