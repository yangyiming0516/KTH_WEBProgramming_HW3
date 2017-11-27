/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW3.client.user;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:UserResource [User]<br>
 * USAGE:
 * <pre>
 *        ClientUser client = new ClientUser();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author YY_More
 */
public class ClientUser {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/TicketREST/webresources";

    public ClientUser() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("User");
    }

    public String putUser(String username, String password, String repeat) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        if (username != null) {
            resource = resource.queryParam("username", username);
        }
        if (repeat != null) {
            resource = resource.queryParam("repeat", repeat);
        }
        Response response = resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).put(Entity.text(""));
        return response.readEntity(String.class);
    }

    public String getUser(String username,String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        if (username != null) {
            resource = resource.queryParam("username", username);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String deleteUser(String username,String password) throws ClientErrorException {
         WebTarget resource = webTarget;
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        if (username != null) {
            resource = resource.queryParam("username", username);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).delete(String.class);
    }

    public void close() {
        client.close();
    }
    
}
