package com.volkmatrix.whatsapp.controller;

import com.volkmatrix.whatsapp.service.dto.WapWebhookPayload;
import com.volkmatrix.whatsapp.service.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1.0/")
public class WebhookController {

  private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

  private static final String VERIFY_TOKEN = "EAGHfjF9KLjcBO5nkhPMg221zZB12g9CyzbTw3IFYJCQb51V8Lg4RvZA3KsIZA7xkCipp34gAAK8C2iqAZB199F60TXDrKLFNO4JYdAv4f46v2vNZCKlrKNy2TuCaPXZCSL8sTEJ5KZBSws0FkL0uk1ZBgUu9Kyqm9CUZB8vNJuYZCx4AZAn6SAi7ZCFp81BCbUYIWTWniAZDZD";  // Replace with your actual verification token

  @Autowired
  private WebhookService webhookService;



  /*@PostMapping("/meta/webhook")
  public ResponseEntity<String> handleWebhook(@RequestHeader("X-Hub-Signature-256") String signature,
                                              @RequestBody Map<String, Object> payload) {
    // Log the received signature and payload for debugging
    System.out.println("Received X-Hub-Signature-256: " + signature);
    System.out.println("Received payload: " + payload);

    // Here you would verify the signature to ensure authenticity
    boolean isVerified = verifySignature(signature, payload);

    if (!isVerified) {
      return new ResponseEntity<>("Invalid signature", HttpStatus.UNAUTHORIZED);
    }

    // You can now process the payload, e.g., save the data, trigger events, etc.
    // For example:
    String objectType = (String) payload.get("object");
    System.out.println("Object type: " + objectType);


    CompletableFuture.runAsync(() -> {
      webhookService.manageWebhook(p);
    });

    // Respond to the webhook with a success message
    return new ResponseEntity<>("Webhook received and processed", HttpStatus.OK);
  }

  // This is just a placeholder for signature verification logic
  private boolean verifySignature(String signature, Map<String, Object> payload) {
    // Here you should implement the logic to verify the SHA-256 signature.
    // For now, returning true for demo purposes.
    return true;
  }*/




  @PostMapping("/meta/webhook")
  public ResponseEntity<String> handleWebhook(@RequestHeader("X-Hub-Signature-256") String signature,
                                              @RequestBody WapWebhookPayload payload) {
    // Log the received signature and payload for debugging
    System.out.println("Received X-Hub-Signature-256: " + signature);
    System.out.println("Received payload: " + payload);

    // Here you would verify the signature to ensure authenticity
    boolean isVerified = verifySignature(signature, payload);

    if (!isVerified) {
      return new ResponseEntity<>("Invalid signature", HttpStatus.UNAUTHORIZED);
    }

    // Process the payload asynchronously
    CompletableFuture.runAsync(() -> {
      webhookService.manageWebhook(payload); // Pass the mapped payload to the service
    });

    // Respond to the webhook with a success message
    return new ResponseEntity<>("Webhook received and processed", HttpStatus.OK);
  }

  private boolean verifySignature(String signature, WapWebhookPayload payload) {
    // Implement your signature verification logic here
    return true; // Replace with actual verification
  }




  // This endpoint handles GET requests for webhook verification
  @GetMapping("/meta/webhook")
  public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") String mode,
                                              @RequestParam("hub.challenge") String challenge,
                                              @RequestParam("hub.verify_token") String verifyToken) {
    System.out.println("Verification request received");
    System.out.println("hub.mode: " + mode);
    System.out.println("hub.challenge: " + challenge);
    System.out.println("hub.verify_token: " + verifyToken);

    // Check if the verify_token matches the expected value
    if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(verifyToken)) {
      // Return the challenge token as the response to confirm verification
      return ResponseEntity.ok(challenge);
    } else {
      // Return an error response if the verification token does not match
      return ResponseEntity.status(403).body("Verification failed: Invalid verify token");
    }
  }


}
