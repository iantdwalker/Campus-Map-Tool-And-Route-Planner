package applicationpackage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

import net.java.plaf.windows.WindowsLookAndFeel;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.*;
//import com.jgoodies.looks.windows.WindowsLookAndFeel;
import de.muntjak.tinylookandfeel.*;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.pagosoft.plaf.PgsLookAndFeel;

/**
 * The main class: application entry point.
 */
public class Main
{
  private ApplicationGui          _applicationGui;
  private SplashScreen            _splashScreen    = new SplashScreen();
  private DisplaySplashScreen     _thread          = new DisplaySplashScreen();
  private BufferedImage           _imageIcon;
  private InputStream             _inputStream;
    
  /**
   * Constructor.
   * @throws java.lang.ClassNotFoundException
   */
  public Main() throws ClassNotFoundException
  {
    _thread.start();
    this.setupGui();
    
    try
    {
      Thread.currentThread().sleep(3000);
    }
    catch (Exception ex)
    {
      Error.showMessage(_applicationGui, "Error running the GUI " +
      "thread: \n" + ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
    }    
    
    //once splash screen disposed show the ApplicationGui
    _applicationGui.setVisible(true);    
  }
  
  /**
   * A class extending Thread to run the SplashScreen JFrame.
   */
  private class DisplaySplashScreen extends Thread
  {
    /**
     * Constructor.
     */
    private DisplaySplashScreen()
    {
    }
    
    /**
     * Runs the thread and displays the SplashScreen.
     */
    public void run()
    {
      Dimension screenSize  = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize   = _splashScreen.getSize();
    
      if (frameSize.height > screenSize.height)
      {
        frameSize.height = screenSize.height;
      }
    
      if (frameSize.width > screenSize.width)
      {
        frameSize.width = screenSize.width;
      }
    
      _splashScreen.setLocation((screenSize.width - frameSize.width) / 2,
      (screenSize.height - frameSize.height) / 2);
    
      _splashScreen.setVisible(true);
          
      try
      {
        sleep(3000);        
      }
      catch (Exception ex)
      {      
        Error.showMessage(_applicationGui, "Error running the SplashScreen " +
        "thread: \n" + ex.getMessage() + "\nReason:\n" + ex.toString());      
        ex.printStackTrace();
      }
      
      _splashScreen.dispose();      
    }
  }
  
  /**
   * Setup the GUI Frame: ApplicationGui (hidden).
   */
  private void setupGui()
  {
    try
    {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");      
      //UIManager.setLookAndFeel("net.java.plaf.windows.WindowsLookAndFeel");
      //UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
      //PlasticLookAndFeel.setPlasticTheme(new SkyBlue());
      //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
      //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
      //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
      //UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
      //UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
      //UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");      
    }    
    catch(Exception ex)
    {
      Error.showMessage(_applicationGui, "Error setting the look and feel: \n" +
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
    }
    
    _applicationGui       = new ApplicationGui();    
    Dimension screenSize  = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize   = _applicationGui.getSize();
        
    if (frameSize.height > screenSize.height)
    {
      frameSize.height = screenSize.height;
    }
    
    if (frameSize.width > screenSize.width)
    {
      frameSize.width = screenSize.width;
    }
    
    _applicationGui.setLocation((screenSize.width - frameSize.width) / 2,
    (screenSize.height - frameSize.height) / 2);
    
    _applicationGui.setDefaultCloseOperation(_applicationGui.DO_NOTHING_ON_CLOSE);
    _applicationGui.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          //determine if any user placed nodes exist or not
          if (_applicationGui.getApplicationGuiUtilities().getNodeArray().size()
          >= 1)
          {
            _applicationGui.getApplicationGuiUtilities().setNodePlaced(true);
          }          
          else
          {
            _applicationGui.getApplicationGuiUtilities().setNodePlaced(false);
          }
    
          //if nodes exist offer the option to save current XML graph first
          if (_applicationGui.getApplicationGuiUtilities().getNodePlaced())
          {
            int optionChoice = JOptionPane.showConfirmDialog(_applicationGui,
            "Do you want to save the XML graph first?","Exit",
            JOptionPane.YES_NO_CANCEL_OPTION);
      
            if (optionChoice == 0)
            {
              _applicationGui.getApplicationGuiUtilities().saveAs();    
              System.exit(0);
            }            
            else if (optionChoice == 1)
            {
              System.exit(0);
            }            
          }    
          else
          {
            System.exit(0);
          }     
        }
      });    
      
      this.setUpImageIcon();
      _applicationGui.setVisible(false);
  }
  
  /**
   * Sets up and adds the image icon to the application frame.
   */
  private void setUpImageIcon()
  {
    try
      {
        _inputStream = this.getClass().getResourceAsStream
        ("/Resources/Icons/Mobile.JPG");
        _imageIcon   = ImageIO.read(_inputStream);
        _inputStream.close();    
        _applicationGui.setIconImage(_imageIcon);
      }      
      catch (Exception ex)
      {
        Error.showMessage(null, "Error setting the application's image icon: \n"
        + ex.getMessage() + "\nReason:\n" + ex.toString());
        ex.printStackTrace();
      }
  }

  /**
   * Entry Point.
   * @throws java.lang.ClassNotFoundException
   * @param args
   */
  public static void main(String[] args) throws ClassNotFoundException
  {
    new Main();
  }
}