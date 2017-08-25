# spring-hibernate-rest-sample

##Registration REST API:

#### EndPoint : 

/register

#### Payload: 

{"name":"Test","emailId":"test@gmail.com","pincode": 274505}

-----------------------------------------------------------------------------------

##GetLogin REST API:

#### EndPoint : 

/getLogin?userId=<aUserId>

Where aUserId is the user id returned after registration.

-----------------------------------------------------------------------------------

##Login REST API:

#### EndPoint : 

/login?ticket=<ticket>

Where ticket is the authentication ticket sent to the user via email. 
Note: This ticket is valid for 10 minutes only. 

-----------------------------------------------------------------------------------
##Usage Notes:

> Use JDK 1.8
> Use Tomcat 8.x or equivalent container