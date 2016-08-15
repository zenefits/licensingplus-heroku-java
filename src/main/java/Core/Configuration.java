package Core;

import Core.Utils.CalenderUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by vthiruvengadam on 8/10/16.
 */
public class Configuration {

    private static final String niprUserNameField = "NIPR_USERNAME";
    private static String niprUsername;

    private static final String niprPasswordField = "NIPR_PASSWORD";
    private static String niprPassword;

    private static final String salesForceConsumerKeyField = "SALESFORCE_CONSUMER_KEY";
    private static String salesForceConsumerKey;

    private static final String salesForceConsumerSecretField = "SALESFORCE_CONSUMER_SECRET";
    private static String salesForceConsumerSecret;

    private static final String salesForceUsernameField = "SALESFORCE_USERNAME";
    private static String salesForceUsername;

    private static final String salesForcePasswordField = "SALESFORCE_PASSWORD";
    private static String salesForcePassword;

    private static final String sendGridApiKeyField = "SENDGRID_API_KEY";
    private static String sendGridApiKey;

    private static final String alertEmailRecipientField = "EMAIL_ALERT_RECIPIENT";
    private static String alertEmailRecipient;

    private static final String alertEmailSenderField = "EMAIL_ALERT_SENDER";
    private static String alertEmailSender;

    private static final String reconcilerRetryField = "RETRY_INTERVAL";
    private static int reconcilerRetry;

    private static final String resyncDaysCountField = "RESYNC_DAYS_COUNT";
    private static int resyncDaysCount;

    private static final int DefaultResyncDaysCount = 5;
    private static final int MaxResyncDaysCount = 14;

    public static int GetRetryInterval() {
        return reconcilerRetry;
    }

    public static int GetResyncDaysCount() {
        return resyncDaysCount;
    }

    public static void LoadParams() {

        niprUsername = System.getenv(niprUserNameField);
        ThrowIfEmpty(niprUserNameField, niprUsername);

        niprPassword = System.getenv(niprPasswordField);
        ThrowIfEmpty(niprPasswordField, niprPassword);

        salesForceConsumerKey = System.getenv(salesForceConsumerKeyField);
        ThrowIfEmpty(salesForceConsumerKeyField, salesForceConsumerKey);

        salesForceConsumerSecret = System.getenv(salesForceConsumerSecretField);
        ThrowIfEmpty(salesForceConsumerSecretField, salesForceConsumerSecret);

        salesForceUsername = System.getenv(salesForceUsernameField);
        ThrowIfEmpty(salesForceUsernameField, salesForceUsername);

        salesForcePassword = System.getenv(salesForcePasswordField);
        ThrowIfEmpty(salesForcePasswordField, salesForcePassword);

        sendGridApiKey = System.getenv(sendGridApiKeyField);
        alertEmailRecipient = System.getenv(alertEmailRecipientField);
        alertEmailSender = System.getenv(alertEmailSenderField);

        String lInterval = System.getenv(reconcilerRetryField);
        if(CalenderUtils.isNullOrWhiteSpace(lInterval)) {
            reconcilerRetry = 3600000; // Every hour
        }
        else {
            reconcilerRetry = Integer.parseInt(lInterval);
        }

        String lDays = System.getenv(resyncDaysCountField);
        if(CalenderUtils.isNullOrWhiteSpace(lInterval)) {
            resyncDaysCount = DefaultResyncDaysCount; // Every minute
        }
        else {
            resyncDaysCount = Integer.parseInt(lInterval);
            if(resyncDaysCount > MaxResyncDaysCount) {
                // We cannot go back beyond MaxResyncDaysCount = 14
                resyncDaysCount = MaxResyncDaysCount;
            }
            else if(resyncDaysCount < 0) {
                resyncDaysCount = DefaultResyncDaysCount;
            }
        }
    }

    public static String GetNiprAuthToken() {
        String lVal = niprUsername + ":" + niprPassword;
        byte[] encodedBytes = Base64.encodeBase64(lVal.getBytes());
        return "Basic " + new String(encodedBytes);
    }

    public static String GetSalesForceAuthInfo(){
        String lInfo = "";
        try {
            // grant_type=password&client_id=<SALESFORCE_CONSUMER_KEY>&client_secret=<SALESFORCE_CONSUMER_SECRET>&username=<SALESFORCE_USERNAME>&password=<SALESFORCE_PASSWORD>
            lInfo = "grant_type=password&client_id=" + salesForceConsumerKey + "&client_secret=" +
                    salesForceConsumerSecret + "&username=" + salesForceUsername + "&password=" + URLEncoder.encode(salesForcePassword, "UTF-8");
            return lInfo;
        }
        catch(Exception ex) {
            System.out.println("Failed to url encode sales force auth info");
        }
        return lInfo;
    }

    public static String GetSendGridKey() {
        return sendGridApiKey;
    }

    public static String GetAlertEmailRecipient() {
        return alertEmailRecipient;
    }

    public static String GetAlertEmailSender() {
        return alertEmailSender;
    }

    public static void ThrowIfEmpty(String aInDataField, String aInData) {

        if(CalenderUtils.isNullOrWhiteSpace(aInData)) {
            throw new IllegalArgumentException("ENV Field " + aInDataField + " is not set");
        }
    }
}
