package applicationpackage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * A dialog class for selecting the application's colour scheme.
 */
public class ColourSchemeDialog extends JDialog 
{
  private JLabel          buildingNodesLabel    = new JLabel();
  private JLabel          choicePointNodesLabel = new JLabel();
  private JLabel          edgesLabel            = new JLabel();
  private JLabel          routesLabel           = new JLabel();
  private JButton         btnCancel             = new JButton();
  private JButton         btnOK                 = new JButton();
  private JLabel          buildingsColourLabel  = new JLabel();
  private JButton         buildingsBtn          = new JButton();
  private JLabel          cpColourLabel         = new JLabel();
  private JLabel          edgesColourLabel      = new JLabel();
  private JLabel          routesColourLabel     = new JLabel();
  private JButton         cpBtn                 = new JButton();
  private JButton         edgesBtn              = new JButton();
  private JButton         routesBtn             = new JButton();
  private JButton         defaultBtn            = new JButton();
  private JLabel          nodeEdgeLabel         = new JLabel();
  private JLabel          newEdgeColourLabel    = new JLabel();
  private JButton         newEdgeBtn            = new JButton();
  private ApplicationGui  _parent;
  

  /**
   * Constructor.
   * @param modal
   * @param title
   * @param parent
   */
  public ColourSchemeDialog(ApplicationGui parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      _parent = parent;
      jbInit();
      
      //set jlabels with existing application colours
      buildingsColourLabel.setBackground
      (_parent.getApplicationGuiColourScheme().getBuildingColour());
      cpColourLabel.setBackground
      (_parent.getApplicationGuiColourScheme().getChoicePointColour());
      edgesColourLabel.setBackground
      (_parent.getApplicationGuiColourScheme().getEdgeColour());
      routesColourLabel.setBackground
      (_parent.getApplicationGuiColourScheme().getRouteColour());
      newEdgeColourLabel.setBackground
      (_parent.getApplicationGuiColourScheme().getNodeForEdgeColour());
    }
    catch(Exception ex)
    {
      Error.showMessage(_parent, "Error initialising ColourSchemeDialog: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());
      ex.printStackTrace();
    }
  }

  /**
   * Initialise the ColourSchemeDialog.
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(294, 282));
    this.getContentPane().setLayout(null);
    this.setTitle("Colour Scheme");
    
    buildingNodesLabel.setText("Building Nodes:");
    buildingNodesLabel.setBounds(new Rectangle(10, 10, 100, 25));
    buildingNodesLabel.setFont(new Font("Tahoma", 1, 12));
    choicePointNodesLabel.setText("Choice Point Nodes:");
    choicePointNodesLabel.setBounds(new Rectangle(10, 45, 130, 25));
    choicePointNodesLabel.setFont(new Font("Tahoma", 1, 12));
    edgesLabel.setText("Edges:");
    edgesLabel.setBounds(new Rectangle(10, 80, 45, 25));
    edgesLabel.setFont(new Font("Tahoma", 1, 12));
    routesLabel.setText("Routes:");
    routesLabel.setBounds(new Rectangle(10, 115, 55, 25));
    routesLabel.setFont(new Font("Tahoma", 1, 12));
    btnCancel.setText("Cancel");
    btnCancel.setBounds(new Rectangle(185, 190, 75, 45));
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancelClicked(e);
        }
      });
    btnOK.setText("OK");
    btnOK.setBounds(new Rectangle(25, 190, 75, 45));
    btnOK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOKClicked(e);
        }
      });
    buildingsColourLabel.setBounds(new Rectangle(155, 15, 35, 20));
    buildingsColourLabel.setBorder(BorderFactory.createLineBorder
    (Color.black, 1));
    buildingsColourLabel.setOpaque(true);
    buildingsBtn.setText("Set...");
    buildingsBtn.setBounds(new Rectangle(210, 15, 65, 20));
    buildingsBtn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          buildingsBtnClicked(e);
        }
      });
    cpColourLabel.setBounds(new Rectangle(155, 50, 35, 20));
    cpColourLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    cpColourLabel.setOpaque(true);
    edgesColourLabel.setBounds(new Rectangle(155, 85, 35, 20));
    edgesColourLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    edgesColourLabel.setOpaque(true);
    routesColourLabel.setBounds(new Rectangle(155, 120, 35, 20));
    routesColourLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    routesColourLabel.setOpaque(true);
    cpBtn.setText("Set...");
    cpBtn.setBounds(new Rectangle(210, 50, 65, 20));
    cpBtn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cpBtnClicked(e);
        }
      });
    edgesBtn.setText("Set...");
    edgesBtn.setBounds(new Rectangle(210, 85, 65, 20));
    edgesBtn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          edgesBtnClicked(e);
        }
      });
    routesBtn.setText("Set...");
    routesBtn.setBounds(new Rectangle(210, 120, 65, 20));
    routesBtn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          routesBtnClicked(e);
        }
      });
    defaultBtn.setText("Default");
    defaultBtn.setBounds(new Rectangle(105, 205, 75, 20));
    defaultBtn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          defaultBtnClicked(e);
        }
      });
    nodeEdgeLabel.setText("New Edge Nodes:");
    nodeEdgeLabel.setBounds(new Rectangle(10, 150, 115, 25));
    nodeEdgeLabel.setFont(new Font("Tahoma", 1, 12));
    newEdgeColourLabel.setBounds(new Rectangle(155, 155, 35, 20));
    newEdgeColourLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    newEdgeColourLabel.setOpaque(true);
    newEdgeBtn.setText("Set...");
    newEdgeBtn.setBounds(new Rectangle(210, 155, 65, 20));
    newEdgeBtn.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          newEdgeBtnClicked(e);
        }
      });
    this.getContentPane().add(newEdgeBtn, null);
    this.getContentPane().add(newEdgeColourLabel, null);
    this.getContentPane().add(nodeEdgeLabel, null);
    this.getContentPane().add(defaultBtn, null);
    this.getContentPane().add(routesBtn, null);
    this.getContentPane().add(edgesBtn, null);
    this.getContentPane().add(cpBtn, null);
    this.getContentPane().add(routesColourLabel, null);
    this.getContentPane().add(edgesColourLabel, null);
    this.getContentPane().add(cpColourLabel, null);
    this.getContentPane().add(buildingsBtn, null);
    this.getContentPane().add(buildingsColourLabel, null);
    this.getContentPane().add(btnOK, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(routesLabel, null);
    this.getContentPane().add(edgesLabel, null);
    this.getContentPane().add(choicePointNodesLabel, null);
    this.getContentPane().add(buildingNodesLabel, null);
  }

  /**
   * Event on button 'Cancel' clicked.
   * @param e
   */
  private void btnCancelClicked(ActionEvent e)
  {
    this.dispose();
  }

  /**
   * Event on button 'OK' clicked.
   * @param e
   */
  private void btnOKClicked(ActionEvent e)
  {
    //set application colours with current jlabel colours
    _parent.getApplicationGuiColourScheme().setBuildingColour
    (buildingsColourLabel.getBackground());
    _parent.getApplicationGuiColourScheme().setChoicePointColour
    (cpColourLabel.getBackground());
    _parent.getApplicationGuiColourScheme().setEdgeColour
    (edgesColourLabel.getBackground());
    _parent.getApplicationGuiColourScheme().setRouteColour
    (routesColourLabel.getBackground());
    _parent.getApplicationGuiColourScheme().setNodeForEdgeColour
    (newEdgeColourLabel.getBackground());
    
    this.dispose();
  }

  /**
   * Event on button 'Set...' for building nodes clicked.
   * @param e
   */
  private void buildingsBtnClicked(ActionEvent e)
  {
    Color tempColour = JColorChooser.showDialog(this, 
    "Select a colour for building nodes", 
    buildingsColourLabel.getBackground());
    
    if (tempColour != null)
    {
      buildingsColourLabel.setBackground(tempColour);
    }     
  }

  /**
   * Event on button 'Set...' for choice point nodes clicked.
   * @param e
   */
  private void cpBtnClicked(ActionEvent e)
  {
    Color tempColour = JColorChooser.showDialog(this, 
    "Select a colour for choice point nodes", 
    cpColourLabel.getBackground());
    
    if (tempColour != null)
    {
      cpColourLabel.setBackground(tempColour);
    }  
  }

  /**
   * Event on button 'Set...' for edges clicked.
   * @param e
   */
  private void edgesBtnClicked(ActionEvent e)
  {
    Color tempColour = JColorChooser.showDialog(this, 
    "Select a colour for edges", 
    edgesColourLabel.getBackground());
    
    if (tempColour != null)
    {
      edgesColourLabel.setBackground(tempColour);
    } 
  }

  /**
   * Event on button 'Set...' for routes clicked.
   * @param e
   */
  private void routesBtnClicked(ActionEvent e)
  {
    Color tempColour = JColorChooser.showDialog(this, 
    "Select a colour for routes", 
    routesColourLabel.getBackground());
    
    if (tempColour != null)
    {
      routesColourLabel.setBackground(tempColour);
    }
  }

  /**
   * Event on button 'Set...' for routes clicked.
   * @param e
   */
  private void newEdgeBtnClicked(ActionEvent e)
  {
    Color tempColour = JColorChooser.showDialog(this, 
    "Select a colour for new edge nodes",
    newEdgeColourLabel.getBackground());
    
     if (tempColour != null)
    {
      newEdgeColourLabel.setBackground(tempColour);
    }
  }
  
  /**
   * Event on button 'Default' clicked.
   * @param e
   */
  private void defaultBtnClicked(ActionEvent e)
  {
    //reset jlabel colours to defaults
    buildingsColourLabel.setBackground(Color.BLUE);
    cpColourLabel.setBackground(Color.YELLOW);
    edgesColourLabel.setBackground(Color.RED);
    routesColourLabel.setBackground(Color.GREEN);
    newEdgeColourLabel.setBackground(Color.BLACK);
  }
}