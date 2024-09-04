package com.volkmatrix.common.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class WapEmailConfiguration {

  @Autowired
  private JavaMailSender mailSender;

  /**
   * This method will send compose and send the message
   * */
  public  void sendMail(String to, String subject, String body)
  {
    SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("info@fonabit.com");
    message.setFrom("noreply@pixabits.in");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
  }

  public  void sendHtmlMail(String to, String subject, String body) throws MessagingException {
    SimpleMailMessage message = new SimpleMailMessage();
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    log.info("send mail");
//        String htmlMsg="<html><body><h1>This is actual message</h1></body></html>";
//        String htmlMsg=EmailTemplates.USER_SAVE_EMAIL;
//        String htmlMsg=EmailTemplates.USER_SMPP_ACTIVATE_EMAIL;
//        htmlMsg=htmlMsg.replace("USERNAME","Rahul");
//        htmlMsg=htmlMsg.replace("PASSWORD","Rahul@123");
//        htmlMsg=htmlMsg.replace("PORT","4123");
//        htmlMsg=htmlMsg.replace("IP","10.21.68.33");
//        String htmlMsg2=EmailTemplates.USER_SMPP_ACTIVATE_EMAIL;
//        mimeMessage.setContent(body, "text/html");
    mimeMessage.setContent("street.html", "text/html");
//        mimeMessage.setContent(body, "text/html");
    helper.setText(body, true);
//        message.setFrom("info@fonabit.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        helper.setFrom("info@fonabit.com");
    helper.setFrom("noreply@pixabits.in");
    helper.setTo(to);
    helper.setSubject(subject);
    mailSender.send(mimeMessage);
    log.info("sent mail");
  }

  @Async
  public  void sendAttchMimeEmail(String[] emails, String attachment, String subject, String sendMessage) {

    try{
      log.info("FilePath"+attachment);
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");
      helper.setFrom("noreply@pixabits.in");
      helper.setReplyTo("noreply@pixabits.in");
      if(attachment!=null && !attachment.isEmpty()){
        FileSystemResource file = new FileSystemResource(new File(attachment));
        helper.addAttachment(file.getFilename(),file);
      }
      helper.setSubject(subject);
      helper.setBcc(emails);
      helper.setText(sendMessage,true);
      mailSender.send(message);
      log.info("Email Sent");
    }catch(Exception e){
      log.info("Error"+e.getMessage());
    }

  }

}
