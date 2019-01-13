package university2;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import java.util.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class MobileServlet extends HttpServlet
{
    public void init()
    {   
    }
    
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        HttpSession session = request.getSession();
        String message = "";
        DataInputStream in = new DataInputStream((InputStream)request.getInputStream());
                
        String action = in.readUTF();
                
        /** Process map requests **********************************************/
        if (action.equals("map") == true)
        {
            String imagePath = this.getServletContext().getRealPath("/web_images");
            URL xmlPath = this.getServletContext().getResource("/xml/Map.xml");
            ProcessMobileImage pmi = new ProcessMobileImage(xmlPath);
            String subAction = in.readUTF();
            
            /** Process building list requests ********************************/
            if (subAction.equals("buildings") == true)
            {
                ArrayList buildings = new ArrayList();
                buildings = pmi.getBuildings();
                
                for (int x = 0; x < buildings.size(); x++)
                {
                    message = message + (String) buildings.get(x) + ",";
                }
                
                response.setContentType("text/plain");
                response.setContentLength(message.length());
                PrintWriter out = response.getWriter();
                out.println(message);
                in.close();
                out.close();
                out.flush();
            }
            
            /** Process route requests ****************************************/
            else if (subAction.equals("processRequest") == true)
            {
                try
                {
                    String startLocation = in.readUTF();
                    String goalLocation = in.readUTF();
                    String typeChoice = in.readUTF();
                    String width = in.readUTF();
                    String height = in.readUTF();
                                                          
                    if (typeChoice.equals("Fully Scaled") == true)
                    {
                        pmi.processRequest(startLocation,goalLocation,width,height,typeChoice);
                        
                        /** Get scaled image **/
                        BufferedImage scaleMap = pmi.getScaledImage();        
                        String fileName = imagePath + "\\ScaleMap" + session.getId() + ".PNG";
                        File scaleMapImageFile = new File(fileName);
                        
                        /** Write image to file on server **/
                        ImageThread t = new ImageThread(scaleMapImageFile, scaleMap);                    
                        t.run();                        
                                                
                        /** Read image into byte array and send to client **/
                        FileInputStream inStream = new FileInputStream(scaleMapImageFile);
                        byte[] data = new byte[scaleMap.getWidth(null) * scaleMap.getHeight(null)];
                        inStream.read(data, 0, data.length);
                        inStream.close();
                        scaleMapImageFile.delete();                        

                        response.setContentType("image/png");
                        response.setContentLength(data.length);
                        OutputStream out = response.getOutputStream();
                        out.write(data, 0, data.length);
                        in.close();
                        out.close();
                        out.flush();                                   
                    }
                    
                    else if (typeChoice.equals("Large Scrollable") == true)
                    {
                        pmi.processRequest(startLocation,goalLocation,width,height,typeChoice);
                        
                        /** Get scaled image **/
                        BufferedImage scaleMap = pmi.getScaledImage();
                        String fileName = imagePath + "\\ScaleMap" + session.getId() + ".PNG";
                        File scaleMapImageFile = new File(fileName);
                        
                        /** Write image to file on server **/
                        ImageThread t = new ImageThread(scaleMapImageFile, scaleMap);                    
                        t.run();

                        /** Read image into byte array and send to client **/
                        FileInputStream inStream = new FileInputStream(scaleMapImageFile);
                        byte[] data = new byte[scaleMap.getWidth(null) * scaleMap.getHeight(null)];
                        inStream.read(data, 0, data.length);
                        inStream.close();
                        scaleMapImageFile.delete();                        

                        response.setContentType("image/png");
                        response.setContentLength(data.length);
                        OutputStream out = response.getOutputStream();
                        out.write(data, 0, data.length);
                        in.close();
                        out.close();
                        out.flush();                                  
                    }
                    
                    else if (typeChoice.equals("Sub-Section Zoom") == true)
                    {
                        pmi.processRequest(startLocation,goalLocation,width,height,typeChoice);
                        
                        /** Get scaled image **/
                        BufferedImage scaleMap = pmi.getScaledImage();        
                        String fileName = imagePath + "\\ScaleMap" + session.getId() + ".PNG";
                        File scaleMapImageFile = new File(fileName);
                        
                        /** Write image to file on server **/
                        ImageThread t = new ImageThread(scaleMapImageFile, scaleMap);                    
                        t.run();

                        /** Read image into byte array and send to client **/
                        FileInputStream inStream = new FileInputStream(scaleMapImageFile);
                        byte[] data = new byte[scaleMap.getWidth(null) * scaleMap.getHeight(null)];
                        inStream.read(data, 0, data.length);
                        inStream.close();
                        scaleMapImageFile.delete();                        

                        response.setContentType("image/png");
                        response.setContentLength(data.length);
                        OutputStream out = response.getOutputStream();
                        out.write(data, 0, data.length);
                        in.close();
                        out.close();
                        out.flush();
                    }
                }
                
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                catch (Error e)
                {
                    e.printStackTrace();
                }
            }                                   
        }   
    }
    
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }      
    
    public void destroy()
    {        
    }
}

class ImageThread extends Thread
{
    File file;
    BufferedImage bi;
    
    public ImageThread(File newFileIn, BufferedImage newBiIn)
    {
        file = newFileIn;
        bi = newBiIn;
    }
    
    public void run()
    {
        try
        {
            ImageIO.write(bi, "png", file);
            //sleep(4000);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}