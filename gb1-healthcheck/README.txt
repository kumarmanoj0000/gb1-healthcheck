------------
Installation
------------

1. Setup the databaseby copying the MySQL driver (mysql-connector-java-5.0.5.jar)
   to the container library directory.
2. Setup the mail session:
   2.1 Copy the JavaMail API (mail-1.4.jar) and Activation API (activation-1.1.jar)
       JARs to the container library directory (in Tomcat 6.0: /lib).
   2.2 Setup a STMP server (such as JES - http://www.ericdaugherty.com/java/mailserver/)
       on the local machine.
       NB: If using JES, set the dns.server environment variable when starting the server.
