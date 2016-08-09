package Core.Nipr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import org.springframework.ws.client.core.WebServiceTemplate;
import java.io.IOException;
import java.net.HttpURLConnection;

class WebServiceMessageSenderWithAuth extends HttpUrlConnectionMessageSender {

    @Override
    protected void prepareConnection(HttpURLConnection connection)
            throws IOException {

        connection.setRequestProperty("Authorization", "Basic c2h1Y2h1bnplbmU6MVNhbGVzZm9yY2UhIQ==");

        super.prepareConnection(connection);
    }
}
