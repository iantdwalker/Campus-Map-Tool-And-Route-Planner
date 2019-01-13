package applicationpackage;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;

/**
 * A dialog class for selecting a route.
 */
public class GetRouteDialog extends JDialog
{
  //GUI attributes  
  private JLabel        jLabel1     = new JLabel();     
  private JLabel        jLabel2     = new JLabel();     
  private JButton       jButton1    = new JButton();    
  private JButton       jButton2    = new JButton();    
  private Choice        jComboBox1  = new Choice();  
  private Choice        jComboBox2  = new Choice();
  private JLabel        jLabel3     = new JLabel();
  private JCheckBox     jCheckBox1  = new JCheckBox();
  private JLabel        jLabel4     = new JLabel();
  private JCheckBox     jCheckBox2  = new JCheckBox();
    
  //other attributes
  private List          _routeBuildingsList;
  private String        _routeStartChoiceText;
  private String        _routeGoalChoiceText;
  private int           _routeStartChoice;
  private int           _routeGoalChoice;
  private boolean       _routeRunDijkstra;
  private ArrayList     _routeNodeArray;    
  private Component     _parent;
  private boolean       _leaveOpen;
  private boolean       _displayFullGraph;
  private int           _startIndex;
  private int           _goalIndex;
        
  /**
   * Constructor.
   * @param modal
   * @param title
   * @param parent
   */
  public GetRouteDialog(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
      
    try
    {
      _routeBuildingsList = new List();
      _routeStartChoice   = -1;
      _routeGoalChoice    = -1;
      _routeRunDijkstra   = false;
      _routeNodeArray     = new ArrayList();    
      _parent             = parent;
      _leaveOpen          = false;
      _displayFullGraph   = false;
      _startIndex         = 0;
      _goalIndex          = 0;
      
      this.jbInit();
    }      
    catch(Exception ex)
    {
      Error.showMessage(parent, "Error initialising the GetRouteDialog: \n" +
      ex.getMessage() + "\nReason:\n" + ex.toString());
      ex.printStackTrace();
    }
  }

  /**
   * Initialise the GetRouteDialog.
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(294, 297));
    this.getContentPane().setLayout(null);
    this.setTitle("Get Route");       

    jLabel1.setText("Select a start location:");
    jLabel1.setBounds(new Rectangle(10, 10, 265, 25));
    jLabel1.setFont(new Font("Tahoma", 1, 12));
    jLabel2.setText("Select a goal location:");
    jLabel2.setBounds(new Rectangle(10, 85, 265, 25));
    jLabel2.setFont(new Font("Tahoma", 1, 12));
    jButton1.setText("OK");
    jButton1.setBounds(new Rectangle(30, 205, 75, 45));
    jButton1.setSize(new Dimension(75, 45));
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOkClicked(e);
        }
      });
    jButton2.setText("Cancel");
    jButton2.setBounds(new Rectangle(180, 205, 75, 45));
    jButton2.setSize(new Dimension(75, 45));
    jButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancelClicked(e);
        }
      });
    jComboBox1.setBounds(new Rectangle(10, 45, 265, 25));
    jComboBox2.setBounds(new Rectangle(10, 120, 265, 25));
    jLabel3.setText("Remain open when route displayed?");
    jLabel3.setBounds(new Rectangle(10, 155, 230, 25));
    jLabel3.setFont(new Font("Tahoma", 1, 12));
    jCheckBox1.setBounds(new Rectangle(240, 160, 20, 15));
    jLabel4.setText("Display full graph with route?");
    jLabel4.setBounds(new Rectangle(10, 175, 190, 25));
    jLabel4.setFont(new Font("Tahoma", 1, 12));
    jCheckBox2.setBounds(new Rectangle(240, 180, 20, 15));
    this.getContentPane().add(jCheckBox2, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jCheckBox1, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jComboBox1, null);
    this.getContentPane().add(jComboBox2, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
  }

  /**
   * Event on button OK clicked.
   * @param e
   */
  private void btnOkClicked(ActionEvent e)
  {
    _routeStartChoiceText = jComboBox1.getSelectedItem();
    _routeGoalChoiceText  = jComboBox2.getSelectedItem();
    _routeStartChoice     = -1;
    _routeGoalChoice      = -1;

    if (_routeStartChoiceText.equals(_routeGoalChoiceText))
    {
      Error.showMessage(this, "Start and goal locations must not" +
      " be the same.");
    }
    else
    {
      for (int x = 0; x < _routeNodeArray.size(); x++)
      {
        Node TempNode = (Node) _routeNodeArray.get(x);
  
        if (_routeStartChoiceText.equals(TempNode.getName()))
        {
          _routeStartChoice = TempNode.getNumber();
        }
  
        if (_routeGoalChoiceText.equals(TempNode.getName()))
        {
          _routeGoalChoice = TempNode.getNumber();
        }          
      }
        
      if (_routeStartChoice == -1)
      {
        Error.showMessage(this, "The selected start location has "
        + "not been placed or named in the graph.");
      }

      if (_routeGoalChoice == -1)
      {
        Error.showMessage(this, "The selected goal location has "
        + "not been placed or named in the graph.");
      }
    }     

    if (_routeStartChoice != -1 & _routeGoalChoice != -1)
    {
      _routeRunDijkstra = true;
      
      //set variables for use when leaving this dialog window open
      if (jCheckBox1.isSelected())
      {
        _leaveOpen        = true;
        _displayFullGraph = jCheckBox2.isSelected();
        _startIndex       = jComboBox1.getSelectedIndex();
        _goalIndex        = jComboBox2.getSelectedIndex();;
      }
      else
      {
        _leaveOpen        = false;
        _displayFullGraph = false;
        _startIndex       = 0;
        _goalIndex        = 0;
      }
      
      //set variable for drawing full graph as well as route
      if (jCheckBox2.isSelected())
      {
        _displayFullGraph = true;
      }
      
      this.dispose();
    }
  }
    
  /**
   * Event on button Cancel clicked.
   * @param e
   */
  private void btnCancelClicked(ActionEvent e)
  {
    _routeRunDijkstra = false;
    this.dispose();
  }
    
  /**
   * To apply the buildings in the list to the gui drop down lists.
   */
  public void setBuildingList()
  {
    //add the buildingsList to the choice boxes
    for (int x = 0; x < _routeBuildingsList.getItemCount(); x++)
    {
      jComboBox1.add(_routeBuildingsList.getItem(x));
      jComboBox2.add(_routeBuildingsList.getItem(x));
    }
  }
    
  /**
   * Set the buildings list.
   * @param routeBuildingsList
   */
  public void setRouteBuildingsList(List routeBuildingsList)
  {
    _routeBuildingsList = routeBuildingsList;
  }
    
  /**
   * Get the String start choice.
   * @return String
   */
  public String getRouteStartChoiceText()
  {
    return _routeStartChoiceText;
  }
    
  /**
   * Get the String goal choice.
   * @return String
   */
  public String getRouteGoalChoiceText()
  {
    return _routeGoalChoiceText;
  }
    
  /**
   * Get the int start choice.
   * @return int
   */
  public int getRouteStartChoice()
  {
    return _routeStartChoice;
  }
    
  /**
   * Get the int goal choice.
   * @return int
   */
  public int getRouteGoalChoice()
  {
    return _routeGoalChoice;
  }
    
  /**
   * Get a boolean that determines whether or not Dijkstra's algorithm can run.
   * @return boolean
   */
  public boolean getRouteRunDijkstra()
  {
    return _routeRunDijkstra;
  }
    
  /**
   * Set the array of nodes.
   * @param routeNodeArray
   */
  public void setRouteNodeArray(ArrayList routeNodeArray)
  {
    _routeNodeArray = routeNodeArray;
  }
  
  /**
   * Get the boolean indicating whether or not the dialog should be left open.
   * @return boolean
   */
  public boolean getLeaveOpen()
  {
    return _leaveOpen;
  }
  
  /**
   * Get the boolean indicating whether or not the full graph should be drawn.
   * @return boolean
   */
  public boolean getDisplayFullGraph()
  {
    return _displayFullGraph;
  }
  
  /**
   * Get the JCheckBox jCheckBox1.
   * @return JCheckBox
   */
  public JCheckBox getLeaveOpenCheckBox()
  {
    return jCheckBox1;
  }
  
   /**
   * Get the JCheckBox jCheckBox2.
   * @return JCheckBox
   */
  public JCheckBox getDisplayFullGraphCheckBox()
  {
    return jCheckBox2;
  }
  
  /**
   * Get the Choice jComboBox1.
   * @return Choice
   */
  public Choice getStartChoice()
  {
    return jComboBox1;
  }
  
  /**
   * Get the Choice jComboBox2.
   * @return Choice
   */
  public Choice getGoalChoice()
  {
    return jComboBox2;
  }
  
  /**
   * Get the start item index.
   * @return int
   */
  public int getStartIndex()
  {
    return _startIndex;
  }
  
  /**
   * Get the goal item index.
   * @return int
   */
  public int getGoalIndex()
  {
    return _goalIndex;
  }
}