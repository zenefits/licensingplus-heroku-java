package Core;

import Core.Utils.CalenderUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Objects;

/**
 * Created by vthiruvengadam on 8/10/16.
 */
public class Configuration {

    private static final String usernameField = "USERNAME";
    private static String username;

    private static final String passwordField = "PASSWORD";
    private static String password;

    private static final String niprAlertEndpointField = "NIPR_ALERT_ENDPOINT";
    private static String getNiprAlertEndpoint;

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

    private static final String salesForceAuthUrlField = "SALESFORCE_AUTH_URL";
    private static String salesForceAuthUrl;

    private static final String sendGridApiKeyField = "SENDGRID_API_KEY";
    private static String sendGridApiKey;

    private static final String sendGridUsernameField = "SENDGRID_USERNAME";
    private static String sendGridUsername;

    private static final String sendGridPasswordField = "SENDGRID_PASSWORD";
    private static String sendGridPassword;

    private static final String alertEmailRecipientField = "EMAIL_ALERT_RECIPIENT";
    private static String alertEmailRecipient;

    private static final String alertEmailSenderField = "EMAIL_ALERT_SENDER";
    private static String alertEmailSender;

    private static final String reconcilerRetryField = "RETRY_INTERVAL";
    private static int reconcilerRetry;

    private static final String resyncDaysCountField = "RESYNC_DAYS_COUNT";
    private static int resyncDaysCount;

    private static final int defaultResyncDaysCount = 5;
    private static final int maxResyncDaysCount = 14;

    private static String expectedAuthHeader = "";

    public static int GetResyncDaysCount() {
        return resyncDaysCount;
    }

    public static void LoadParams() {

        username = System.getenv(usernameField);
        password = System.getenv(passwordField);
        String s = username + ":" + password;
        byte[] encodedBytes = Base64.encodeBase64(s.getBytes());
        expectedAuthHeader = "Basic " + new String(encodedBytes);

        // "https://pdb-services.nipr.com/pdb-alerts-industry-services/services/industry-ws"
        getNiprAlertEndpoint = System.getenv(niprAlertEndpointField);
        throwIfEmpty(niprAlertEndpointField, getNiprAlertEndpoint);

        niprUsername = System.getenv(niprUserNameField);
        throwIfEmpty(niprUserNameField, niprUsername);

        niprPassword = System.getenv(niprPasswordField);
        throwIfEmpty(niprPasswordField, niprPassword);

        salesForceConsumerKey = System.getenv(salesForceConsumerKeyField);
        throwIfEmpty(salesForceConsumerKeyField, salesForceConsumerKey);

        salesForceConsumerSecret = System.getenv(salesForceConsumerSecretField);
        throwIfEmpty(salesForceConsumerSecretField, salesForceConsumerSecret);

        salesForceUsername = System.getenv(salesForceUsernameField);
        throwIfEmpty(salesForceUsernameField, salesForceUsername);

        salesForcePassword = System.getenv(salesForcePasswordField);
        throwIfEmpty(salesForcePasswordField, salesForcePassword);

        // https://test.salesforce.com/services/oauth2/token
        salesForceAuthUrl = System.getenv(salesForceAuthUrlField);
        throwIfEmpty(salesForceAuthUrlField, salesForceAuthUrl);

        sendGridApiKey = System.getenv(sendGridApiKeyField);
        sendGridUsername = System.getenv(sendGridUsernameField);
        sendGridPassword = System.getenv(sendGridPasswordField);

        alertEmailRecipient = System.getenv(alertEmailRecipientField);
        throwIfEmpty(alertEmailRecipientField, alertEmailRecipient);

        alertEmailSender = System.getenv(alertEmailSenderField);
        throwIfEmpty(alertEmailSenderField, alertEmailSender);

        String lInterval = System.getenv(reconcilerRetryField);
        throwIfEmpty(reconcilerRetryField, lInterval);
        reconcilerRetry = Integer.parseInt(lInterval);

        String lDays = System.getenv(resyncDaysCountField);
        if(CalenderUtils.isNullOrWhiteSpace(lDays)) {
            resyncDaysCount = defaultResyncDaysCount; // Every minute
        }
        else {
            resyncDaysCount = Integer.parseInt(lDays);
            if(resyncDaysCount > maxResyncDaysCount) {
                // We cannot go back beyond MaxResyncDaysCount = 14
                resyncDaysCount = maxResyncDaysCount;
            }
            else if(resyncDaysCount < 0) {
                resyncDaysCount = defaultResyncDaysCount;
            }
        }
    }

    public static boolean IsAuthenticated(String aInAuthHeader) {
        if(!Objects.equals(expectedAuthHeader, new String(aInAuthHeader))) {
            return false;
        }

        return true;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getGetNiprAlertEndpoint() {
        return getNiprAlertEndpoint;
    }

    public static String getSendGridKey() {
        return sendGridApiKey;
    }

    public static String getNiprUsername() {
        return niprUsername;
    }

    public static String getNiprPassword() {
        return niprPassword;
    }

    public static String getSalesForceAuthUrl() {
        return salesForceAuthUrl;
    }

    public static String getSalesForceConsumerKey() {
        return salesForceConsumerKey;
    }

    public static String getSalesForceConsumerSecret() {
        return salesForceConsumerSecret;
    }

    public static String getSalesForceUsername() {
        return salesForceUsername;
    }

    public static String getSalesForcePassword() {
        return salesForcePassword;
    }

    public static String getSendGridApiKey() {
        return sendGridApiKey;
    }

    public static String getSendGridUsername() {
        return sendGridUsername;
    }

    public static String getSendGridPassword() {
        return sendGridPassword;
    }

    public static String getAlertEmailSender() {
        return alertEmailSender;
    }

    public static String getAlertEmailRecipient() {
        return alertEmailRecipient;
    }

    public static int getReconcilerRetry() {
        return reconcilerRetry;
    }

    public static int getResyncDaysCount() {
        return resyncDaysCount;
    }

    private static void throwIfEmpty(String aInDataField, String aInData) {

        if(CalenderUtils.isNullOrWhiteSpace(aInData)) {
            throw new IllegalArgumentException("ENV Field " + aInDataField + " is not set");
        }
    }

}
