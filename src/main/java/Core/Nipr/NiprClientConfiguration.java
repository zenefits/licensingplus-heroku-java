package Core.Nipr;

import nipr.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.*;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import org.springframework.ws.client.core.WebServiceTemplate;
import java.io.IOException;
import java.net.HttpURLConnection;

public class NiprClientConfiguration {

    private static String niprAuthToken;

    public static String getNiprAuthToken() {
        return niprAuthToken;
    }
    public static Jaxb2Marshaller GetMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("nipr.wsdl");
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    public static NiprClient GetNiprClient(String aInNiprAuthToken) {
        niprAuthToken = aInNiprAuthToken;
        Jaxb2Marshaller lMarshaller = NiprClientConfiguration.GetMarshaller();

        NiprClient client = new NiprClient();
        client.setDefaultUri("https://pdb-services.nipr.com/pdb-alerts-industry-services/services/industry-ws");
        client.setMarshaller(lMarshaller);
        client.setUnmarshaller(lMarshaller);
        WebServiceTemplate template = client.getWebServiceTemplate();
        template.setMessageSender(new WebServiceMessageSenderWithAuth());
        return client;
    }
}
