Ian Walker - 209842
-------------------

PRCI305 - How to run software applications
------------------------------------------

This document explains each step required to run the project's software applications.


1) Java application - "Campus Map Tool and Route Planner"


-Ensure MS Access "University Database" has system DSN set up - name DSN "University"
-open "Java Application" folder, "Project1" folder, "deploy" folder, double click "Run.bat"
(JAR and XML must be within this folder)
-Alternatively load the project in JDev 10.1.2, make and run project

*Full user instructions can be found using menu option "Help", "User Help"*

---

2) Java applet - "Campus Route Planner"


-Included as part of web application build - can be accessed from home page by following link

---

3) Web application - "Plymouth University Web Application"


-Ensure MS Access "University Database" has system DSN set up - name DSN "University"
-Application has been developed using NetBeans 4.1 (JRE 5.0)
-To open in NetBeans 4.1 - "File", "Open Project", browse to "Web Application Model 2", double click,
select "ProjectWebApplication" and double click the button "Open Project Folder"
-Build and run project (right click project in tree, "Build Project" and "Run Project")

-Alternatively, this web application has been deployed on Martin Beck's server, accessible via:
	http://psqmachine.soc.plymouth.ac.uk/maptool

---

4) Mobile application - "Mobile Map and Database Search Application"


-Ensure MS Access "University Database" has system DSN set up - name DSN "University"
-Ensure J2ME Wireless Toolkit 2.2 is installed on target machine (application runs on emulator)
-Application has been developed using NetBeans 4.1
-To open in NetBeans 4.1 - "File", "Open Project", browse to "J2ME Midlet Study", double click,
select "MobileApplication" and double click the button "Open Project Folder"
-Ensure web application is deployed to server (Apache Tomcat) and that server is running
-Build and run project (click on Projects tab in tree and right click on the project, "Build Project" and "Run Project")

-Alternatively access the J2ME wireless toolkit program installation and select "Run MIDP Application"
	When prompted browse to the JAD location at "J2ME Midlet Study\MobileApplication\dist" and select it.

*Points to note*

-With regards to mobile application Java src files "DBMidlet" and "MapMidlet":

1) Both contain a hard-coded server location: private String url = "http://localhost:8084/ProjectWebApplication/MobileServlet";
that relates to the Apache Tomcat installation port 8084 of the test machine.  If running the application using localhost,
"8084" in the string above may require changing to match default Tomcat port of 8080 or whichever port localhost is running on.
 
2) If attempting to connect to Martin Beck's server, then the entire line: private String url = "http://localhost:8084/ProjectWebApplication/MobileServlet";
in both src files above will need changing to: private String url = "http://psqmachine.soc.plymouth.ac.uk/maptool/MobileServlet";
However, this is only accessable from inside the university network and still requires a valid installation of the J2ME WTK 2.2.

If either approach 1) or 2) is taken and Java src files are changed then the project can be re-built (re-compiled) in NetBeans 4.1:
click on Projects tab in tree and right click on the project, "Build Project" and "Run Project"

---

Any problems or questions please email ian.walker100@students.plymouth.ac.uk





