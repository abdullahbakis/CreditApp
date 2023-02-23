package practicum.creditapp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This class provides a service for sending SMS messages using Twilio API.
 */
@Service
public class SMSService {

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.from.phone.number}")
    private String FROM_NUMBER;

    /**
     * Sends an SMS message to the specified phone number.
     * @param to the recipient's phone number in E.164 format (e.g. +1234567890)
     * @param body the text of the message to be sent
     */
    public void sendSMS(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(FROM_NUMBER),
                        body)
                .create();

        System.out.println("SMS sent to " + to + ", SID: " + message.getSid());
    }

}
