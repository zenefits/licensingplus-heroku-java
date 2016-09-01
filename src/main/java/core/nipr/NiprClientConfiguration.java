package core.nipr;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

public class NiprClientConfiguration {

    private static String niprAuthToken;

    public static String getNiprAuthToken() {
        return niprAuthToken;
    }

    private static Jaxb2Marshaller getMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("nipr.wsdl");
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    public static NiprClient getNiprClient(String aInUri, String aInUsername, String aInPassword) {
        niprAuthToken = getNiprAuthToken(aInUsername, aInPassword);
        Jaxb2Marshaller lMarshaller = NiprClientConfiguration.getMarshaller();

        NiprClient client = new NiprClient();
        client.setDefaultUri(aInUri);
        client.setMarshaller(lMarshaller);
        client.setUnmarshaller(lMarshaller);
        WebServiceTemplate template = client.getWebServiceTemplate();
        template.setMessageSender(new WebServiceMessageSenderWithAuth());
        return client;
    }

    private static String getNiprAuthToken(String aInUsername, String aInPassword) {
        String lVal = aInUsername + ":" + aInPassword;
        byte[] encodedBytes = Base64.encodeBase64(lVal.getBytes());
        return "Basic " + new String(encodedBytes);
    }
}
