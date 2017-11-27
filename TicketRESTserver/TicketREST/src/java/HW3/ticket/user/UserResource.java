/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW3.ticket.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * REST Web Service
 *
 * @author YY_More
 */
@Path("User")
public class UserResource {
    
    static{
        File file=new File("Users.xml");
        if (!file.exists())
        try{
            Element root = new Element("Users");
            Document doc = new Document(root);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileOutputStream("Users.xml"));
        }catch(IOException e){
        }
    }

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    /**
     * Retrieves representation of an instance of HW3.ticket.user.UserResource
     * @param Username
     * @param Password
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@QueryParam("username") String Username, @QueryParam("password") String Password) throws JDOMException {
        //TODO return proper representation object
       try{
            File file=new File("Users.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(file);
            Element root = document.getRootElement();
            List<Element> users = root.getChildren("User");
            for (int i=0;i<users.size();i++)
                if (users.get(i).getChild("Username").getText().equals(Username) && users.get(i).getChild("Password").getText().equals(Password))
                {
                    String session = (new SecureRandom()).toString();
                    users.get(i).getChild("Session").setText(session);
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(document, new FileOutputStream("Users.xml"));
                    return session;
                }
        }catch(IOException e){
         e.printStackTrace();
        }	
        return "FAIL";
    }


    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putUser(@QueryParam("username") String Username, @QueryParam("password") String Password, @QueryParam("repeat") String Repeat) throws JDOMException {
        
        try{
        File file=new File("Users.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        
        Element root = document.getRootElement();
        List<Element> users = root.getChildren("User");
        for (int i=0;i<users.size();i++)
            if (users.get(i).getChildren("Username").get(0).getText().equals(Username)){
                if (!users.get(i).getChildren("Password").get(0).getText().equals(Password))
                return "FAIL";
                else
                {
                    users.get(i).getChildren("Password").get(0).setText(Repeat);
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(document, new FileOutputStream("Users.xml"));
                    return "SUCCEED"; 
                }
            }
        if (!Password.equals(Repeat)) return "Different";
        Element newUser = new Element("User");
        Element username = new Element("Username");
        username.setText(Username);
        Element password = new Element("Password");
        password.setText(Password);
        Element session = new Element("Session");
        session.setText("NULL");
        newUser.addContent(username);
        newUser.addContent(password);
        newUser.addContent(session);
        
        
        document.getRootElement().addContent(newUser);
        
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(document, new FileOutputStream("Users.xml"));
        }catch(IOException e){
         e.printStackTrace();
        }		
        
        return "SUCCEED";
        
    }
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@QueryParam("username") String Username, @QueryParam("password") String Password) throws JDOMException {
        try{
            File file=new File("Users.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(file);
            Element root = document.getRootElement();
            List<Element> users = root.getChildren("User");
            for (int i=0;i<users.size();i++)
                if (users.get(i).getChild("Username").getText().equals(Username) && users.get(i).getChild("Password").getText().equals(Password))
                {
                    root.removeContent(users.get(i));
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(document, new FileOutputStream("Users.xml"));
                    return "TRUE";
                }
        }catch(IOException e){
         e.printStackTrace();
        }	
        return "FAIL";
    }
}
