package applicationpackage;

import java.awt.List;
import java.awt.Point;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.w3c.dom.Document;

/**
 * Provides utility methods for the ApplicationGui class.
 */
public class ApplicationGuiUtilities 
{
  //general attributes
  private ApplicationGui  _applicationGui;
  private ArrayList       _nodeArray;
  private ArrayList       _edgeArray;
  private ArrayList       _resultingEdges;
  private List            _buildingsList;
      
  //Dijkstra results
  private MapGraph        _mapGraph;
  private Dijkstra        _dijkstra;
  private int[]           _predecessorNodes;
  private int             _startChoice;
  private String          _startChoiceText;
  private int             _goalChoice;
  private String          _goalChoiceText;
  private String[]        _dijkstraResults;
  private String          _flag;
  private int             _tempStartNode;
  private int             _tempEndNode;
  private boolean         _leaveOpen;
  private boolean         _dispayFullGraph;
  private int             _listStartIndex;
  private int             _listGoalIndex;
  private Point           _dialogLocation;
   
  //drawing
  private BufferedImage   _image;
  private File            _userMapChoice;
  private int             _choicePointCounter;
  private boolean         _standAloneNode;
  private int             _standAloneNodes;
  private boolean         _nodePlaced;
  private int             _noOfNodes;
  private int             _zoom;
  private int             _mouseBtn;
    
  //XML processing
  private ProcessTree     _processTree;
  private XmlParser       _xmlParser;
  private Document        _document;
  
  //JFileChooser
  private int             _fcVal; 
  private File            _userXmlChoice;
  private JFileChooser    _imageChooser;
  private JFileChooser    _xmlChooser;
  private ImageFileFilter _imageFilter;
  private XmlFileFilter   _xmlFilter;
  
  //for sorting the _buildingsList
  public String[]         _tempBuildingsArray;
    
  /**
   * Constructor.
   */
  public ApplicationGuiUtilities(ApplicationGui applicationGui)
  {
    _applicationGui           = applicationGui;
    _nodeArray                = new ArrayList();
    _edgeArray                = new ArrayList();
    _resultingEdges           = new ArrayList();
    _buildingsList            = new List();
    _dijkstra                 = new Dijkstra();
    _startChoice              = 0;
    _goalChoice               = 0;
    _leaveOpen                = false;
    _dispayFullGraph          = false;
    _listStartIndex           = 0;
    _listGoalIndex            = 0;
    _choicePointCounter       = 1;
    _standAloneNode           = true;
    _standAloneNodes          = 0;
    _nodePlaced               = false;
    _noOfNodes                = 0;
    _zoom                     = 1;
    _imageChooser             = new JFileChooser();
    _xmlChooser               = new JFileChooser();
    _imageFilter              = new ImageFileFilter();
    _xmlFilter                = new XmlFileFilter();    
  }
  
  /**
   * Pythagoras's Hypotenuse theorem to calculate the edge weights.
   * @return int
   * @param endPoint
   * @param startPoint
   */
  public int calculateWeight(Point startPoint, Point endPoint)
  {
    int     side1         = endPoint.y - startPoint.y;
    int     side2         = endPoint.x - startPoint.x;
    int     xSquared      = (side1 * side1 + side2 * side2);
    double  weightDouble  = Math.sqrt(xSquared);
    int     weightInt     = (int) weightDouble;
    
    return weightInt;
  }
  
  /**
   * Private method that constructs a MapGraph object from node/edge arrays.
   * @return boolean
   */
  private boolean createGraph()
  {
    //1: set choice point number counter
    for (int x = 0; x < _nodeArray.size(); x++)
    {
      Node tempNode = (Node) _nodeArray.get(x);
      
      if (tempNode.getName().startsWith("Choice Point"))
      {
        tempNode.setName("Choice Point" + _choicePointCounter);
        _nodeArray.set(_nodeArray.indexOf(tempNode), tempNode);
        _choicePointCounter = _choicePointCounter + 1;        
      }      
    }
    
    _choicePointCounter = 1;

    //2: ensure at least 2 nodes and one edege are placed
    if (_nodeArray.size() < 2 | _edgeArray.size() < 1)
    {
      Error.showMessage(_applicationGui,
      "You must place at least two nodes and one edge.");  
      return false;
    }    
    else
    {
      //3: ensure that there are no standalone nodes
      _standAloneNodes = 0;
      
      for (int y = 0; y < _nodeArray.size(); y++)
      {
        Node tempNode2 = (Node) _nodeArray.get(y);
        
        for (int z = 0; z < _edgeArray.size(); z++)
        {
          Edge tempEdge = (Edge) _edgeArray.get(z);

          if (tempEdge.getStartNode() == tempNode2.getNumber() |
              tempEdge.getEndNode() == tempNode2.getNumber())
          {
            _standAloneNode = false;
          }
        }
        
        if (_standAloneNode)
        {
          _standAloneNodes = _standAloneNodes + 1;
        }
        
        _standAloneNode = true;
      }

      if (_standAloneNodes != 0)
      {
        Error.showMessage(_applicationGui,
        "You must connect all nodes with an edge."); 
        return false;
      }
      else
      {
        //4: we can attempt to create the graph
        _mapGraph = new MapGraph(_nodeArray.size());

        //add the nodes
        for (int i = 0; i < _nodeArray.size(); i++)
        {
          Node tempNode3 = (Node) _nodeArray.get(i);
          _mapGraph.addNode(tempNode3.getNumber(), tempNode3.getName());
        }

        //add the edges
        for (int j = 0; j < _edgeArray.size(); j++)
        {
          Edge tempEdge2 = (Edge) _edgeArray.get(j);
          _mapGraph.addEdge(tempEdge2.getStartNode(), tempEdge2.getEndNode(),
          tempEdge2.getWeight());
        }
        
        _startChoice = 0;
        
        //execute dummy Dijkstra run to ensure it is a connected graph
        boolean graphConnected = this.runDijkstra();

        //5: check if the graph is fully connected and leave if not
        if (!graphConnected)
        {
          Error.showMessage(_applicationGui, "This graph is not fully " +
          "connected: the seperate elements\nmust join together to form one " +
          "complete and connected graph.");    
          
          _mapGraph = null;
          return false;
        }        
        else
        {
          return true;
        }                
      }
    }
  }
  
  /**
   * Execute Dijkstra's algorithm on a created MapGraph object.
   * @return boolean
   */
  public boolean runDijkstra()
  {
    //map graph object and selected start location int are local variables
    _predecessorNodes = _dijkstra.execute(_mapGraph, _startChoice);

    //return false if graph is not connected
    if (!_dijkstra.getGraphConnected())
    {
      return false;
    }
    else
    {
      _dijkstraResults = new String[_nodeArray.size()];

      for (int x = 0; x < _nodeArray.size(); x++)
      {
        _dijkstraResults[x] = _dijkstra.GetRoute
        (_mapGraph, _predecessorNodes, _startChoice, x);
      }
      
      return true;
    }
  }
  
  /**
   * Method for saving a graph of nodes and edges to xml documents.
   */
  public void saveXmlGraph()
  {
    if (_userXmlChoice != null)
    {
      this.internalSaveProcess(_userXmlChoice.getAbsolutePath());
    }
    else
    {
      Error.showMessage(_applicationGui, "Error saving XML graph: The " + 
      "current active file is null.  Please try the menu option 'Save As'");
    }
  }
  
  /**
   * Method for saving a graph of nodes and edges to xml documents.
   */
  public void saveAs()
  {
    //open File dialog and set appropriate filters
    _xmlChooser.setDialogTitle("Save As...");
    _xmlChooser.setFileFilter(_xmlFilter);
    _xmlChooser.setAcceptAllFileFilterUsed(false);
    _fcVal = _xmlChooser.showSaveDialog(_applicationGui);
      
    //save option selected
    if (_fcVal == JFileChooser.APPROVE_OPTION)
    {
      _userXmlChoice = _xmlChooser.getSelectedFile();
      String tempExt = FileFilterUtilities.getExtension(_userXmlChoice);
            
      //If file with ext already exists
      if (_userXmlChoice.isFile())
      {
        int optionChoice = JOptionPane.showConfirmDialog(_applicationGui, 
        "Do you want to overwrite the existing file?", "Save",
        JOptionPane.YES_NO_CANCEL_OPTION);
        
        if (optionChoice == 0)
        {
          //check selected file is right type
          if ((tempExt.equalsIgnoreCase("xml")))
          {
            this.internalSaveProcess(_userXmlChoice.getAbsolutePath());            
            _applicationGui.getMenuSaveXML().setEnabled(true);
            _applicationGui.getSaveXmlButton().setEnabled(true);
          }            
          else
          {
            Error.showMessage(_applicationGui, "XML file save error: graph " +
            "has not been saved because the chosen file is an incorrect type.");           
          }
        }            
        else if (optionChoice == 1)
        {
          this.saveAs();
        }        
      }      
      else
      {        
        //test for name without '.XML' extension incase of overwriting
        String tempName = _userXmlChoice.getAbsoluteFile() + ".xml";
        File tempFile   = new File(tempName);
        
        //if file without ext already exists
        if (tempFile.isFile())
        {
          int optionChoice = JOptionPane.showConfirmDialog(_applicationGui,
          "Do you want to overwrite the existing file?", "Save",
          JOptionPane.YES_NO_CANCEL_OPTION);
        
          if (optionChoice == 0)
          {
            //save a file that has not got a user added extension as XML
            if (tempExt == null)
            {
              this.internalSaveProcess(_userXmlChoice.getAbsolutePath() +
              ".xml");              
              _applicationGui.getMenuSaveXML().setEnabled(true);
              _applicationGui.getSaveXmlButton().setEnabled(true);
            }
          }              
          else if (optionChoice == 1)
          {
            this.saveAs();
          }
        }     
        
        //save a file that has not got a user added extension as XML
        else if (tempExt == null)
        {
          this.internalSaveProcess(_userXmlChoice.getAbsolutePath() + ".xml");
          _applicationGui.getMenuSaveXML().setEnabled(true);
          _applicationGui.getSaveXmlButton().setEnabled(true);
        }
          
        //Save a file that does have a user added extension
        else 
        {        
          //check selected file is right type
          if ((tempExt.equalsIgnoreCase("xml")))
          {
            this.internalSaveProcess(_userXmlChoice.getAbsolutePath());
            _applicationGui.getMenuSaveXML().setEnabled(true);
            _applicationGui.getSaveXmlButton().setEnabled(true);
          }            
          else
          {
            Error.showMessage(_applicationGui, "XML file save error: graph " +
            "has not been saved because the chosen file is an incorrect type.");           
          }
        }
      }
    }
  }
  
  /**
   * An internal phase of the saveXmlGraph() method that is often repeated.
   * @param path
   */
  private void internalSaveProcess(String path)
  {
    //first, set choice point number counter
    for (int x = 0; x < _nodeArray.size(); x++)
    {
      Node tempNode = (Node) _nodeArray.get(x);
                
      if (tempNode.getName().startsWith("Choice Point"))
      {
        tempNode.setName("Choice Point" + _choicePointCounter);
        _nodeArray.set(_nodeArray.indexOf(tempNode), tempNode);
        _choicePointCounter = _choicePointCounter + 1;        
      }      
    }
              
    _choicePointCounter = 1;
          
    //second, create XMLParser object and get the blank document
    _xmlParser    = new XmlParser(_applicationGui);
    _document     = _xmlParser.getDocument();
          
    //third, create ProcessTree and send ArrayLists and Doc to it
    _processTree  = new ProcessTree(_applicationGui, _nodeArray, _edgeArray,
    _document);
          
    //fourth, create the document information and get it
    _processTree.createDocument();
    _document     = _processTree.getDocument();
          
    //lastly, send document to XMLParser and write out to a file
    _xmlParser.setDocument(_document);
    _xmlParser.writeXmlFile(path);
          
    JOptionPane.showMessageDialog(_applicationGui,"XML map graph saved " +
    "successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
    
    _applicationGui.getStatusBar().setText(new String[] {"Map graph saved."});
  }
  
  /**
   * A method to remove all the nodes and edges from the arrays and repaint.
   */
  public void removeAll()
  {
    //reset edge place marker and selected node colours
    _applicationGui.setWhichNode(1);
    this.resetSelectedNodeColour();
 
    int nodeCounter = _nodeArray.size();
    int edgeCounter = _edgeArray.size();

    //remove all nodes
    if (_nodeArray.size() != 0)
    {
      for (int x = 0; x <= nodeCounter; x++)
      {
        _nodeArray.remove(0);
        x = 0;
        nodeCounter = nodeCounter - 1;
      }
      
      _noOfNodes = 0;            
    }

    //remove all edges
    if (_edgeArray.size() != 0)
    {
      for (int y = 0; y <= edgeCounter; y++)
      {
        _edgeArray.remove(0);
        y = 0;
        edgeCounter = edgeCounter - 1;
      }     
    }
    
    //clear buildingList, disable menus and repaint
    _buildingsList.removeAll();
    
    _applicationGui.getMenuInsertEdge().setEnabled(false);
    _applicationGui.getMenuMoveNode().setEnabled(false);
    _applicationGui.getMenuNameNode().setEnabled(false);
    _applicationGui.getMenuRemoveItem().setEnabled(false);
    _applicationGui.getMenuRemoveAll().setEnabled(false);
    
    _applicationGui.getMenuHideGraph().setEnabled(false);
    _applicationGui.getMenuShowGraph().setEnabled(false);
    
    _applicationGui.getMenuRun().setEnabled(false);
    
    _applicationGui.getInsertEdgeButton().setEnabled(false);
    _applicationGui.getMoveNodeButton().setEnabled(false);
    _applicationGui.getNameNodeButton().setEnabled(false);
    _applicationGui.getRemoveItemButton().setEnabled(false);
    _applicationGui.getRemoveAllButton().setEnabled(false);
    
    _applicationGui.getHideGraphButton().setEnabled(false);
    _applicationGui.getShowGraphButton().setEnabled(false);
    
    _applicationGui.getGetRouteButton().setEnabled(false);
    
    _applicationGui.getDrawingPanel().paintAll();
    _applicationGui.getStatusBar().setText(new String[]
    {"All existing nodes and edges have been removed."});
  }
  
  /**
   * Create the list of buildings for the GetRoute dialog box.
   */
  public void createBuildingsList()
  {
    //clear list to begin with
    _buildingsList.removeAll();
    
    for (int x = 0; x < _nodeArray.size(); x++)
    {
      Node tempNode = (Node) _nodeArray.get(x);        
      
      if (!tempNode.getName().startsWith("Choice Point"))
      {
        _buildingsList.add(tempNode.getName());        
      }
    }
    
    //sort the list
    _tempBuildingsArray = _buildingsList.getItems();
    String sortString   = "";
    Arrays.sort(_tempBuildingsArray, sortString.CASE_INSENSITIVE_ORDER);
    _buildingsList.removeAll();
                
    for (int y = 0; y < _tempBuildingsArray.length; y++)
    {
      _buildingsList.add(_tempBuildingsArray[y]);
    } 
  }
  
  /**
   * Method to open a map image file.
   */
  public void openMapImage()
  {
    //determine if any user placed nodes exist or not
    if (this.checkForNodes("Open Map"))
    {
      //open File dialog and set appropriate filters
      _imageChooser.setDialogTitle("Open map image of type GIF, JPG or PNG");
      _imageChooser.setFileFilter(_imageFilter);
      _imageChooser.setAcceptAllFileFilterUsed(false);    
      _fcVal = _imageChooser.showOpenDialog(_applicationGui);
                 
      if (_fcVal == JFileChooser.APPROVE_OPTION)
      {
        _userMapChoice = _imageChooser.getSelectedFile();
        String tempExt = FileFilterUtilities.getExtension(_userMapChoice);
        
        if (tempExt == null)
        {
          tempExt = "no extension";
        }
              
        //check selected file is right type and exists
        if (((tempExt.equals("jpg")) || (tempExt.equals("gif")) ||
           (tempExt.equals("png"))) && ((_userMapChoice.isFile())))
        {
          this.removeAll();
          _applicationGui.getDrawingPanel().displayMap();
        
          if (_image != null)
          {
            _nodePlaced = false;
            _applicationGui.getDrawingPanel().remove
            (_applicationGui.getIntroTextPane());
            
            _applicationGui.getMenuOpenXML().setEnabled(true);
            _applicationGui.getMenuSaveXML().setEnabled(false);
            _applicationGui.getMenuSaveAsXML().setEnabled(true);
            
            _applicationGui.getMenuEdit().setEnabled(true);
            _applicationGui.getMenuInsertEdge().setEnabled(false);
            _applicationGui.getMenuMoveNode().setEnabled(false);
            _applicationGui.getMenuNameNode().setEnabled(false);
            _applicationGui.getMenuRemoveItem().setEnabled(false);
            _applicationGui.getMenuRemoveAll().setEnabled(false);
            
            _applicationGui.getMenuHideGraph().setEnabled(false);
            _applicationGui.getMenuShowGraph().setEnabled(false);
            _applicationGui.getMenuZoom().setEnabled(true);
            
            _applicationGui.getMenuRun().setEnabled(false);
            
            _applicationGui.getOpenXmlButton().setEnabled(true);
            _applicationGui.getSaveXmlButton().setEnabled(false);
            _applicationGui.getSaveAsXmlButton().setEnabled(true);                     
                    
            _applicationGui.getInsertNodeButton().setEnabled(true);
            _applicationGui.getInsertEdgeButton().setEnabled(false);
            _applicationGui.getMoveNodeButton().setEnabled(false);
            _applicationGui.getNameNodeButton().setEnabled(false);
            _applicationGui.getRemoveItemButton().setEnabled(false);
            _applicationGui.getRemoveAllButton().setEnabled(false);          
            
            _applicationGui.getHideGraphButton().setEnabled(false);
            _applicationGui.getShowGraphButton().setEnabled(false);    
            _applicationGui.getZoomButton().setEnabled(true);            
                        
            _applicationGui.getGetRouteButton().setEnabled(false);
            
            _applicationGui.getDrawingPanel().revalidate();
            _applicationGui.getStatusBar().setText
            (new String[] {"Map image open."});
          }
        }      
        else
        {
          Error.showMessage(_applicationGui, "Image file read error: the file " +
          "could be an incorrect type or not exist.");
        }             
      }
    }
  }
  
  /**
   * Method for opening an XML file of graph nodes and edges. 
   */
  public void openXmlGraph()
  {
    //determine if any user placed nodes exist or not
    if (this.checkForNodes("Open XML"))    
    {  
      //open File dialog and set appropriate filters
      _xmlChooser.setDialogTitle("Open map graph XML file");
      _xmlChooser.setFileFilter(_xmlFilter);
      _xmlChooser.setAcceptAllFileFilterUsed(false);      
      _fcVal = _xmlChooser.showOpenDialog(_applicationGui);
            
      if (_fcVal == JFileChooser.APPROVE_OPTION)
      {
        _userXmlChoice = _xmlChooser.getSelectedFile();
        String tempExt = FileFilterUtilities.getExtension(_userXmlChoice);
          
        if (tempExt == null)
        {
          tempExt = "no extension";
        }
          
        //check selected file is right type and exists
        if ((tempExt.equalsIgnoreCase("xml")) && (_userXmlChoice.isFile()))
        {
          //parse the XML file and process
          _xmlParser    = new XmlParser(_applicationGui);
          _xmlParser.loadXmlFile(_userXmlChoice.getAbsolutePath());
          _document     = _xmlParser.getDocument();      
          _processTree  = new ProcessTree(_applicationGui);
          _processTree.setDocument(_document);      
          _processTree.processDocument();
                      
          //after processing, check XML is a map graph for this application
          if (_processTree.getCorrectTitle())
          {
            boolean   larger        = false;
            ArrayList tempNodeArray = new ArrayList();
            tempNodeArray           = _processTree.getNodes();
                          
            //check if XML graph is larger than the map image
            for (int x = 0; x < tempNodeArray.size(); x++)
            {
              Node tempNode = (Node) tempNodeArray.get(x);
                
              if ((tempNode.getCoOrdinates().x >= _image.getWidth()-8) || 
              (tempNode.getCoOrdinates().y >= _image.getHeight()-8))
              {
                larger = true;
              }
            }
              
            if (!larger)
            {
              //remove all existing nodes
              this.removeAll();
              
              //set new node and edge arrays
              _nodeArray = _processTree.getNodes();
              _edgeArray = _processTree.getEdges();
              _noOfNodes = _nodeArray.size();
              
              //display the graph by repainting panel and set the builingList
              _applicationGui.getDrawingPanel().paintAll();
              this.createBuildingsList();          
              _nodePlaced = true;
              
              //enable menus if appropriate
              if (_nodeArray.size() == 0)
              {
                _applicationGui.getMenuSaveXML().setEnabled(true);
                
                _applicationGui.getMenuInsertEdge().setEnabled(false);
                _applicationGui.getMenuMoveNode().setEnabled(false);
                _applicationGui.getMenuNameNode().setEnabled(false);
                _applicationGui.getMenuRemoveItem().setEnabled(false);
                _applicationGui.getMenuRemoveAll().setEnabled(false);
                
                _applicationGui.getMenuHideGraph().setEnabled(false);
                _applicationGui.getMenuShowGraph().setEnabled(false);
                
                _applicationGui.getMenuRun().setEnabled(false);
                
                _applicationGui.getSaveXmlButton().setEnabled(true);
                
                _applicationGui.getInsertEdgeButton().setEnabled(false);
                _applicationGui.getMoveNodeButton().setEnabled(false);
                _applicationGui.getNameNodeButton().setEnabled(false);
                _applicationGui.getRemoveItemButton().setEnabled(false);
                _applicationGui.getRemoveAllButton().setEnabled(false);
                
                _applicationGui.getHideGraphButton().setEnabled(false);
                _applicationGui.getShowGraphButton().setEnabled(false);
                
                _applicationGui.getGetRouteButton().setEnabled(false);
                
                _applicationGui.getStatusBar().setText
                (new String[] {"Map graph open (0 nodes placed)."});
              }
              else if (_nodeArray.size() == 1)
              {
                _applicationGui.getMenuSaveXML().setEnabled(true);
                
                _applicationGui.getMenuInsertEdge().setEnabled(false);
                _applicationGui.getMenuMoveNode().setEnabled(true);
                _applicationGui.getMenuNameNode().setEnabled(true);
                _applicationGui.getMenuRemoveItem().setEnabled(true);
                _applicationGui.getMenuRemoveAll().setEnabled(true);
                
                _applicationGui.getMenuHideGraph().setEnabled(true);
                _applicationGui.getMenuShowGraph().setEnabled(true);
                
                _applicationGui.getMenuRun().setEnabled(false);
                
                _applicationGui.getSaveXmlButton().setEnabled(true);
                
                _applicationGui.getInsertEdgeButton().setEnabled(false);
                _applicationGui.getMoveNodeButton().setEnabled(true);
                _applicationGui.getNameNodeButton().setEnabled(true);
                _applicationGui.getRemoveItemButton().setEnabled(true);
                _applicationGui.getRemoveAllButton().setEnabled(true);
                
                _applicationGui.getHideGraphButton().setEnabled(true);
                _applicationGui.getShowGraphButton().setEnabled(true);
                
                _applicationGui.getGetRouteButton().setEnabled(false);
                
                _applicationGui.getStatusBar().setText
                (new String[] {"Map graph open (1 node placed)."});
              }
              else if (_nodeArray.size() > 1)
              {
                _applicationGui.getMenuSaveXML().setEnabled(true);
                
                _applicationGui.getMenuInsertEdge().setEnabled(true);
                _applicationGui.getMenuMoveNode().setEnabled(true);
                _applicationGui.getMenuNameNode().setEnabled(true);
                _applicationGui.getMenuRemoveItem().setEnabled(true);
                _applicationGui.getMenuRemoveAll().setEnabled(true);
                
                _applicationGui.getMenuHideGraph().setEnabled(true);
                _applicationGui.getMenuShowGraph().setEnabled(true);
                
                _applicationGui.getMenuRun().setEnabled(true);
                
                _applicationGui.getSaveXmlButton().setEnabled(true);
                
                _applicationGui.getInsertEdgeButton().setEnabled(true);
                _applicationGui.getMoveNodeButton().setEnabled(true);
                _applicationGui.getNameNodeButton().setEnabled(true);
                _applicationGui.getRemoveItemButton().setEnabled(true);
                _applicationGui.getRemoveAllButton().setEnabled(true);
                
                _applicationGui.getHideGraphButton().setEnabled(true);
                _applicationGui.getShowGraphButton().setEnabled(true);
                
                _applicationGui.getGetRouteButton().setEnabled(true);
                
                _applicationGui.getStatusBar().setText
                (new String[] {"Map graph open (multiple nodes placed)."});
              }
            }            
            else
            {
              Error.showMessage(_applicationGui, "XML file was not created in" +
              " conjunction with this\nmap image as some of the nodes lie" +
              " outside\nof the image itself.");          
            }           
          }          
          else
          {
            Error.showMessage(_applicationGui, "XML file was not created by " +
            "this application.");         
          }               
        }        
        else
        {
          Error.showMessage(_applicationGui, "XML file load error: the file " + 
          "could be an incorrect type or not exist.");       
        }                              
      }
    }
  }
  
  /**
   * Method to check for existing nodes on the drawingPanel.
   * @return boolean
   * @param title
   */
  public boolean checkForNodes(String title)
  {
    if (_nodeArray.size() >= 1)
    {
      _nodePlaced = true;
    }      
    else
    {
      _nodePlaced = false;
    }
      
    //if nodes exist offer the option to save current XML graph first
    if (_nodePlaced)
    {
      int optionChoice = JOptionPane.showConfirmDialog(_applicationGui,
      "Do you want to save the XML graph first?", title, 
      JOptionPane.YES_NO_CANCEL_OPTION);

      if (optionChoice == 0)
      {
        this.saveAs();
        return true;
      }
      else if (optionChoice == 1)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    else
    {
      return true;
    }
  }
    
  /**
   * Display the get route dialog and run Dijkstra's algorithm.
   */
  public void getRoute()
  {
    if (this.createGraph())
    {    
      //check if _buildingsList has at least two names
      if (_buildingsList.getItemCount() <= 1)
      {
        Error.showMessage(_applicationGui,
        "You must name at least two buildings.");     
      }      
      else
      {
        GetRouteDialog getRouteDialog = new GetRouteDialog(_applicationGui, 
        "Get Route", true);
        getRouteDialog.setRouteNodeArray(_nodeArray);
        getRouteDialog.setRouteBuildingsList(_buildingsList);
        getRouteDialog.setBuildingList();      
        getRouteDialog.setLocationRelativeTo(_applicationGui);
        getRouteDialog.getLeaveOpenCheckBox().setSelected(_leaveOpen);
        getRouteDialog.getDisplayFullGraphCheckBox().setSelected
        (_dispayFullGraph);
        getRouteDialog.getStartChoice().select(_listStartIndex);
        getRouteDialog.getGoalChoice().select(_listGoalIndex);
        
        if (_dialogLocation != null)
        {
          getRouteDialog.setLocation(_dialogLocation);
        }
        
        getRouteDialog.setVisible(true);
          
        //if btnOk clicked on dialog run Dijkstra's algorithm
        if (getRouteDialog.getRouteRunDijkstra())
        {
          _startChoice      = getRouteDialog.getRouteStartChoice();
          _goalChoice       = getRouteDialog.getRouteGoalChoice();
          _startChoiceText  = getRouteDialog.getRouteStartChoiceText();
          _goalChoiceText   = getRouteDialog.getRouteGoalChoiceText();
          
          //remove all edges stored from previous routes
          int removeEdgeCounter = _resultingEdges.size();
        
          if (_resultingEdges.size() != 0)
          {
            for (int x = 0; x <= removeEdgeCounter; x++)
            {
              _resultingEdges.remove(0);
              x = 0;
              removeEdgeCounter = removeEdgeCounter - 1;
            }
          }
          
          //execute Dijkstra run and ensure graph is connected
          boolean graphConnected = this.runDijkstra();

          if (!graphConnected)
          {
            Error.showMessage(_applicationGui, "This graph is not fully " +
            "connected: the seperate elements\nmust join together to form " + 
            "one complete and connected graph.");          
          }        
          else
          {                    
            //cycle through string array of Dijkstra paths from source
            for (int y = 0; y < _dijkstraResults.length; y++)
            {
              String tempString = _dijkstraResults[y];
    
              //if path from source to goal is located in array process here
              if (tempString.endsWith(_goalChoiceText + "]"))
              {
                //split string into seperate nodes stored in string array
                String[] result = tempString.split(", ");
    
                for (int z = 0; z < result.length; z++)
                {
                  if (result[z].startsWith("["))
                  {
                    for (int i = 0; i < _nodeArray.size(); i++)
                    {
                      Node tempNode = (Node) _nodeArray.get(i);
                        
                      if (tempNode.getName().equals(result[z].substring(1)))
                      {
                        _tempStartNode = tempNode.getNumber();
                        _flag = "end";
                      }
                    }
                  }
                  else if (result[z].endsWith("]"))
                  {
                    for (int i = 0; i < _nodeArray.size(); i++)
                    {
                      Node tempNode = (Node) _nodeArray.get(i);
                      String[] result2 = result[z].split("]");
                        
                      if (tempNode.getName().equals(result2[0]))
                      {
                        _tempEndNode = tempNode.getNumber();
                        for (int j = 0; j < _edgeArray.size(); j++)
                        {
                          Edge tempEdge = (Edge) _edgeArray.get(j);
                            
                          if (tempEdge.getStartNode() == _tempStartNode && 
                          tempEdge.getEndNode()       == _tempEndNode)
                          {
                            _resultingEdges.add(tempEdge);
                          }
                        }
                      }
                    }
                  }
                  else if (_flag.equals("end"))
                  {
                    _flag = "start";
                    for (int i = 0; i < _nodeArray.size(); i++)
                    {
                      Node tempNode = (Node) _nodeArray.get(i);
                        
                      if (tempNode.getName().equals(result[z]))
                      {
                        _tempEndNode = tempNode.getNumber();                      
                        for (int j = 0; j < _edgeArray.size(); j++)
                        {
                          Edge tempEdge = (Edge) _edgeArray.get(j);
                            
                          if (tempEdge.getStartNode() == _tempStartNode && 
                          tempEdge.getEndNode()       == _tempEndNode)
                          {
                            _resultingEdges.add(tempEdge);
                          }
                        }
                      }
                    }
                  }
    
                  if (_flag.equals("start"))
                  {
                    _flag = "end";
                    for (int k = 0; k < _nodeArray.size(); k++)
                    {
                      Node tempNode = (Node) _nodeArray.get(k);
                        
                      if (tempNode.getName().equals(result[z]))
                      {
                        _tempStartNode = tempNode.getNumber();
                      }
                    }
                  }
                }
              }
            }
            //finally paint the route
            _dispayFullGraph = getRouteDialog.getDisplayFullGraph();
            _applicationGui.getDrawingPanel().paintRoute();
            _applicationGui.getStatusBar().setText(new String[]
            {"The selected route is displayed."});
            
            //set variables if leaving window open
            if (getRouteDialog.getLeaveOpen())
            {
              _leaveOpen                = true;
              _listStartIndex           = getRouteDialog.getStartIndex();
              _listGoalIndex            = getRouteDialog.getGoalIndex();
              _dialogLocation           = getRouteDialog.getLocation();
                            
              this.getRoute();
            }
            else
            {
              _leaveOpen                = false;
              _listStartIndex           = 0;
              _listGoalIndex            = 0;
              _dialogLocation           = null;
            }
          }
        }
      }
    }
  }
  
  /**
   * Reset node for new edge values and repaint.
   */
  public void resetSelectedNodeColour()
  {
    for (int i = 0; i < _nodeArray.size(); i++)
    {
      Node tempNode = (Node) _nodeArray.get(i);
      tempNode.setForEdge(false);
      _nodeArray.set(i, tempNode);
      
      _applicationGui.getDrawingPanel().paintAll();     
    }
  }
  
  /**
   * Get the boolean indicating whether or not a node has been placed.
   * @return boolean
   */
  public boolean getNodePlaced()
  {
    return _nodePlaced;
  }
  
  /**
   * Set the boolean indicating whether or not a node has been placed.
   * @param placed
   */
  public void setNodePlaced(boolean placed)
  {
    _nodePlaced = placed;
  }
  
  /**
   * Get the number of nodes in the node array.
   * @return int
   */
  public int getNoOfNodes()
  {
    return _noOfNodes;
  }
  
  /**
   * Set the number of nodes in the node array.
   * @param number
   */
  public void setNoOfNodes(int number)
  {
    _noOfNodes = number;
  } 
  
  /**
   * Get the node array.
   * @return ArrayList
   */
  public ArrayList getNodeArray()
  {
    return _nodeArray;
  }
  
  /**
   * Set the node array.
   * @param nodes
   */
  public void setNodeArray(ArrayList nodes)
  {
    _nodeArray = nodes;
  }
  
  /**
   * Get the edge array.
   * @return ArrayList
   */
  public ArrayList getEdgeArray()
  {
    return _edgeArray;
  }
  
  /**
   * Set the edge array.
   * @param edges
   */
  public void setEdgeArray(ArrayList edges)
  {
    _edgeArray = edges;
  }
  
  /**
   * Get the resultingEdges array.
   * @return ArrayList
   */
  public ArrayList getResultingEdges()
  {
    return _resultingEdges;
  }
  
  /**
   * Set the resultingEdges array.
   * @param resultingEdgesArray
   */
  public void setResultingEdges(ArrayList resultingEdgesArray)
  {
    _resultingEdges = resultingEdgesArray;
  }
  
  /**
   * Get the buildings list.
   * @return List
   */
  public List getBuildingsList()
  {
    return _buildingsList;
  }
  
  /**
   * Set the buildings list.
   * @param buildings
   */
  public void setBuildingsList(List buildings)
  {
    _buildingsList = buildings;
  }
  
  /**
   * Get the zoom factor.
   * @return int
   */
  public int getZoomFactor()
  {
    return _zoom;
  }
  
  /**
   * Set the zoom factor.
   * @param zoomFactor
   */
  public void setZoomFactor(int zoomFactor)
  {
    _zoom = zoomFactor;
  }
  
  /**
   * Get the mouse button clicked.
   * @return int
   */
  public int getMouseButton()
  {
    return _mouseBtn;
  }
  
  /**
   * Set the mouse button clicked.
   * @param button
   */
  public void setMouseButton(int button)
  {
    _mouseBtn = button;
  }
  
  /**
   * Get the map image
   * @return BufferedImage
   */
  public BufferedImage getImage()
  {
    return _image;
  }
  
  /**
   * Set the map image.
   * @param newImage
   */
  public void setImage(BufferedImage newImage)
  {
    _image = newImage;
  }
  
  /**
   * Get the map file choice.
   * @return File
   */
  public File getMapFile()
  {
    return _userMapChoice;
  }
  
  /**
   * Set the map file choice.
   * @param file
   */
  public void setMapFile(File file)
  {
    _userMapChoice = file;
  }  
  
  /**
   * Get the boolean that determines whether or not the full map will be drawn
   * with a route.
   * @return boolean
   */
  public boolean getDispayFullGraph()
  {
    return _dispayFullGraph;
  }
}