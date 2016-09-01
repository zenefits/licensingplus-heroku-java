package core.utils;

import org.springframework.http.*;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vthiruvengadam on 8/15/16.
 */
public class SendGridClient {

    private final static String sendGridUrl = "https://api.sendgrid.com/v3/mail/send";
    private final static String sendGridUrlv2 = "https://api.sendgrid.com/api/mail.send.json";

    private final static String sendEmailData =  "{" +
            "\"personalizations\": [{ \"to\": [{\"email\": \"<SEND_TO_EMAIL>\"}]}]," +
            "\"from\": { \"email\": \"<SEND_FROM_EMAIL>\" }," +
            "\"subject\": \"<EMAIL_SUBJECT>\"," +
            "\"content\": [<EMAIL_CONTENT>] }";

    // api_user=your_sendgrid_username&api_key=your_sendgrid_password&to=destination@example.com&toname=Destination&subject=Example_Subject&text=testingtextbody&from=info@domain.com

    private static String apiToken;

    private static String alertEmailRecipient;

    private static String alertEmailSender;

    private static String sendgridUsername;

    private static String sendgridPassword;

    public static String getSendGridEmailBody(String aInSubject, String aInBody) {
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

    public static void init(String aInToken, String aInEmailRecipient, String aInAlertEmailSender) {
        apiToken = aInToken;
        alertEmailRecipient = aInEmailRecipient;
        alertEmailSender = aInAlertEmailSender;
    }

    public static void initV2(String aInUsername, String aInPassword, String aInEmailRecipient, String aInAlertEmailSender) {
        sendgridUsername = aInUsername;
        sendgridPassword = aInPassword;
        alertEmailRecipient = aInEmailRecipient;
        alertEmailSender = aInAlertEmailSender;
    }

    public static void sendEmail(String aInSubject, String aInBody) {

        if(CalenderUtils.isNullOrWhiteSpace(apiToken)) {
            System.out.println("SendGridClient: No api key is set, skipping....");
        }


        String lData = getSendGridEmailBody(aInSubject, aInBody);
        System.out.println("SendGridClient: Sending email " + lData);

        try
        {
            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.setContentType(MediaType.APPLICATION_JSON);

            lHeaders.set("Authorization", "Bearer " + apiToken);
            // send request and parse result
            ResponseEntity<String> lResponse = WebUtils.postData(sendGridUrl, lData, lHeaders, String.class);
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

    public static void sendEmailv2 (String aInSubject, String aInBody) {

        if(CalenderUtils.isNullOrWhiteSpace(sendgridUsername)
           || CalenderUtils.isNullOrWhiteSpace(sendgridPassword)) {
            System.out.println("SendGridClientV2: No username password set, skipping....");
        }

        try {
            String lData = "api_user=" + sendgridUsername +
                    "&api_key=" + sendgridPassword +
                    "&to=" + alertEmailRecipient +
                    "&subject=" + URLEncoder.encode(aInSubject, "UTF-8") +
                    "&text=" + URLEncoder.encode(aInBody, "UTF-8") +
                    "&from=" + alertEmailSender;
            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            ResponseEntity<String> lResponse = WebUtils.postData(sendGridUrlv2, lData, lHeaders, String.class);
            if ((lResponse.getStatusCode() == HttpStatus.OK)
                    || (lResponse.getStatusCode() == HttpStatus.ACCEPTED))
            {
                System.out.println("SendGridClientV2: Email sent ");

            } else {
                System.out.println("SendGridClientV2: Failed to Send Email:  " + lResponse.getStatusCode());
            }

        }
        catch (Exception ex) {
            System.out.println("SendGridClientV2: Failed to send Email " + ex.getMessage());
        }
    }

    public static boolean sendEmailWithAttachment (String aInSubject, String aInBody, String aInFilePath) {

        boolean lStatus = false;
        if(CalenderUtils.isNullOrWhiteSpace(sendgridUsername)
                || CalenderUtils.isNullOrWhiteSpace(sendgridPassword)) {
            System.out.println("SendGridClientV2: No username password set, skipping....");
            return lStatus;
        }

        try {
            File lFile = new File(aInFilePath);
            if(!lFile.exists()) {
                System.out.println("SendGridClient: File to be sent as an attachment does not exist " + aInFilePath);
                return false;
            }
            // Read the contents of a file
            String lFileContents = readFile(aInFilePath, Charset.defaultCharset());

            String lData = "api_user=" + sendgridUsername +
                    "&api_key=" + sendgridPassword +
                    "&to=" + alertEmailRecipient +
                    "&subject=" + URLEncoder.encode(aInSubject, "UTF-8") +
                    "&text=" + URLEncoder.encode(aInBody, "UTF-8") +
                    "&from=" + alertEmailSender +
                    "&files[" + lFile.getName() + "]=" + URLEncoder.encode(lFileContents, "UTF-8");
            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            ResponseEntity<String> lResponse = WebUtils.postData(sendGridUrlv2, lData, lHeaders, String.class);
            if ((lResponse.getStatusCode() == HttpStatus.OK)
                    || (lResponse.getStatusCode() == HttpStatus.ACCEPTED))
            {
                System.out.println("SendGridClientV2: Email sent with attachment");
                lStatus = true;
            } else {
                System.out.println("SendGridClientV2: Failed to Send Email with attachement:  " + lResponse.getStatusCode());
            }

        }
        catch (Exception ex) {
            System.out.println("SendGridClientV2: Failed to send Email with attachment " + ex.getMessage());
        }

        return lStatus;
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
