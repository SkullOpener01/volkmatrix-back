package com.volkmatrix.whatsapp.service.repo;

import com.volkmatrix.whatsapp.service.model.whatsapp.UserWhatsappMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWapMsgRepo extends JpaRepository<UserWhatsappMessages,Long> {


  UserWhatsappMessages findByResponseMsgIdAndUserMobile(String id, String recipientId);


  @Query("SELECT DISTINCT e.userMobile FROM UserWhatsappMessages e WHERE e.phone_number_id = :phoneNumberID")
  List<String> findDistinctUsersOfBusiness(@Param("phoneNumberID") String phoneNumberID);

  // Fetch data based on userMobile with LIKE, and order by tmstmp in descending order
  @Query("SELECT c FROM UserWhatsappMessages c WHERE c.userMobile LIKE %:userMobile AND c.phone_number_id = " +
      ":phoneNumberId")
  List<UserWhatsappMessages> fetchUserChatMessage(@Param("userMobile") String userMobile, @Param("phoneNumberId") String phoneNumberId);
}
