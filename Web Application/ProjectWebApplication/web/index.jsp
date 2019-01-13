<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="true" %>

<html>
  <head>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"></meta>
    <title>University Web Application</title>
    <table width="100%" border="0" bordercolor="white">
    <tr>
        <td align="left"><img src="web_images/logo.gif"></td>
        <td align="center" style="font-family:century; font-size:150%; color:blue">--- University Web Application Home ---</td>
        <td align="right"><img src="web_images/logo.gif"></td>
    </tr>
    </table>
  </head>
  <body bgcolor="white">
  <hr color="blue">
  <BR>
  <p align="center" style="font-family:arial; font-size:90%; color:black">
    Welcome to the Plymouth University Web Application!
  </p>
  <p align="justify" style="font-family:arial; font-size:90%; color:black">
    This website has been created for people who require information about Plymouth University's campus routes and building locations,
    or those that that wish to retrieve information about staff, rooms, schools and faculties.  Users can find routes between buildings
    using the "CAMPUS ROUTE PLANNER" link to the Applet below or search the database using the "DATABASE SEARCH" link also below. 
    Assigned administrators can log in to update the database via the "Log In" link above.
  </p>
  <BR>  
  <table width="100%" border="0" bordercolor="white">
    <tr>
        <td align="center"><a href="RoutePlanner.html">CAMPUS ROUTE PLANNER</a><BR><BR><a href="RoutePlanner.html"><img src="web_images/map.JPG" height="150" width="150"></a></td>
    </tr>
    <tr>
        <td align="center">The campus map route planner allows you to search for building to building routes that will be
        displayed on a picture of the campus.  The map is zoomable using the left (zoom in) and right (zoom out)
        mouse buttons.</td>       
    </tr>   
  </table>
  <p align="center" style="font-family:arial; font-size:90%; color:black">
    Access using a mobile device!
  </p>
  <table width="100%" border="0" bordercolor="white">
    <tr>
        <td align="center"><img src="web_images/map2.JPG" height="150" width="150"></td>
    </tr>
  </table>
  <p align="justify" style="font-family:arial; font-size:90%; color:black">
    A mobile version of this web application can also be accessed on Java-enabled devices - you can try the application for
    yourself by <a href="midlet">downloding the Java JAD/JAR files here</a> or you can 
    <a href="images">view images of the mobile application running on an emulator here</a>.
  </p>
  </body>
</html>