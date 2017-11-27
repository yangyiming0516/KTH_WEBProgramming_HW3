/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW3.client.query;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:QueryResource [Query]<br>
 * USAGE:
 * <pre>
 *        ClientQuery client = new ClientQuery();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author YY_More
 */
public class ClientQuery {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/TicketREST/webresources";

    public ClientQuery() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Query");
    }

    public String getRoute(String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (from != null) {
            resource = resource.queryParam("from", from);
        }
        if (to != null) {
            resource = resource.queryParam("to", to);
        }
        resource = resource.path("Route");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String getData(int number, String first, String second, String date) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (date != null) {
            resource = resource.queryParam("date", date);
        }
        if (Integer.toString(number) != null) {
            resource = resource.queryParam("number", number);
        }
        if (first != null) {
            resource = resource.queryParam("first", first);
        }
        if (second != null) {
            resource = resource.queryParam("second", second);
        }
        resource = resource.path("Data");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}
