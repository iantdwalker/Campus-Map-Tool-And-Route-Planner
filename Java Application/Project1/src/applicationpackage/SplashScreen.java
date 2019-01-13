package applicationpackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A class that extends JFrame and acts as a splash screen.
 */
public class SplashScreen extends JFrame 
{
  private SplashPanel _splashPanel = new SplashPanel();
    
  /**
   * Constructor.
   */
  public SplashScreen()
  {
    try
    {
      this.jbInit();
      this.setSize(new Dimension(400, 400));
      this.getContentPane().add(_splashPanel, BorderLayout.CENTER);
    }
    catch (Exception ex)
    {
      Error.showMessage(this, "Error displaying the application's splash " +
      "screen: \n" + ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
    }
  }

  /**
   * Initialise the JFrame.
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(new BorderLayout());
    this.setUndecorated(true);   
    this.setTitle("Campus Map Tool and Route Planner");
  }
  
  /**
   * An internal class that extends JPanel and paints the image.
   */
  private class SplashPanel extends JPanel
  {
    private BufferedImage   splashImage;
    private InputStream     inputStream;    
        
    /**
     * Constructor.
     */
    private SplashPanel()
    {
      this.setUpImage();
      this.DisplayImage();
    }
    
    /**
     * Initialises a BufferedImage via an InputStream and ImageIO.
     */
    private void setUpImage()
    {
      try
      {
        inputStream = this.getClass().getResourceAsStream
        ("/Resources/SplashScreen.jpg");
        splashImage = ImageIO.read(inputStream);
        inputStream.close();                
      }      
      catch (Exception ex)
      {
        Error.showMessage(null, "Error reading the application's splash " + 
        "screen image: \n" + ex.getMessage() + "\nReason:\n" + ex.toString());      
        ex.printStackTrace();
      }     
    }
    
    /**
     * Calls repaint() in order to paint the image to the JPanel.
     */
    private void DisplayImage()
    {
      this.setPreferredSize(new Dimension(400, 400));  
      this.repaint();
      this.revalidate();
    }
    
    /**
     * Paint method for this JPanel.
     * @param g
     */
    protected void paintComponent(Graphics g)
    {
      //paint background
      super.paintComponent(g);
      
      Graphics2D g2 = (Graphics2D) g;
            
      //check if image is null first and draw if not null
      if (splashImage != null)
      {
        g2.drawImage(splashImage, 0, 0, 400, 400, this);
      }
    }
  }
}