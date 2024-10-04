package com.volkmatrix.whatsapp.service.model.whatsapp;

import com.volkmatrix.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Data
//public class UserWhatsappMessages extends BaseModel {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private long id;
//
//  // Org Details
//  @Column(nullable = false)
//  private String phone_number_id;
//
//  @Column(nullable = false)
//  private String display_phone_number;
//
//  // User Profile Details
//  @Column(nullable = false)
//  private String userMobile;
//
//  @Column(nullable = false)
//  private String userName;
//
//  // Message Details
//  @Column(nullable = false)
//  private String messageBy; // business or User
//
//  @Column(nullable = false)
//  private String userMsgTime; // human-readable time
//
//  private long userMsgTimeEpoch; // epoch time for easier sorting and comparisons
//
//  @Column(nullable = false)
//  private String messageType; // text/media/reaction/location/reactive
//
//  // Text message details
//  private String msgText;
//
//  // Reaction message details
//  private String reactedOnMsgId; // ID of the message that received the reaction
//  private String emoji; // Reaction emoji
//
//  // Media message details
//  private String mediaType; // e.g., image/video/audio/document
//  private String mediaFileUrl; // URL to the media file
//
//  // Current message status details
//  @Column(nullable = false)
//  private String currentMsgStatus; // accepted/sent/delivered/read/failed
//
//  private long sentTime; // epoch time for easier handling
//  private boolean sentStatusFlg = false;
//
//  private long deliveredTime; // epoch time
//  private boolean deliveredStatusFlg = false;
//
//  private long readTime; // epoch time
//  private boolean readStatusFlg = false;
//
//  private long errorTime; // epoch time
//  private boolean errorStatusFlg = false;
//
//  private long acceptedTime; // epoch time
//  private boolean acceptedStatusFlg = false;
//
//  private String responseMsgId; // ID of the message in response to
//  private String errorRemark; // Error description if message failed
//  private int errorCode; // Error code if message failed
//
//  // Optional fields for categorization
//  private String category; // marketing/service/utility/authentication
//
//  // Method to check if the message is media type
//  public boolean isMediaMessage() {
//    return mediaType != null && !mediaType.isEmpty();
//  }
//
//  // Additional utility methods can be added here
//}



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type")
@Data
public class UserWhatsappMessages extends BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // Org Details
  private String phone_number_id;

  private String display_phone_number;

  // User Profile Details
  private String userMobile;

  private String userName;

  // Common message attributes
  private String messageBy; // business or User


  // Use a long to store the timestamp in milliseconds (epoch time)
  private String userMsgTime; // epoch time for the message
  private long userMsgTimeEpoch;

  // Status Details
  private String currentMsgStatus;  // accepted/sent/delivered/read/failed
    private long sentTime; // epoch time for easier handling
  private boolean sentStatusFlg = false;

  private long deliveredTime; // epoch time
  private boolean deliveredStatusFlg = false;

  private long readTime; // epoch time
  private boolean readStatusFlg = false;

  private long errorTime; // epoch time
  private boolean errorStatusFlg = false;

  private long acceptedTime; // epoch time
  private boolean acceptedStatusFlg = false;

  private String responseMsgId; // ID of the message in response to
  private String errorRemark; // Error description if message failed
  private int errorCode; // Error code if message failed


}