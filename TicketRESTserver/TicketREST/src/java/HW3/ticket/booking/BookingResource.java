/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW3.ticket.booking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
@Path("Booking")
public class BookingResource {
    
    int cnt=13579;
    int cnt2 = 900661288;
    static{
        File file=new File("Booking.xml");
        if (!file.exists())
        try{
            Element root = new Element("Bookings");
            Document doc = new Document(root);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileOutputStream("Bookings.xml"));
        }catch(IOException e){
        }
    }

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookingResource
     */
    public BookingResource() {
    }

    /**
     * Retrieves representation of an instance of HW3.ticket.booking.BookingResource
     * @param number
     * @param card
     * @param first
     * @param date
     * @param second
     * @return an instance of java.lang.String
     */
    @POST
    @Path("booking")
    @Produces(MediaType.TEXT_PLAIN)
    public String booking(@QueryParam("number") int number,@QueryParam("first") String first,@QueryParam("second") String second,@QueryParam("date") String date,@QueryParam("card") String card) throws JDOMException {
        String Rd = (new SecureRandom()).toString();
        cnt++;
        try{
            File file=new File("Bookings.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(file);
            Element root = document.getRootElement();
            Element newbooking = new Element("Booking");
            Element newNo = new Element("BookingNo");
            newNo.setText(String.valueOf(cnt));
            newbooking.addContent(newNo);
            Element trip = new Element("trips");
            trip.setText(String.valueOf(number));
            newbooking.addContent(trip);
            Element Card = new Element("Card");
            Card.setText(card);
            newbooking.addContent(Card);
            
            Element newFirstNo = new Element("FirstNo");
            newFirstNo.setText(first);
            newbooking.addContent(newFirstNo);
            cnt2++;
            Element newFirstTk = new Element ("FirstTk");
            newFirstTk.setText(String.valueOf(cnt2));
            newbooking.addContent(newFirstTk);
            
            if (number==2){
                Element newSecondNo = new Element("SecondNo");
                newSecondNo.setText(second);
                newbooking.addContent(newSecondNo);
                cnt2++;
                Element newSecondTk = new Element ("SecondTk");
                newSecondTk.setText(String.valueOf(cnt2));
                newbooking.addContent(newSecondTk);
            }
                   
            root.addContent(newbooking);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileOutputStream("Bookings.xml"));
            
            file=new File("Records.xml");
            document = saxBuilder.build(file);
            root = document.getRootElement();
            List<Element> records = root.getChildren("Record");
            
            for (int i=0;i<records.size();i++)
                if (records.get(i).getChild("No").getText().equals(first))
                {
                    records.get(i).getChild("Remain-"+ date).setText(String.valueOf(Integer.parseInt(records.get(i).getChild("Remain-"+ date).getText())-1));
                }
            if (number==2){
                for (int i=0;i<records.size();i++)
                    if (records.get(i).getChild("No").getText().equals(second))
                    {
                        records.get(i).getChild("Remain-"+ date).setText(String.valueOf(Integer.parseInt(records.get(i).getChild("Remain-"+ date).getText())-1));
                    }
                
            }
            xmlOutput.output(document, new FileOutputStream("Records.xml"));
            
        }catch(IOException e){
         System.err.print("error");
        }
        
        return String.valueOf(cnt);
    }

    @POST
    @Path("issue")
    @Produces(MediaType.TEXT_PLAIN)
    public String issue(@QueryParam("bookingnumber") String bookingnumber) throws JDOMException {
        String output = new String("");
        try{
            File file=new File("Bookings.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(file);
            Element root = document.getRootElement();
            List<Element> bookings = root.getChildren("Booking");
            for (int i=0;i<bookings.size();i++){
                if (bookings.get(i).getChild("BookingNo").getText().equals(bookingnumber)){
                    int trips = Integer.parseInt(bookings.get(i).getChild("trips").getText());
                    output = output.concat(bookings.get(i).getChild("FirstNo").getText());
                    output = output.concat(" "+bookings.get(i).getChild("FirstTk").getText());
                    
                    if (trips>1){
                        output = output.concat(" "+bookings.get(i).getChild("SecondNo").getText());
                        output = output.concat(" "+bookings.get(i).getChild("SecondTk").getText());
                    }
                }
            }
            }catch(IOException e){
         System.err.print("error");
        }
        return output;
    }
    
}
