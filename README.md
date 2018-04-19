# KTH_WEBProgramming_HW3

Implemented in Netbeans + GlassFish + Jersey 2.5.1
The server program is in TicketRESTserver folder and client is in TicketRESTclient.  Remember that Jdom.jar should be included in the server program. Deploy the service first, sample data xml files should be put in {glassfish_server}/glassfish/domains/domain1/config


All services in HW3 are rewritten in RESTful style. New function is added in order to use all of GET/POST/PUT/DELETE methods.

Services:
../User  GET
	Login service

../User  Delete
	Delete account

../User  PUT
	Change password or create a new account

../Booking/booking		POST
	Book for a trip, get a booking reference

../Booking/issue	POST
	Issue ticket with valid booking reference and get ticket number

../Query/Route	GET
	Search for possible route from an original to destination

../Query/Data		GET
	Search for valid seat and price for a route in certain date

All services above is using QueryParam to pass parameters for the operations
The use of client is nothing different from HW2.
