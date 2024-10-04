package com.volkmatrix.whatsapp.service.service;

import com.volkmatrix.common.enums.TimeZone;
import com.volkmatrix.common.enums.Whatsapp;
import com.volkmatrix.common.utils.WapDateTimeUtils;
import com.volkmatrix.common.utils.WhatsappConfigs;
import com.volkmatrix.whatsapp.service.dto.WapMediaFechRes;
import com.volkmatrix.whatsapp.service.dto.WapWebhookPayload;
import com.volkmatrix.whatsapp.service.model.whatsapp.MediaMessage;
import com.volkmatrix.whatsapp.service.model.whatsapp.ReactionMessage;
import com.volkmatrix.whatsapp.service.model.whatsapp.TextMessage;
import com.volkmatrix.whatsapp.service.model.whatsapp.UserWhatsappMessages;
import com.volkmatrix.whatsapp.service.repo.UserWapMsgRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebhookServiceImpl implements WebhookService {

  private static final Logger log = LoggerFactory.getLogger(WebhookServiceImpl.class);

  @Autowired
  private UserWapMsgRepo userWapMsgRepo;

  @Autowired
  WhatsappConfigs whatsappConfigs;

/*  @Override
  public void manageWebhook(WapWebhookPayload payload) {
    log.info("Inside manageWebhook payload: {}", payload);

    List<WapWebhookPayload.Entry> entry = payload.getEntry();
    entry.forEach(entry1 -> {
      List<WapWebhookPayload.Change> changes = entry1.getChanges();
      changes.forEach(change1 -> {
        WapWebhookPayload.Value value = change1.getValue();

        // Checking Meta-data
        WapWebhookPayload.Metadata metadata = value.getMetadata();
        String displayPhoneNumber = metadata.getDisplay_phone_number();
        String phoneNumberId = metadata.getPhone_number_id();

        // Checking for status
        List<WapWebhookPayload.Status> statuses = value.getStatuses();
        if (statuses != null) {
          updateMsgStatus(statuses);
        }

        // Store new message from user
        List<WapWebhookPayload.Message> messages = value.getMessages();
        if (messages != null) {
          UserWhatsappMessages userWhatsappMessages = new UserWhatsappMessages();
          // Adding org details
          userWhatsappMessages.setPhone_number_id(phoneNumberId);
          userWhatsappMessages.setDisplay_phone_number(displayPhoneNumber);

          // Checking customer profile data
          List<WapWebhookPayload.Contact> contacts = value.getContacts();
          for (WapWebhookPayload.Contact contact : contacts) {
            userWhatsappMessages.setUserName(contact.getProfile().getName());
            userWhatsappMessages.setUserMobile(contact.getWa_id());
          }

          saveUserNewMessage(messages, userWhatsappMessages);
        }
      });
    });
  }

  private void saveUserNewMessage(List<WapWebhookPayload.Message> messages, UserWhatsappMessages userWhatsappMessages) {
    log.info("Inside saveUserNewMessage {}", userWhatsappMessages);
    for (WapWebhookPayload.Message message : messages) {
      userWhatsappMessages.setResponseMsgId(message.getId());
      userWhatsappMessages.setMessageBy(Whatsapp.USER.getValue());
      userWhatsappMessages.setUserMsgTimeEpoch(message.getTimestamp());

      // Convert timestamp to a readable format (if needed)
      userWhatsappMessages.setUserMsgTime(WapDateUtils.stringDateFromEpoch(message.getTimestamp(), "Asia/Calcutta"));

      String messageType = message.getType();

      switch (messageType) {
        case "text":
          userWhatsappMessages.setMessageType(messageType);
          userWhatsappMessages.setMsgText(message.getText().getBody());
          break; // Add break to avoid fall-through
        case "reaction":
          userWhatsappMessages.setMessageType(messageType);
          WapWebhookPayload.Reaction reaction = message.getReaction();
          userWhatsappMessages.setReactedOnMsgId(reaction.getMessage_id());
          userWhatsappMessages.setEmoji(reaction.getEmoji());
          break; // Add break to avoid fall-through
        case "image":
          userWhatsappMessages.setMessageType(messageType);
          WapWebhookPayload.Image image = message.getImage();
          // Assuming there might be a URL or other properties you want to set
          userWhatsappMessages.setMediaFileUrl(image.getUrl()); // Assuming getUrl() returns the media URL
          break; // Add break to avoid fall-through
        // Add cases for other message types as necessary
        default:
          log.warn("Unknown message type: {}", messageType);
          break; // Handle unknown message types
      }

      userWapMsgRepo.save(userWhatsappMessages); // Save each message
    }
  }

  // This method will update the status of messages
  private void updateMsgStatus(List<WapWebhookPayload.Status> statuses) {
    log.info("Inside updateMsgStatus");
    for (WapWebhookPayload.Status status : statuses) {
      String id = status.getId();
      String recipientId = status.getRecipient_id();
      String msgStatus = status.getStatus();
      long timestamp = status.getTimestamp();

      UserWhatsappMessages usrMsgs = userWapMsgRepo.findByResponseMsgIdAndUserMobile(id, recipientId);
      if (usrMsgs != null) {
        usrMsgs.setCurrentMsgStatus(msgStatus);

        // Update status flags based on the current message status
        switch (msgStatus) {
          case "accepted":
            usrMsgs.setAcceptedStatusFlg(true);
            usrMsgs.setAcceptedTime(timestamp);
            break; // Add break to avoid fall-through
          case "sent":
            usrMsgs.setSentStatusFlg(true);
            usrMsgs.setSentTime(timestamp);
            break; // Add break to avoid fall-through
          case "delivered":
            usrMsgs.setDeliveredStatusFlg(true);
            usrMsgs.setDeliveredTime(timestamp);
            break; // Add break to avoid fall-through
          case "read":
            usrMsgs.setReadStatusFlg(true);
            usrMsgs.setReadTime(timestamp);
            break; // Add break to avoid fall-through
          case "failed":
            usrMsgs.setErrorStatusFlg(true);
            usrMsgs.setErrorTime(timestamp);
            List<WapWebhookPayload.Errors> errors = status.getErrors();
            if (errors != null) {
              for (WapWebhookPayload.Errors error : errors) {
                usrMsgs.setErrorRemark(error.getTitle());
                usrMsgs.setErrorCode(error.getCode());
              }
            }
            break; // Add break to avoid fall-through
          default:
            log.warn("Unknown message status: {}", msgStatus);
            break; // Handle unknown statuses
        }

        userWapMsgRepo.save(usrMsgs);
        log.info("After update msg status: {}, for {}", usrMsgs, usrMsgs.getUserMobile());
      } else {
        log.warn("No user message found for ID: {} and Recipient: {}", id, recipientId);
      }
    }
  }*/

//  =======================================================

  @Override
  public void manageWebhook(WapWebhookPayload payload) {
    log.info("Inside manageWebhook payload: {}", payload);

    List<WapWebhookPayload.Entry> entry = payload.getEntry();
    entry.forEach(entry1 -> {
      List<WapWebhookPayload.Change> changes = entry1.getChanges();
      changes.forEach(change1 -> {
        WapWebhookPayload.Value value = change1.getValue();

        // Checking Meta-data
        WapWebhookPayload.Metadata metadata = value.getMetadata();
        String displayPhoneNumber = metadata.getDisplay_phone_number();
        String phoneNumberId = metadata.getPhone_number_id();

        // Checking for status
        List<WapWebhookPayload.Status> statuses = value.getStatuses();
        if (statuses != null) {
          updateMsgStatus(statuses);
        }

        // Store new message from user
        List<WapWebhookPayload.Message> messages = value.getMessages();
        if (messages != null) {
          for (WapWebhookPayload.Message message : messages) {
            UserWhatsappMessages userWhatsappMessage = createUserWhatsappMessage(message, phoneNumberId, displayPhoneNumber, value.getContacts());
            userWapMsgRepo.save(userWhatsappMessage); // Save each message
          }
        }
      });
    });
  }

  private UserWhatsappMessages createUserWhatsappMessage(WapWebhookPayload.Message message, String phoneNumberId, String displayPhoneNumber, List<WapWebhookPayload.Contact> contacts) {
    UserWhatsappMessages userWhatsappMessages = null;
    String messageType = message.getType();

    switch (messageType) {
      case "text":
        userWhatsappMessages = new TextMessage();
        ((TextMessage) userWhatsappMessages).setMsgText(message.getText().getBody());
        break;
      case "reaction":
        userWhatsappMessages = new ReactionMessage();
        WapWebhookPayload.Reaction reaction = message.getReaction();
        ((ReactionMessage) userWhatsappMessages).setReactedOnMsgId(reaction.getMessage_id());
        ((ReactionMessage) userWhatsappMessages).setEmoji(reaction.getEmoji());
        break;
          case "image":
            userWhatsappMessages = new MediaMessage();
            WapWebhookPayload.Image image = message.getImage();
            ((MediaMessage) userWhatsappMessages).setMediaType(Whatsapp.IMAGE.getValue()); // Set media type as needed
            ((MediaMessage) userWhatsappMessages).setCaption(image.getCaption());
            ((MediaMessage) userWhatsappMessages).setMimeType(image.getMime_type());
            ((MediaMessage) userWhatsappMessages).setSha256(image.getSha256());
            ((MediaMessage) userWhatsappMessages).setMediaFileId(image.getId());
            ((MediaMessage) userWhatsappMessages).setMediaMsg(true);
            WapMediaFechRes mediaById = whatsappConfigs.getMediaById(image.getId());
            ((MediaMessage) userWhatsappMessages).setMediaFileUrl(mediaById.getUrl());

            break;
            case "video":
              userWhatsappMessages = new MediaMessage();
              WapWebhookPayload.Video video = message.getVideo();
              ((MediaMessage) userWhatsappMessages).setMediaType(Whatsapp.VIDEO.getValue());
              ((MediaMessage) userWhatsappMessages).setMimeType(video.getMime_type());
              ((MediaMessage) userWhatsappMessages).setSha256(video.getSha256());
              ((MediaMessage) userWhatsappMessages).setMediaFileId(video.getId());
              ((MediaMessage) userWhatsappMessages).setMediaMsg(true);
              WapMediaFechRes videoMediaById = whatsappConfigs.getMediaById(video.getId());
              ((MediaMessage) userWhatsappMessages).setMediaFileUrl(videoMediaById.getUrl());
              break;
          case "audio":
            userWhatsappMessages = new MediaMessage();
            WapWebhookPayload.Audio audio = message.getAudio();
            ((MediaMessage) userWhatsappMessages).setMediaType(Whatsapp.AUDIO.getValue());
            ((MediaMessage) userWhatsappMessages).setVoice(true);
            ((MediaMessage) userWhatsappMessages).setMimeType(audio.getMime_type());
            ((MediaMessage) userWhatsappMessages).setSha256(audio.getSha256());
            ((MediaMessage) userWhatsappMessages).setMediaFileId(audio.getId());
            ((MediaMessage) userWhatsappMessages).setMediaMsg(true);
            WapMediaFechRes audioMediaById = whatsappConfigs.getMediaById(audio.getId());
            ((MediaMessage) userWhatsappMessages).setMediaFileUrl(audioMediaById.getUrl());
            break;
          case "document" :
            userWhatsappMessages = new MediaMessage();
            WapWebhookPayload.Document document = message.getDocument();
            ((MediaMessage) userWhatsappMessages).setMediaType(Whatsapp.DOCUMENT.getValue());
            ((MediaMessage) userWhatsappMessages).setMimeType(document.getMime_type());
            ((MediaMessage) userWhatsappMessages).setSha256(document.getSha256());
            ((MediaMessage) userWhatsappMessages).setMediaFileId(document.getId());
            ((MediaMessage) userWhatsappMessages).setMediaMsg(true);
            WapMediaFechRes docMediaById = whatsappConfigs.getMediaById(document.getId());
            ((MediaMessage) userWhatsappMessages).setMediaFileUrl(docMediaById.getUrl());
            break;
      default:
        log.warn("Unknown message type: {}", messageType);
        break;
    }


    if (userWhatsappMessages != null) {
      userWhatsappMessages.setPhone_number_id(phoneNumberId);
      userWhatsappMessages.setDisplay_phone_number(displayPhoneNumber);
      userWhatsappMessages.setResponseMsgId(message.getId());
      userWhatsappMessages.setMessageBy(Whatsapp.USER.getValue());
      userWhatsappMessages.setUserMsgTimeEpoch(message.getTimestamp());
      userWhatsappMessages.setUserMsgTime(WapDateTimeUtils.getCurrentEpochDateTimeStr(TimeZone.GMT.getValue()));

      // Checking customer profile data
      for (WapWebhookPayload.Contact contact : contacts) {
        userWhatsappMessages.setUserName(contact.getProfile().getName());
        userWhatsappMessages.setUserMobile(contact.getWa_id());
      }
    }

    return userWhatsappMessages;
  }

  // This method will update the status of messages
  private void updateMsgStatus(List<WapWebhookPayload.Status> statuses) {
    log.info("Inside updateMsgStatus");
    for (WapWebhookPayload.Status status : statuses) {
      String id = status.getId();
      String recipientId = status.getRecipient_id();
      String msgStatus = status.getStatus();
      long timestamp = status.getTimestamp();

      UserWhatsappMessages usrMsgs = userWapMsgRepo.findByResponseMsgIdAndUserMobile(id, recipientId);
      if (usrMsgs != null) {
        usrMsgs.setCurrentMsgStatus(msgStatus);

        // Update status flags based on the current message status
        switch (msgStatus) {
          case "accepted":
            usrMsgs.setAcceptedStatusFlg(true);
            usrMsgs.setAcceptedTime(timestamp);
            break;
          case "sent":
            usrMsgs.setSentStatusFlg(true);
            usrMsgs.setSentTime(timestamp);
            break;
          case "delivered":
            usrMsgs.setDeliveredStatusFlg(true);
            usrMsgs.setDeliveredTime(timestamp);
            break;
          case "read":
            usrMsgs.setReadStatusFlg(true);
            usrMsgs.setReadTime(timestamp);
            break;
          case "failed":
            usrMsgs.setErrorStatusFlg(true);
            usrMsgs.setErrorTime(timestamp);
            List<WapWebhookPayload.Errors> errors = status.getErrors();
            if (errors != null) {
              for (WapWebhookPayload.Errors error : errors) {
                usrMsgs.setErrorRemark(error.getTitle());
                usrMsgs.setErrorCode(error.getCode());
              }
            }
            break;
          default:
            log.warn("Unknown message status: {}", msgStatus);
            break;
        }

        userWapMsgRepo.save(usrMsgs);
        log.info("After update msg status: {}, for {}", msgStatus, recipientId);
      } else {
        log.warn("No user message found for ID: {} and Recipient: {}", id, recipientId);
      }
    }
  }



}
