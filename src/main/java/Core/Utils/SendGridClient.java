package Core.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;

/**
 * Created by vthiruvengadam on 8/15/16.
 */
//PLEASE RESOLVE all warnings (unused referenced class)
//PLEASE look into use log4j, so we can check ERROR vs INFO easily
//PLEASE use lower case as the firstLetter for method
public class SendGridClient {

    private final static String sendGridUrl = "https://api.sendgrid.com/v3/mail/send";

    private final static String sendEmailData =  "{" +
            "\"personalizations\": [{ \"to\": [{\"email\": \"<SEND_TO_EMAIL>\"}]}]," +
            "\"from\": { \"email\": \"<SEND_FROM_EMAIL>\" }," +
            "\"subject\": \"<EMAIL_SUBJECT>\"," +
            "\"content\": [<EMAIL_CONTENT>] }";

    private static String apiToken;

    private static String alertEmailRecipient;

    private static String alertEmailSender;

    public static String GetSendGridEmailBody(String aInSubject, String aInBody) {
    	//PLEASE use generics here
        JSONObject obj = new JSONObject();
        obj.put("value", aInBody);
        obj.put("type", "text/plain");

        String lSendEmailData = sendEmailData;
        lSendEmailData = lSendEmailData.replace("<SEND_TO_EMAIL>", alertEmailRecipient);
        lSendEmailData = lSendEmailData.replace("<SEND_FROM_EMAIL>", alertEmailSender);
        lSendEmailData = lSendEmailData.replace("<EMAIL_SUBJECT>", aInSubject);
        lSendEmailData = lSendEmailData.replace("<EMAIL_CONTENT>", obj.toJSONString());
        return lSendEmailData;
    }

    public static void Init(String aInToken, String aInEmailRecipient, String aInAlertEmailSender) {
        apiToken = aInToken;
        alertEmailRecipient = aInEmailRecipient;
        alertEmailSender = aInAlertEmailSender;
    }

    public static void SendEmail(String aInSubject, String aInBody) {

        if(CalenderUtils.isNullOrWhiteSpace(apiToken)) {
            System.out.println("SendGridClient: No api key is set, skipping....");
        }


        String lData = GetSendGridEmailBody(aInSubject, aInBody);
        System.out.println("SendGridClient: Sending email " + lData);

        try
        {
            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.setContentType(MediaType.APPLICATION_JSON);

            lHeaders.set("Authorization", "Bearer " + apiToken);
            // send request and parse result
            ResponseEntity<String> lResponse = WebUtils.PostData(sendGridUrl, lData, lHeaders, String.class);
            if ((lResponse.getStatusCode() == HttpStatus.OK)
                || (lResponse.getStatusCode() == HttpStatus.ACCEPTED))
            {
                System.out.println("SendGridClient: Email sent ");

            } else {
                System.out.println("SendGridClient: Failed to Send Email:  " + lResponse.getStatusCode());
            }
        }
        catch(Exception e) {

            System.out.println("SendGridClient: Failed to send email due to exception " + e.getMessage());
        }

        System.out.println("SendGridClient: Email sent successfully");
    }


}
