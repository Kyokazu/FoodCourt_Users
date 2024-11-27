package com.foodcourt.proyect.infrastructure.handler;

import com.foodcourt.proyect.config.TwilioConfig;
import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSHandler {

    private final TwilioRestClient twilioRestClient;
    private final TwilioConfig twilioConfig;
    private static final Logger logger = LoggerFactory.getLogger(SMSHandler.class);

    @Autowired
    public SMSHandler(TwilioRestClient twilioRestClient, TwilioConfig twilioConfig) {
        this.twilioRestClient = twilioRestClient;
        this.twilioConfig = twilioConfig;
    }

    public void sendSms(String to, String message) {
        try {
            Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                    message
            ).create(twilioRestClient);
        } catch (ApiException e) {
            logger.error("Failed to send SMS: {}", e.getMessage());
            throw e;
        }
    }

    public void sendWhatsapp(String to, String message) {
        try {
            Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber("whatsapp:" + twilioConfig.getTwilioPhoneNumber()),
                    message
            ).create(twilioRestClient);
        } catch (ApiException e) {
            logger.error("Failed to send WhatsApp message: {}", e.getMessage());
            throw e;
        }
    }
}