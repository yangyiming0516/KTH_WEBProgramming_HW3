/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW3.client.booking;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:BookingResource [Booking]<br>
 * USAGE:
 * <pre>
 *        ClientBooking client = new ClientBooking();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author YY_More
 */
public class ClientBooking {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/TicketREST/webresources";

    public ClientBooking() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Booking");
    }

    public String booking(int number,String first,String second,String date,String card) throws ClientErrorException {
        WebTarget resource = webTarget.path("booking");
        if (Integer.toString(number) != null) {
            resource = resource.queryParam("number", number);
        }
        if (first != null) {
            resource = resource.queryParam("first", first);
        }
        if (second != null) {
            resource = resource.queryParam("second", second);
        }
        if (date != null) {
            resource = resource.queryParam("date", date);
        }
        if (card != null) {
            resource = resource.queryParam("card", card);
        }
        return resource.request().post(null, String.class);
    }

    public String issue(String bookingnumber) throws ClientErrorException {
        WebTarget resource = webTarget.path("issue");
        if (bookingnumber != null) {
            resource = resource.queryParam("bookingnumber", bookingnumber);
        }
        return resource.request().post(null, String.class);
    }

    public void close() {
        client.close();
    }
    
}
