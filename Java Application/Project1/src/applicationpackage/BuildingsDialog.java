package applicationpackage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.Rectangle;

/**
 * Dialog for selecting a building name
 */
public class BuildingsDialog extends JDialog
  {
    private JLabel      jLabel1           = new JLabel();
    private JButton     jButton1          = new JButton();
    private JButton     jButton2          = new JButton();
    private JLabel      jLabel2           = new JLabel();
    private JTextField  jTextField1       = new JTextField();
    private JTextField  jTextField2       = new JTextField();
    private JButton     jButton3          = new JButton();
    private String      _buildingChoice;
    private int         _btnChoice;    

  /**
   * Constructor.
   * @param modal
   * @param title
   * @param parent
   */
    public BuildingsDialog(Frame parent, String title, boolean modal)
    {
      super(parent, title, modal);
      
      try
      {     
        this.jbInit();
      }      
      catch(Exception ex)
      {
        Error.showMessage(parent, "Error displaying Buildings Dialog: \n" + 
        ex.getMessage() + "\nReason:\n" + ex.toString());
        ex.printStackTrace();
      }
    }

  /**
   * Initialise the BuildingsDialog.
   * @throws java.lang.Exception
   */
    private void jbInit() throws Exception
    {
      this.setSize(new Dimension(294, 282));
      this.getContentPane().setLayout(null);
      this.setTitle("Name Node");
      
      jLabel1.setText("Input a new node name:");
      jLabel1.setBounds(new Rectangle(10, 60, 265, 50));
      jLabel1.setFont(new Font("Tahoma", 1, 12));
      jButton1.setText("OK");
      jButton1.setBounds(new Rectangle(30, 190, 75, 45));
      jButton1.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnOKClicked(e);
          }
        });
      jButton2.setText("Cancel");
      jButton2.setBounds(new Rectangle(180, 190, 75, 45));
      jButton2.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnCancelClicked(e);
          }
        });
      jLabel2.setText("The current node name is:");
      jLabel2.setBounds(new Rectangle(10, 0, 265, 50));
      jLabel2.setFont(new Font("Tahoma", 1, 12));
      jTextField1.setBounds(new Rectangle(10, 40, 265, 25));
      jTextField1.setEditable(false);
      jTextField2.setBounds(new Rectangle(10, 100, 265, 25));
      jTextField2.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          jTextField2_keyPressed(e);
        }
      });
      jButton3.setText("This node is a choice point");
      jButton3.setBounds(new Rectangle(10, 145, 265, 30));
      jButton3.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnCPClicked(e);
          }
        });
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jTextField2, null);
      
      //set the cursor in the jTextField2 (new name)
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          jTextField2.requestFocus();
        }
      });
    }

  /**
   * Event on button 'OK' clicked.
   * @param e
   */
    private void btnOKClicked(ActionEvent e)
    {
      this.processNewName();
    }
    
  /**
   * Event on button 'Cancel' clicked.
   * @param e
   */
    private void btnCancelClicked(ActionEvent e)
    {
      _btnChoice = 2;
      this.dispose();
    }
    
  /**
   * Event on button 'This node is a choice point' clicked.
   * @param e
   */
    private void btnCPClicked(ActionEvent e)
    {
      jTextField2.setText("Choice Point");
    }

  /**
   * Event on keyboard key return/enter pressed.
   * @param e
   */
    private void jTextField2_keyPressed(KeyEvent e)
    {
      if (e.getKeyCode() == 10)
      {
        this.processNewName();
      }
    }  
    
   /**
   * Private method to handle button OK clicked or enter/return pressed.
   */
    private void processNewName()
    {
      if (!jTextField2.getText().equals(""))
      {
        _buildingChoice = jTextField2.getText();
        _btnChoice      = 1;
        this.dispose();
      }      
      else
      {
        Error.showMessage(this, "A node name cannot be empty.");
      }
    }

  /**
   * Set the building choice String if it's changed by another component.
   * @param buildingChoice
   */
    public void setBuildingChoice(String buildingChoice)
    {
      _buildingChoice = buildingChoice;
    }
    
  /**
   * Returns the building choice String for use in parent components.
   * @return String
   */
    public String getBuildingChoice()
    {
      return _buildingChoice;
    }

  /**
   * Returns the button clicked for use in parent components.
   * @return 
   */
    public int getBtnChoice()
    {
      return _btnChoice;
    }
    
  /**
   * Set the node name textbox with a selected node's name.
   * @param name
   */
    public void setNodeNameText(String name)
    {
      jTextField1.setText(name);
    }
  }