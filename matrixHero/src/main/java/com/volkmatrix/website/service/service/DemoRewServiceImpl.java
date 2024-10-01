package com.volkmatrix.website.service.service;

import com.volkmatrix.common.CommonConstants;
import com.volkmatrix.common.StatusCodes;
import com.volkmatrix.common.exception.ApiServiceException;
import com.volkmatrix.common.exception.ResourceNotFoundException;
import com.volkmatrix.common.model.BaseResponse;
import com.volkmatrix.common.model.DataResponse;
import com.volkmatrix.common.utils.ApplicationUtils;
import com.volkmatrix.common.utils.EmailConfiguration;
import com.volkmatrix.common.utils.EmailTemplates;
import com.volkmatrix.common.utils.WhatsappConfigs;
import com.volkmatrix.website.service.dto.DemoReqNewDto;
import com.volkmatrix.website.service.dto.DemoReqUpdateDtp;
import com.volkmatrix.website.service.dto.FetchDemoReqDto;
import com.volkmatrix.website.service.model.DemoRequests;
import com.volkmatrix.website.service.model.VolkServices;
import com.volkmatrix.website.service.repo.DemoReqRepo;
import com.volkmatrix.website.service.repo.ServicesRepo;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DemoRewServiceImpl implements DemoReqService {

  private static final Logger log = LoggerFactory.getLogger(DemoRewServiceImpl.class);
  @Autowired
  private DemoReqRepo demoReqRepo;

  @Autowired
  private com.volkmatrix.website.service.repo.ServicesRepo ServicesRepo;
  @Autowired
  private EntityManager entityManager; // Use EntityManager for CriteriaBuilder
  @Autowired
  private ServicesRepo servicesRepo;

  @Autowired
  EmailConfiguration emailConfiguration;

  @Autowired
  WhatsappConfigs whatsappConfigs;



  @Override
  @Transactional
  public ResponseEntity<BaseResponse> createDemoRequest(@Valid DemoReqNewDto requestDto) {
    try {
      BaseResponse response = new BaseResponse();
      // Create a new DemoRequests object from the DTO
      DemoRequests newRequest = new DemoRequests();
      newRequest.setName(requestDto.getName());
      newRequest.setEmail(requestDto.getEmail());
      newRequest.setMobile(requestDto.getMobile());
      newRequest.setMessage(requestDto.getMessage());
      if(requestDto.isBotPenguinReq()){
        newRequest.setDemoDateTime(requestDto.getDemoDate());
      }else {
      newRequest.setDemoDateTime(requestDto.getDemoDate() + ", " + requestDto.getDemoTime());
      }
      newRequest.setWapNotification(requestDto.isWapNotification());
      newRequest.setConnectExpert(requestDto.isConnectExpert());
      newRequest.setKeepInformed(requestDto.isKeepInformed());


      // Save the demo request
      DemoRequests savedRequest = demoReqRepo.save(newRequest);

      String emailBody = EmailTemplates.DEMO_REQEST_MAIL_NEW;
      emailBody = emailBody.replace("CUSTOMER_NAME", requestDto.getName());
      emailBody = emailBody.replace("CUSTOMER_EMAIL", requestDto.getEmail());
      emailBody = emailBody.replace("CUSTOMER_PHONE", requestDto.getMobile());

      String finalEmailBody = emailBody;
      String [] recipient = {"business@volkmatrix.com", requestDto.getEmail()};
      new Thread(() -> {
        log.info("sending message to recipient : {}", recipient[1]);
        emailConfiguration.sendAttchMimeEmail(recipient, null,"Get Ready: Your Personalized Demo with Volkmatrix " +
            "is Coming Soon!", finalEmailBody);
      }).start();

      // sending whatsapp to customer
      new Thread(() -> {
        log.info("sending whatsapp to customer : {}");
        whatsappConfigs.sendDemoToCustomer(requestDto.getMobile(),requestDto.getName(),
            savedRequest.getDemoDateTime());
      }).start();

      // sending whatsapp to business
      new Thread(() -> {
        log.info("sending whatsapp to business : {}");
        whatsappConfigs.sendDemoToBizz("9717803016",requestDto.getName(),requestDto.getMobile(),requestDto.getEmail()
            ,savedRequest.getDemoDateTime());
      }).start();



      response.setMessage(CommonConstants.DEMO_REQUEST_SUCCESS);
      response.setStatusCode(StatusCodes.REQUEST_SUCCESS);

      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      // Handle any exceptions that occur during the process
      throw new ApiServiceException(e.getMessage());
    }
  }

  @Override
  @Transactional
  public ResponseEntity<BaseResponse> updateDemoRequest(@Valid DemoReqUpdateDtp updateDto) {
    try {
      BaseResponse response = new BaseResponse();
      // Fetch the existing DemoRequests record
      Optional<DemoRequests> existingRequestOpt = demoReqRepo.findById(updateDto.getId());

      if (!existingRequestOpt.isPresent()) {
        response.setMessage(CommonConstants.ErrorMessages.RECORD_NOT_FOUND);
        response.setStatusCode(StatusCodes.REQUEST_FAILURE);
      } else {

        DemoRequests existingRequest = existingRequestOpt.get();

        // Update fields with values from DTO
        existingRequest.setName(updateDto.getName());
        existingRequest.setEmail(updateDto.getEmail());
        existingRequest.setMobile(updateDto.getMobile());
        existingRequest.setMessage(updateDto.getMessage());
        existingRequest.setWapNotification(updateDto.isWapNotification());
        existingRequest.setConnectExpert(updateDto.isConnectExpert());
        existingRequest.setKeepInformed(updateDto.isKeepInformed());

        // Update management fields
        existingRequest.setContacted(updateDto.isContacted());
        existingRequest.setByWhom(updateDto.getByWhom());
        existingRequest.setRemarks(updateDto.getRemarks());
        existingRequest.setProposalSent(updateDto.isProposalSent());

        // Update services based on the IDs provided
        if (updateDto.getVolkServicesIds() != null && !updateDto.getVolkServicesIds().isEmpty()) {
          List<VolkServices> updatedServices = new ArrayList<>();
          for (Long serviceId : updateDto.getVolkServicesIds()) {
            VolkServices service = servicesRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));
            updatedServices.add(service);
          }
          existingRequest.setVolkServices(updatedServices); // Update the services
        }

        // Save the updated request
        DemoRequests updatedRequest = demoReqRepo.save(existingRequest);
        response.setMessage(CommonConstants.DATA_UPDATED_SUCCESSFULLY);
        response.setStatusCode(StatusCodes.REQUEST_SUCCESS);

      }

      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      // Handle any exceptions that occur during the process
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @Override
  public ResponseEntity<BaseResponse> deleteDemoRequest(Long id) {
    BaseResponse response = new BaseResponse();
    // Check if the request exists before deleting
    if (!demoReqRepo.existsById(id)) {
      throw new ResourceNotFoundException("DemoRequest not found with id: " + id);
    }
    demoReqRepo.deleteById(id);
    response.setMessage(CommonConstants.DATA_DELETED_SUCCESSFULLY);
    response.setStatusCode(StatusCodes.REQUEST_FAILURE);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Override
  public ResponseEntity<DataResponse> getDemoRequest(Long id) {
    DataResponse response = new DataResponse();
    DemoRequests demoRequests = demoReqRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("DemoRequest not found with id: " + id));
    response.setData(demoRequests);
    response.setMessage(CommonConstants.DEMO_REQUEST_SUCCESS);
    response.setStatusCode(StatusCodes.REQUEST_SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @Override
  public List<DemoRequests> getAllDemoRequests() {
    return demoReqRepo.findAll();
  }

  @Override
  public List<DemoRequests> getAllDemoRequestsBy(FetchDemoReqDto fetchDemoReqDto) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<DemoRequests> criteriaQuery = criteriaBuilder.createQuery(DemoRequests.class);
    Root<DemoRequests> root = criteriaQuery.from(DemoRequests.class);

    List<Predicate> predicates = new ArrayList<>();

    // Check for ID
    if (fetchDemoReqDto.getId() != null && fetchDemoReqDto.getId() > 0) {
      predicates.add(criteriaBuilder.equal(root.get("id"), fetchDemoReqDto.getId()));
    }

    // Check for byWhom
    if (fetchDemoReqDto.getByWhom() != null && !fetchDemoReqDto.getByWhom().isEmpty()) {
      predicates.add(criteriaBuilder.equal(root.get("byWhom"), fetchDemoReqDto.getByWhom()));
    }

    // Check for isContacted
    if (fetchDemoReqDto.isContacted()) {
      predicates.add(criteriaBuilder.equal(root.get("isContacted"), true));
    }

    // Check for isProposalSent
    if (fetchDemoReqDto.isProposalSent()) {
      predicates.add(criteriaBuilder.equal(root.get("isProposalSent"), true));
    }

    criteriaQuery.where(predicates.toArray(new Predicate[0]));
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public ResponseEntity<BaseResponse> sendEmailToDemoRequester(String mailAddress) {
    try {
      BaseResponse response = new BaseResponse();
      String OTP = ApplicationUtils.generateRandomNumOnLength(6);
      log.info("OTP :: {}", OTP);

      (new Thread() {
        @Override
        public void run() {
          try {
            emailConfiguration.sendMail(mailAddress, "OTP REQUEST.",
                "Dear Customer, \n\nYour OTP(One time password) is " + OTP + "\n\n Thanks \n Volkmatrix");
          } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiServiceException(e.getMessage());
          }
        }
      }).start();


      (new Thread() {
        @Override
        public void run() {
          try {
            String emailBody = EmailTemplates.OTP_LOGIN;
            emailBody = emailBody.replace("OTP_NUMBER", OTP);
            emailConfiguration.sendHtmlMail(mailAddress, "2FA Code", emailBody);
          } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiServiceException(e.getMessage());
          }
        }
      }).start();

      response.setMessage("Sending mail to requested demo");
      response.setStatusCode(StatusCodes.REQUEST_SUCCESS);


      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (Exception e) {
      throw new ApiServiceException(e.getMessage());
    }
  }
}
