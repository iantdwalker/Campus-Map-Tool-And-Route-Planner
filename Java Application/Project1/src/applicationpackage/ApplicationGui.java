package applicationpackage;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.*;

import javax.swing.*;

/**
 * Main application gui JFrame class.
 */
public class ApplicationGui extends JFrame
{
  //menu items
  private JMenuBar                    menuBar                   = new JMenuBar();
  private JToolBar                    actionsToolBar            = new JToolBar("Actions");
    
  private JMenu                       menuFile                  = new JMenu();
  private JMenuItem                   menuOpenMap               = new JMenuItem();
  private JMenuItem                   menuOpenXML               = new JMenuItem();
  private JMenuItem                   menuSaveXML               = new JMenuItem();
  private JMenuItem                   menuSaveAsXML             = new JMenuItem();
  private JMenuItem                   menuExit                  = new JMenuItem();

  private JMenu                       menuEdit                  = new JMenu();
  private JRadioButtonMenuItem        menuInsertNode            = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem        menuInsertEdge            = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem        menuMoveNode              = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem        menuNameNode              = new JRadioButtonMenuItem();
  private JRadioButtonMenuItem        menuRemoveItem            = new JRadioButtonMenuItem();
  private JMenuItem                   menuRemoveAll             = new JMenuItem();  
      
  private JMenu                       menuView                  = new JMenu();
  private JMenu                       menuFrame                 = new JMenu();
  private JMenuItem                   menuToolbar               = new JMenuItem();
  private JMenuItem                   menuHideGraph             = new JMenuItem();
  private JMenuItem                   menuShowGraph             = new JMenuItem();
  private JRadioButtonMenuItem        menuZoom                  = new JRadioButtonMenuItem();
      
  private JMenu                       menuOptions               = new JMenu();
  private JMenuItem                   menuSetColours            = new JMenuItem();
  
  private JMenu                       menuRun                   = new JMenu();
  private JMenuItem                   menuGetRoute              = new JMenuItem();  
  
  private JMenu                       menuHelp                  = new JMenu();
  private JMenuItem                   menuUserHelp              = new JMenuItem();
  private JMenuItem                   menuAbout                 = new JMenuItem();
  
  //menu toolbar
  private JButton                     openMapButton             = new JButton();
  private JButton                     openXmlButton             = new JButton();
  private JButton                     saveXmlButton             = new JButton();
  private JButton                     saveAsXmlButton           = new JButton();
  
  private JButton                     insertNodeButton          = new JButton();
  private JButton                     insertEdgeButton          = new JButton();
  private JButton                     moveNodeButton            = new JButton();
  private JButton                     nameNodeButton            = new JButton();
  private JButton                     removeItemButton          = new JButton();
  private JButton                     removeAllButton           = new JButton();
  private JButton                     hideGraphButton           = new JButton();
  private JButton                     showGraphButton           = new JButton();
  private JButton                     zoomButton                = new JButton();
  private JButton                     setColourButton           = new JButton();
  private JButton                     getRouteButton            = new JButton();
  private JButton                     userHelpButton            = new JButton();
  private JButton                     aboutButton               = new JButton();
  
  private Color                       _menuBackgroundColor;
  
  //status bar
  private StatusBar                   statusBar                 = new StatusBar();
  
  //other attributes
  private ApplicationGuiUtilities     _applicationGuiUtilities  = new ApplicationGuiUtilities(this);
  private ApplicationGuiColourScheme  _colourScheme             = new ApplicationGuiColourScheme(); 
  private ApplicationGuiIcons         _applicationGuiIcons      = new ApplicationGuiIcons(this);
  private String                      _TITLE                    = "Campus Map Tool and Route Planner";
  private DrawingPanelMouseListener   _mouse                    = new DrawingPanelMouseListener(this);               
  private int                         _userOptionChoice         = 0;
  private int                         _whichNode                = 1;
  private JPanel                      frameJPanel               = new JPanel();
  private JScrollBar                  hbar                      = new JScrollBar(JScrollBar.HORIZONTAL);
  private JScrollBar                  vbar                      = new JScrollBar(JScrollBar.VERTICAL);
  private DrawingPanel                drawingPanel              = new DrawingPanel(this);  
  private JScrollPane                 frameJScrollPane          = new JScrollPane(drawingPanel);
  private JTextPane                   introTextPane             = new JTextPane();  
    
  /**
   * Constructor.
   */
  public ApplicationGui()
  {
    try
    {
      jbInit();      
    }    
    catch(Exception ex)
    {
      Error.showMessage(this, "Error initialising ApplicationGui: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();     
    }
  }

  /**
   * Initialise the ApplicationGui.
   * @throws java.io.IOException
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception, IOException
  {
    this.setJMenuBar(menuBar);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(actionsToolBar, BorderLayout.NORTH);
                
    //default size for empty frame
    this.setSize(new Dimension(800, 600));    
    this.setTitle(_TITLE);
    
    menuFile.setText("File");
    menuOpenMap.setText("Open Map Image");
    menuOpenXML.setText("Open XML Graph");
    menuSaveXML.setText("Save XML Graph");
    menuSaveAsXML.setText("Save As...");
    menuExit.setText("Exit");
    menuOpenMap.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          openMap_ActionPerformed(ae);
        }
      });
    menuOpenXML.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          openXML_ActionPerformed(ae);
        }
      });  
    menuSaveXML.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          saveXML_ActionPerformed(ae);
        }
      });
    menuSaveAsXML.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          saveAsXML_ActionPerformed(ae);
        }
      });  
    menuExit.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          exit_ActionPerformed(ae);
        }
      });
    menuFile.add(menuOpenMap);
    menuFile.add(menuOpenXML);
    menuFile.add(menuSaveXML);
    menuFile.add(menuSaveAsXML);
    menuFile.add(new JSeparator());
    menuFile.add(menuExit);
    menuOpenXML.setEnabled(false);
    menuSaveXML.setEnabled(false);
    menuSaveAsXML.setEnabled(false);
    
    menuEdit.setText("Edit");
    menuInsertNode.setText("Insert Node");
    menuInsertEdge.setText("Insert Edge");
    menuMoveNode.setText("Move Node");
    menuNameNode.setText("Name Node");
    menuRemoveItem.setText("Remove Item");
    menuRemoveAll.setText("Remove All");
    menuInsertNode.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          insertNode_ActionPerformed(ae);
        }
      });
    menuInsertEdge.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          insertEdge_ActionPerformed(ae);
        }
      });
    menuMoveNode.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          moveNode_ActionPerformed(ae);
        }
      });
    menuNameNode.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          nameNode_ActionPerformed(ae);
        }
      });
    menuRemoveItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          removeItem_ActionPerformed(ae);
        }
      });
    menuRemoveAll.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          removeAll_ActionPerformed(ae);
        }
      });
    menuEdit.add(menuInsertNode);
    menuEdit.add(menuInsertEdge);
    menuEdit.add(menuMoveNode);
    menuEdit.add(menuNameNode);
    menuEdit.add(menuRemoveItem);
    menuEdit.add(menuRemoveAll);
    menuEdit.setEnabled(false);
        
    menuView.setText("View");
    menuFrame.setText("Application Frame");
    menuToolbar.setText("Actions Toolbar Off");
    menuHideGraph.setText("Hide Graph");
    menuShowGraph.setText("Show Graph");
    menuZoom.setText("Zoom In/Out");   
    menuToolbar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          menuToolbar_ActionPerformed(ae);
        }
      });
    menuHideGraph.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          hideGraph_ActionPerformed(ae);
        }
      });
    menuShowGraph.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          showGraph_ActionPerformed(ae);
        }
      });    
    menuZoom.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          menuZoom_ActionPerformed(ae);
        }
      });     
    menuFrame.add(menuToolbar);
    menuView.add(menuFrame);
    menuView.add(menuHideGraph);
    menuView.add(menuShowGraph);
    menuView.add(menuZoom);
    menuHideGraph.setEnabled(false);
    menuShowGraph.setEnabled(false);   
    menuZoom.setEnabled(false);    
    
    menuOptions.setText("Options");
    menuSetColours.setText("Set Colour Scheme");
    menuSetColours.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          setColourScheme_ActionPerformed(ae);
        }
      });
    menuOptions.add(menuSetColours);
          
    menuRun.setText("Run");
    menuGetRoute.setText("Get Route");    
    menuGetRoute.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          getRoute_ActionPerformed(ae);
        }
      });   
    menuRun.add(menuGetRoute);
    menuRun.setEnabled(false);
    
    menuHelp.setText("Help");
    menuUserHelp.setText("User Help");
    menuAbout.setText("About");
    menuUserHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          userHelp_ActionPerformed(ae);
        }
      });
    menuAbout.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          about_ActionPerformed(ae);
        }
      });
    menuHelp.add(menuUserHelp);
    menuHelp.add(menuAbout);

    menuBar.add(menuFile);
    menuBar.add(menuEdit);
    menuBar.add(menuView);
    menuBar.add(menuOptions);
    menuBar.add(menuRun);
    menuBar.add(menuHelp);
    
    //set menu image icons
    _applicationGuiIcons.setMenuIcons();
    
    //construct toolbar
    openMapButton.setToolTipText("Open Map Image");
    openMapButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          openMap_ActionPerformed(ae);
        }
      });   
    actionsToolBar.add(openMapButton);
    
    openXmlButton.setToolTipText("Open XML graph");
    openXmlButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          openXML_ActionPerformed(ae);
        }
      });
    openXmlButton.setEnabled(false);
    actionsToolBar.add(openXmlButton);
    
    saveXmlButton.setToolTipText("Save XML graph");
    saveXmlButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          saveXML_ActionPerformed(ae);
        }
      });
    saveXmlButton.setEnabled(false);
    actionsToolBar.add(saveXmlButton);
    
    saveAsXmlButton.setToolTipText("Save As...");
    saveAsXmlButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          saveAsXML_ActionPerformed(ae);
        }
      });
    saveAsXmlButton.setEnabled(false);
    actionsToolBar.add(saveAsXmlButton);
    
    insertNodeButton.setToolTipText("Insert Node");
    insertNodeButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          insertNode_ActionPerformed(ae);
        }
      });
    actionsToolBar.addSeparator();
    insertNodeButton.setEnabled(false);
    actionsToolBar.add(insertNodeButton);
    
    insertEdgeButton.setToolTipText("Insert Edge");
    insertEdgeButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          insertEdge_ActionPerformed(ae);
        }
      });
    insertEdgeButton.setEnabled(false);
    actionsToolBar.add(insertEdgeButton);
    
    moveNodeButton.setToolTipText("Move Node");
    moveNodeButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          moveNode_ActionPerformed(ae);
        }
      });
    moveNodeButton.setEnabled(false);
    actionsToolBar.add(moveNodeButton);
    
    nameNodeButton.setToolTipText("Name Node");
    nameNodeButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          nameNode_ActionPerformed(ae);
        }
      });
    nameNodeButton.setEnabled(false);
    actionsToolBar.add(nameNodeButton);
    
    removeItemButton.setToolTipText("Remove Item");
    removeItemButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          removeItem_ActionPerformed(ae);
        }
      });
    removeItemButton.setEnabled(false);
    actionsToolBar.add(removeItemButton);
    
    removeAllButton.setToolTipText("Remove All");
    removeAllButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          removeAll_ActionPerformed(ae);
        }
      });
    removeAllButton.setEnabled(false);
    actionsToolBar.add(removeAllButton);
    
    hideGraphButton.setToolTipText("Hide Graph");
    hideGraphButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          hideGraph_ActionPerformed(ae);
        }
      });
    actionsToolBar.addSeparator();
    hideGraphButton.setEnabled(false);
    actionsToolBar.add(hideGraphButton);
    
    showGraphButton.setToolTipText("Show Graph");
    showGraphButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          showGraph_ActionPerformed(ae);
        }
      });
    showGraphButton.setEnabled(false);
    actionsToolBar.add(showGraphButton);
    
    zoomButton.setToolTipText("Zoom In/Out");
    zoomButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          menuZoom_ActionPerformed(ae);
        }
      });
    zoomButton.setEnabled(false);
    actionsToolBar.add(zoomButton);
    
    setColourButton.setToolTipText("Set Colour Scheme");
    setColourButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          setColourScheme_ActionPerformed(ae);
        }
      });
    actionsToolBar.addSeparator();
    actionsToolBar.add(setColourButton);
    
    getRouteButton.setToolTipText("Get Route");
    getRouteButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          getRoute_ActionPerformed(ae);
        }
      });
    actionsToolBar.addSeparator();
    getRouteButton.setEnabled(false);
    actionsToolBar.add(getRouteButton);
      
    userHelpButton.setToolTipText("User Help");
    userHelpButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          userHelp_ActionPerformed(ae);
        }
      });
    actionsToolBar.addSeparator();
    actionsToolBar.add(userHelpButton);
    
    aboutButton.setToolTipText("About");
    aboutButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae) 
        {
          about_ActionPerformed(ae);
        }
      });
    actionsToolBar.add(aboutButton);
    
    //set toolbar image icons   
    _applicationGuiIcons.setToolbarIcons();
    _menuBackgroundColor = actionsToolBar.getBackground();
    
    //set the opening instructions panel
    introTextPane.setEditable(false);
    introTextPane.setBackground(frameJPanel.getBackground());
    introTextPane.setFont(new Font("Verdana", 0, 16));
    introTextPane.setForeground(Color.BLACK);    
    String instructions = this.getStartInstructions();
    introTextPane.setText(instructions);
    
    //set the campus map jpg and drawing canvas
    drawingPanel.add(introTextPane);
    drawingPanel.addMouseListener(_mouse);
    drawingPanel.addMouseMotionListener(_mouse);    
    
    //central panel with scroll bars
    frameJPanel.setLayout(new BorderLayout());
    frameJScrollPane.setHorizontalScrollBar(hbar);
    frameJScrollPane.setVerticalScrollBar(vbar);
        
    hbar.addAdjustmentListener(new ScrollBarAdjustmentListener());
    vbar.addAdjustmentListener(new ScrollBarAdjustmentListener());

    frameJPanel.add(hbar, BorderLayout.SOUTH);
    frameJPanel.add(vbar, BorderLayout.EAST);
    frameJPanel.add(frameJScrollPane, BorderLayout.CENTER);
    
    //status bar
    statusBar.setText(new String[] {"Welcome!"});
    
    this.getContentPane().add(frameJPanel, BorderLayout.CENTER);
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
   }
   
   /**
   * Private method that reads and returns a text file of start instructions.
   * @throws java.lang.Exception
   * @return String
   */
  private String getStartInstructions() throws Exception
  {
    String            text              = "";
    boolean           done              = false;
    InputStream       inputStream       = this.getClass().getResourceAsStream
    ("/Resources/StartInstructions.txt");
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
    
    return text;
  }  
  
  /**
   * Event on menu item 'Open Map Image' selected.
   * @param e
   */
  private void openMap_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 0;
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
    
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
    
    _applicationGuiUtilities.openMapImage();    
  }
  
  /**
   * Event on menu item 'Open XML Graph' selected.
   * @param e
   */
  private void openXML_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 0;
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
    
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
    
    _applicationGuiUtilities.openXmlGraph();          
  }

  /**
   * Event on menu item 'Save XML Graph' selected.
   * @param e
   */
  private void saveXML_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _applicationGuiUtilities.saveXmlGraph();
  }
  
  /**
   * Event on menu item 'Save As...' selected.
   * @param e
   */
  private void saveAsXML_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _applicationGuiUtilities.saveAs();
  }

  /**
   * Event on menu item 'Exit' selected.
   * @param e
   */
  private void exit_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 0;
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
    
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
    
    //determine if any user placed nodes exist or not
    boolean exit = _applicationGuiUtilities.checkForNodes("Exit");
    
    if (exit)
    {
      System.exit(0);
    }   
  }
  
  /**
   * Event on menu item 'Insert Node' selected.
   * @param e
   */
  private void insertNode_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 1;
    
    //reset edge place marker and selected node colours
    _whichNode         = 1;
    _applicationGuiUtilities.resetSelectedNodeColour();
    
    menuInsertNode.setSelected(true);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
              
    insertNodeButton.setBackground(Color.GRAY);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
  }

  /**
   * Event on menu item 'Insert Edge' selected.
   * @param e
   */
  private void insertEdge_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice = 2;    
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(true);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
        
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(Color.GRAY);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
  }

  /**
   * Event on menu item 'Move Node' selected.
   * @param e
   */
  private void moveNode_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 3;
    
    //reset edge place marker and selected node colours
    _whichNode         = 1;
    _applicationGuiUtilities.resetSelectedNodeColour();
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(true);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
        
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(Color.GRAY);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
  }
  
  /**
   * Event on menu item 'Name Node' selected.
   * @param e
   */
  private void nameNode_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 5;
    
    //reset edge place marker and selected node colours
    _whichNode         = 1;
    _applicationGuiUtilities.resetSelectedNodeColour();
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(true);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
        
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(Color.GRAY);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
  }

  /**
   * Event on menu item 'Remove Item' selected.
   * @param e
   */
  private void removeItem_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice  = 4;
    
    //reset edge place marker and selected node colours
    _whichNode         = 1;
    _applicationGuiUtilities.resetSelectedNodeColour();
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(true);
                        
    menuZoom.setSelected(false);
        
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(Color.GRAY);
          
    zoomButton.setBackground(_menuBackgroundColor);
  }

  /**
   * Event on menu item 'Remove All' selected.
   * @param e
   */
  private void removeAll_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {""});
    
    _userOptionChoice = 0;    
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(false);
    
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(_menuBackgroundColor);
    
    //determine if any user placed nodes exist or not
    if (_applicationGuiUtilities.checkForNodes("Remove All"))
    {
      _applicationGuiUtilities.removeAll();
    }       
  }
  
  /**
   * Event on menu item 'Toolbar On/Off' selected.
   * @param e
   */
  private void menuToolbar_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {"Menu toolbar on/off."});
    
    if (menuToolbar.getText().equals("Actions Toolbar Off"))
    {
      actionsToolBar.setVisible(false);
      menuToolbar.setText("Actions Toolbar On");
    }
    else if (menuToolbar.getText().equals("Actions Toolbar On"))
    {
      actionsToolBar.setVisible(true);
      menuToolbar.setText("Actions Toolbar Off");
    }
  }
  
  /**
   * Event on menu item 'Hide Graph' selected.
   * @param e
   */
  private void hideGraph_ActionPerformed(ActionEvent e)
  {
    drawingPanel.hideGraph();
    
    statusBar.setText(new String[]
    {"The map graph is currently hidden."});
  }

  /**
   * Event on menu item 'Show Graph' selected.
   * @param e
   */
  private void showGraph_ActionPerformed(ActionEvent e)
  {
    drawingPanel.paintAll();
    
    statusBar.setText(new String[]
    {"The map graph is currently visible."});
  }
  
  /**
   * Event on menu item 'Zoom In/Out' selected.
   * @param e
   */
  private void menuZoom_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {"Left mouse button to zoom in, right mouse button to zoom out."});
    
    _userOptionChoice = 6;    
    
    menuInsertNode.setSelected(false);
    menuInsertEdge.setSelected(false);
    menuMoveNode.setSelected(false);
    menuNameNode.setSelected(false);
    menuRemoveItem.setSelected(false);
                        
    menuZoom.setSelected(true);
        
    insertNodeButton.setBackground(_menuBackgroundColor);
    insertEdgeButton.setBackground(_menuBackgroundColor);
    moveNodeButton.setBackground(_menuBackgroundColor);
    nameNodeButton.setBackground(_menuBackgroundColor);
    removeItemButton.setBackground(_menuBackgroundColor);
          
    zoomButton.setBackground(Color.GRAY);
  }  
  
  /**
   * Event on menu item 'Set Colour Scheme' selected.
   * @param e
   */
  private void setColourScheme_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {"Set the application's colour scheme."});
    
    ColourSchemeDialog colourDialog = new ColourSchemeDialog(this,
    "Colour Scheme", true);
    
    colourDialog.setLocationRelativeTo(this);    
    colourDialog.setVisible(true);
    
    //refresh any colour settings on the graph drawing
    drawingPanel.paintAll();
  } 

  /**
   * Event on menu item 'Get Route' selected.
   * @param e
   */
  private void getRoute_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {"Select a start and goal location."});
    
    _applicationGuiUtilities.getRoute();    
  }
  
  /**
   * Event on menu item 'User Help' selected.
   * @param e
   */
  private void userHelp_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {"User help."});
        
    UserHelpDialog userHelpDialog = new UserHelpDialog(this, "User Help", true);
    userHelpDialog.setLocationRelativeTo(this);
    userHelpDialog.setVisible(true);    
  }

  /**
   * Event on menu item 'About' selected.
   * @param e
   */
  private void about_ActionPerformed(ActionEvent e)
  {
    statusBar.setText(new String[]
    {"About."});
        
    JOptionPane.showMessageDialog(this, "'Campus Map Tool and Route Planner'\n"
    + "Designed, created and copyright by Ian Walker 2005-2007.", "About", 
    JOptionPane.INFORMATION_MESSAGE);
  }
  
  /**
   * Get the menu item menuFile.
   * @return JMenuItem
   */
  public JMenuItem getMenuFile()
  {
    return menuFile;
  }
  
  /**
   * Get the menu item menuOpenMap.
   * @return JMenuItem
   */
  public JMenuItem getMenuOpenMap()
  {
    return menuOpenMap;
  }
  
  /**
   * Get the menu item menuOpenXML.
   * @return JMenuItem
   */
  public JMenuItem getMenuOpenXML()
  {
    return menuOpenXML;
  }
    
  /**
   * Get the menu item menuSaveXML.
   * @return JMenuItem
   */
  public JMenuItem getMenuSaveXML()
  {
    return menuSaveXML;
  }
  
  /**
   * Get the menu item menuSaveAsXML.
   * @return JMenuItem
   */
  public JMenuItem getMenuSaveAsXML()
  {
    return menuSaveAsXML;
  }
  
  /**
   * Get the menu item menuExit.
   * @return JMenuItem
   */
  public JMenuItem getMenuExit()
  {
    return menuExit;
  }
  
  /**
   * Get the menu menuEdit.
   * @return JMenu
   */
  public JMenu getMenuEdit()
  {
    return menuEdit;
  }
  
  /**
   * Get the menu item insertNode.
   * @return JMenuItem
   */
  public JMenuItem getMenuInsertNode()
  {
    return menuInsertNode;
  }
  
  /**
   * Get the menu item insertEdge.
   * @return JMenuItem
   */
  public JMenuItem getMenuInsertEdge()
  {
    return menuInsertEdge;
  }
  
  /**
   * Get the menu item moveNode.
   * @return JMenuItem
   */
  public JMenuItem getMenuMoveNode()
  {
    return menuMoveNode;
  }
  
  /**
   * Get the menu item nameNode.
   * @return JMenuItem
   */
  public JMenuItem getMenuNameNode()
  {
    return menuNameNode;
  }
  
  /**
   * Get the menu item removeItem.
   * @return JMenuItem
   */
  public JMenuItem getMenuRemoveItem()
  {
    return menuRemoveItem;
  }
  
  /**
   * Get the menu item removeAll.
   * @return JMenuItem
   */
  public JMenuItem getMenuRemoveAll()
  {
    return menuRemoveAll;
  }
  
  /**
   * Get the menu menuView.
   * @return JMenu
   */
  public JMenu getMenuView()
  {
    return menuView;
  }
  
  /**
   * Get the menu menuFrame.
   * @return JMenu
   */
  public JMenu getMenuFrame()
  {
    return menuFrame;
  }
  
  /**
   * Get the menu item menuToolbar.
   * @return JMenuItem
   */
  public JMenuItem getMenuToolbar()
  {
    return menuToolbar;
  }
  
  /**
   * Get the menu item showGraph.
   * @return JMenuItem
   */
  public JMenuItem getMenuShowGraph()
  {
    return menuShowGraph;
  }
  
  /**
   * Get the menu item hideGraph.
   * @return JMenuItem
   */
  public JMenuItem getMenuHideGraph()
  {
    return menuHideGraph;
  }
  
  /**
   * Get the menu item zoom.
   * @return JMenuItem
   */
  public JMenuItem getMenuZoom()
  {
    return menuZoom;
  }
  
  /**
   * Get the menu menuOptions.
   * @return JMenu
   */
  public JMenu getMenuOptions()
  {
    return menuOptions;
  }
  
  /**
   * Get the menu item setColourScheme.
   * @return JMenuItem
   */
  public JMenuItem getMenuSetColourScheme()
  {
    return menuSetColours;
  }
  
  /**
   * Get the menu menuRun.
   * @return JMenu
   */
  public JMenu getMenuRun()
  {
    return menuRun;
  }
  
  /**
   * Get the menu item getRoute.
   * @return JMenuItem
   */
  public JMenuItem getMenuGetRoute()
  {
    return menuGetRoute;
  }
  
  /**
   * Get the menu item help.
   * @return JMenuItem
   */
  public JMenuItem getMenuHelp()
  {
    return menuHelp;
  }
  
  /**
   * Get the menu item userHelp.
   * @return JMenuItem
   */
  public JMenuItem getMenuUserHelp()
  {
    return menuUserHelp;
  }
  
  /**
   * Get the menu item about.
   * @return JMenuItem
   */
  public JMenuItem getMenuAbout()
  {
    return menuAbout;
  }
  
  /**
   * Get the open map toolbar button.
   * @return JButton
   */
  public JButton getOpenMapButton()
  {
    return openMapButton;
  }
  
  /**
   * Get the open xml graph toolbar button.
   * @return JButton
   */
  public JButton getOpenXmlButton()
  {
    return openXmlButton;
  }
  
  /**
   * Get the save xml graph toolbar button.
   * @return JButton
   */
  public JButton getSaveXmlButton()
  {
    return saveXmlButton;
  }
  
  /**
   * Get the save as xml graph toolbar button.
   * @return JButton
   */
  public JButton getSaveAsXmlButton()
  {
    return saveAsXmlButton;
  }
  
  /**
   * Get the insert node toolbar button.
   * @return JButton
   */
  public JButton getInsertNodeButton()
  {
    return insertNodeButton;
  }
  
  /**
   * Get the insert edge toolbar button.
   * @return JButton
   */
  public JButton getInsertEdgeButton()
  {
    return insertEdgeButton;
  }
  
  /**
   * Get the move node toolbar button.
   * @return JButton
   */
  public JButton getMoveNodeButton()
  {
    return moveNodeButton;
  }
  
  /**
   * Get the name node toolbar button.
   * @return JButton
   */
  public JButton getNameNodeButton()
  {
    return nameNodeButton;
  }
  
  /**
   * Get the remove item toolbar button.
   * @return JButton
   */
  public JButton getRemoveItemButton()
  {
    return removeItemButton;
  }
  
  /**
   * Get the remove all toolbar button.
   * @return JButton
   */
  public JButton getRemoveAllButton()
  {
    return removeAllButton;
  }
  
  /**
   * Get the hide graph toolbar button.
   * @return JButton
   */
  public JButton getHideGraphButton()
  {
    return hideGraphButton;
  }
  
  /**
   * Get the show graph toolbar button.
   * @return JButton
   */
  public JButton getShowGraphButton()
  {
    return showGraphButton;
  }
  
  /**
   * Get the zoom toolbar button.
   * @return JButton
   */
  public JButton getZoomButton()
  {
    return zoomButton;
  }
  
  /**
   * Get the set colour toolbar button.
   * @return JButton
   */
  public JButton getSetColourButton()
  {
    return setColourButton;
  }
  
  /**
   * Get the get route toolbar button.
   * @return JButton
   */
  public JButton getGetRouteButton()
  {
    return getRouteButton;
  }
  
  /**
   * Get the user help toolbar button.
   * @return JButton
   */
  public JButton getUserHelpButton()
  {
    return userHelpButton;
  }
  
  /**
   * Get the about toolbar button.
   * @return JButton
   */
  public JButton getAboutButton()
  {
    return aboutButton;
  }
  
  /**
   * Get the gui's ApplicationGuiUtilities class.
   * @return ApplicationGuiUtilities
   */
  public ApplicationGuiUtilities getApplicationGuiUtilities()
  {
    return _applicationGuiUtilities;
  }
  
  /**
   * Get the gui's colour scheme class.
   * @return ApplicationGuiColourScheme
   */
  public ApplicationGuiColourScheme getApplicationGuiColourScheme()
  {
    return _colourScheme;
  }
  
  /**
   * Get the gui's DrawingPanel class.
   * @return DrawingPanel
   */
  public DrawingPanel getDrawingPanel()
  {
    return drawingPanel;
  }
  
  /**
   * Get the gui's JScrollPane class.
   * @return JScrollPane
   */
  public JScrollPane getFrameJScrollPane()
  {
    return frameJScrollPane;
  }
  
  /**
   * Get the gui's JTextPane class.
   * @return JTextPane
   */
  public JTextPane getIntroTextPane()
  {
    return introTextPane;
  }
  
  /**
   * Get the gui's userOptionChoice int that coresponds to menu item selected.
   * @return int
   */
  public int getUserOptionChoice()
  {
    return _userOptionChoice;
  }
  
  /**
   * Get the gui's int that represents last node selected when creating edges.
   * @return int
   */
  public int getWhichNode()
  {
    return _whichNode;
  }
  
  /**
   * Set the gui's int that represents last node selected when creating edges.
   * @param whichNode
   */
  public void setWhichNode(int whichNode)
  {
    _whichNode = whichNode;
  } 
  
  /**
   * Get the gui's StatusBar.
   * @return StatusBar
   */
  public StatusBar getStatusBar()
  {
    return statusBar;
  }  
}