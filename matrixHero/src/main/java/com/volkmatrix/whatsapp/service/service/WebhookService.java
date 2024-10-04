package com.volkmatrix.whatsapp.service.service;


import com.volkmatrix.whatsapp.service.dto.WapWebhookPayload;

public interface WebhookService {

 void manageWebhook(WapWebhookPayload payload);
}
