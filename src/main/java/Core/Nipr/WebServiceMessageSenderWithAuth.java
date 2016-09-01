package core.nipr;

import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import java.io.IOException;
import java.net.HttpURLConnection;

class WebServiceMessageSenderWithAuth extends HttpUrlConnectionMessageSender {

    @Override
    protected void prepareConnection(HttpURLConnection connection)
            throws IOException {

        connection.setRequestProperty("Authorization", NiprClientConfiguration.getNiprAuthToken());

        super.prepareConnection(connection);
    }
}
