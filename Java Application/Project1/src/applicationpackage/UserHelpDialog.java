package applicationpackage;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;

/**
 * A class that displays a dialog of user instructions.
 */
public class UserHelpDialog extends JDialog
{
  //GUI attributes
  private JLabel          jLabel1           = new JLabel();
  private JButton         jButton1          = new JButton();
  private JTextPane       jTextPane1        = new JTextPane();
  private JScrollPane     dialogJScrollPane = new JScrollPane(jTextPane1);
  private JScrollBar      vbar              = new JScrollBar(JScrollBar.VERTICAL);
  private JPanel          jPanel1           = new JPanel();
  
  //global attributes
  private Component       _parent;

  /**
   * Constructor.
   * @param modal
   * @param title
   * @param parent
   */
  public UserHelpDialog(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    _parent = parent;
      
    try
    {  
      this.jbInit();
    }      
    catch(Exception ex)
    {
      Error.showMessage(_parent, "Error creating UserHelpDialog: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
    }
  }

  /**
   * Initialise the UserHelpDialog.
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(628, 524));
    this.getContentPane().setLayout(null);
    jLabel1.setText("Campus Map Tool and Route Planner");
    jLabel1.setBounds(new Rectangle(5, 5, 610, 50));
    jLabel1.setFont(new Font("Tahoma", 1, 12));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jButton1.setText("Close");
    jButton1.setBounds(new Rectangle(255, 440, 120, 45));
    jButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
        {
          btnCloseClicked(e);
        }
    });
    jTextPane1.setBounds(new Rectangle(5, 45, 610, 385));
    
    String            text              = "";
    boolean           done              = false;
    InputStream       inputStream       = this.getClass().getResourceAsStream
    ("/Resources/UserHelp.txt");
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader    bufferedReader    = new BufferedReader(inputStreamReader);   
    
    while (!done)
    {
      String current = bufferedReader.readLine();
      
      if (current == null)
      {
        done = true;
      }
      else
      {
        text = text + current;
        text = text + "\n";
      }            
    }
    
    bufferedReader.close();
    inputStreamReader.close();
    inputStream.close();    
    
    //remove the additional "\n" applied to the end of the text
    text = text.substring(0, text.length()-1);   
    
    jTextPane1.setText(text);
    jTextPane1.setEditable(false);
    jTextPane1.setFont(new Font("Arial", 0, 12));
    int x = jTextPane1.getText().indexOf("'Campus Map Tool and Route Planner'" +
    " allows you");

    if (x != -1)
    {
      jTextPane1.setCaretPosition(x);
    }

    dialogJScrollPane.setVerticalScrollBar(vbar);
    vbar.addAdjustmentListener(new ScrollBarAdjustmentListener());
    jPanel1.setBounds(new Rectangle(5, 45, 610, 390));
    jPanel1.setLayout(new BorderLayout());
    jPanel1.add(dialogJScrollPane, BorderLayout.CENTER);

    this.getContentPane().add(jPanel1, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel1, null);
  }

  /**
   * Dispose the dialog on button close clicked.
   * @param e
   */
  private void btnCloseClicked(ActionEvent e)
  {
    this.dispose();
  }
}