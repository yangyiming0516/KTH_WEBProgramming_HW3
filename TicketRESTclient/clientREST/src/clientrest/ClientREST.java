/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrest;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author YY_More
 */
public class ClientREST {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
        
        throws JDOMException_Exception {
        HW3.client.user.ClientUser User= new HW3.client.user.ClientUser();
        HW3.client.booking.ClientBooking Booking= new HW3.client.booking.ClientBooking();
        HW3.client.query.ClientQuery Query= new HW3.client.query.ClientQuery();
        // TODO code application logic here
        String username,password,session,repeat;
        boolean success = false;
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to ticket booking system");
        do{
        success = false;
        System.out.println("Please sign in first to use our service");
        System.out.println("Do you want to login(1) or register(2) or change your password(3) or delete your account(4)");
        String tmp = sc.nextLine();
        if (tmp.equals("2")){
            System.out.println("Please enter your Username:");
            username=sc.nextLine();
            System.out.println("Please enter your Password:");
            password=sc.nextLine();
            System.out.println("Please repeat your Password:");
            repeat=sc.nextLine();
            tmp = User.putUser(username,password,repeat);
            if (tmp.equals("FAIL")){
                System.out.println("Sorry the Username is occupied, Please try again");
            }else{
                System.out.println("Register Succeed!");
            }
        }
        else
            if (tmp.equals("1"))
            {
            System.out.println("Please enter your Username:");
            username=sc.nextLine();
            System.out.println("Please enter your Password:");
            password=sc.nextLine();
            session = User.getUser(username,password);
            if (session.equals("FAIL")){
                System.out.println("Input Username/Password not valid, please check!");
            }else{
                success=true;
            }
            }
            else
            if (tmp.equals("4"))
            {
                System.out.println("Please enter your Username:");
                username=sc.nextLine();
                System.out.println("Please enter your Password:");
                password=sc.nextLine();
                tmp=User.deleteUser(password, username);
                if (tmp.equals("FAIL")){
                    System.out.println("Input Username/Password not valid, please check!");
                }
                else System.out.println("Your account has been deleted");
            }
            else {
                System.out.println("Please enter your Username:");
                username=sc.nextLine();
                System.out.println("Please enter your Password:");
                password=sc.nextLine();
                System.out.println("Please enter your new Password:");
                repeat=sc.nextLine();
                tmp = User.putUser(username,password,repeat);
                if (tmp.equals("FAIL")){
                    System.out.println("Sorry something is wrong, Please try again");
                }else{
                    System.out.println("Your Password has been changed!");
                }
            }
        }while (!success);
        
        while (true){
        System.out.println("Choose: search for flights (1)  or  check for booking (2)  or  exit(3)");
        String choose = sc.nextLine();
        if (choose.equals("3")) break;
        String From = null;
        String To = null;
        if (choose.equals("1")){
            System.out.println("From:");
            From = sc.nextLine();
            System.out.println("To:");
            To = sc.nextLine();
            String query = Query.getRoute(From, To);
            int cnt=0;
            Scanner reader=new Scanner(query);
            HashMap<Integer,String> map = new HashMap<>();
            while (reader.hasNext()){
                cnt++;
                System.out.println("\nRoute "+ cnt+":");
                String flag = reader.next();
                if (flag.equals("?")){
                    String tmp1 = reader.next();
                    map.put(cnt,"Single " +tmp1);
                    System.out.println("Flight:   "+tmp1);
                    System.out.println("From:   "+reader.next());
                    System.out.println("To:   "+reader.next());
                    System.out.println("Departure at:   "+reader.next());
                    System.out.println("Land at:   "+reader.next());
                    reader.next();
                }else 
                if (flag.equals("!")){
                    reader.next();
                    String tmp1 = reader.next();
                    System.out.println("Flight:   "+tmp1);
                    System.out.println("From:   "+reader.next());
                    System.out.println("To:   "+reader.next());
                    System.out.println("Departure at:   "+reader.next());
                    System.out.println("Land at:   "+reader.next());
                    reader.next();
                    reader.next();
                    String tmp2 = reader.next();
                    System.out.println("Flight:   "+tmp2);
                    System.out.println("From:   "+reader.next());
                    System.out.println("To:   "+reader.next());
                    System.out.println("Departure at:   "+reader.next());
                    System.out.println("Land at:   "+reader.next());
                    reader.next();
                    map.put(cnt,"double "+tmp1+" "+tmp2);
                    reader.next();
                }
            }
            System.out.println(" ");
            System.out.println("Which one do you prefer? input NO to leave");
            String flag = sc.nextLine();
            if (flag.equals("NO")) continue;
            int choice = Integer.parseInt(flag);
            System.out.println("What date do you want?");
            String date = sc.nextLine();
            reader=new Scanner(map.get(choice));
            String FirstFlight=null;
            String SecondFlight=null;
            int trips=1;
            if (reader.next().equals("Single")){
                FirstFlight = reader.next();
            }
            else{
                FirstFlight = reader.next();
                SecondFlight = reader.next();
                trips=2;
            }
            
            String data= Query.getData(trips,FirstFlight,SecondFlight,date);    
            reader=new Scanner(data);
            System.out.println("Your trip costs "+reader.next()+ " in total.");
            System.out.println(reader.next()+ " seats is still available.");
            System.out.println("Please input your credit card number for booking");
            String cardNumber = sc.nextLine();
            String ref = Booking.booking(trips,FirstFlight,SecondFlight,date,cardNumber);
            while (true){
                System.out.println("Thanks for your booking! Your booking reference is "+ref);
                System.out.println("PLEASE REMEMBER YOUR REFERENCE!");
                System.out.println("Input YES to leave");
                flag = sc.nextLine();
                if (flag.equals("YES")) break;
            }
            
            
        }
        if (choose.equals("2")){
            System.out.println("PLEASE input your BOOKING REFERENCE:");
            String ref=sc.nextLine();
            String data = Booking.issue(ref);
            Scanner reader = new Scanner(data);
            if (reader.hasNext()){
                System.out.println("Your first flight is "+reader.next());
                System.out.println("You're now checked in. The ticket number is: "+reader.next());
            }
            if (reader.hasNext()){
                System.out.println("Your second flight is "+reader.next());
                System.out.println("You're now checked in. The ticket number is: "+reader.next());
            }
            System.out.println("Thank you, have a nice trip!");
        }
        }
        
    }

    private static class JDOMException_Exception extends Exception {

        public JDOMException_Exception() {
        }
    }


    
}
