package applicationpackage;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * A class that listens to mouse events on a DrawingPanel.
 */
class DrawingPanelMouseListener implements MouseListener, MouseMotionListener
{
  ApplicationGui              _applicationGui;
  ApplicationGuiUtilities     _applicationGuiUtilities;
      
  //for mouse events
  private int                 _mouseX;
  private int                 _mouseY;
  private int                 _nodeToMove;
  private Point               _returnTempPoint;
  private Point               _tempPoint;
  private boolean             _nodeExists;
  private boolean             _nodeClickedOn;   
  int                         _startNodeNo;
  Point                       _edgeStartPoint;
  Point                       _edgeEndPoint;            
        
  /**
   * Constructor.
   * @param applicationGuiUtilities
   * @param applicationGui
   */
  public DrawingPanelMouseListener(ApplicationGui applicationGui)
  {
    _applicationGui           = applicationGui;   
    _applicationGuiUtilities  = _applicationGui.getApplicationGuiUtilities();      
    _returnTempPoint          = new Point();
    _edgeStartPoint           = new Point();
    _edgeEndPoint             = new Point();
    _nodeExists               = false;
    _nodeClickedOn            = false;
    _nodeToMove               = -1;
    _startNodeNo              = 0;
  }    

  /**
   * The start of a mouse dragged operation on the DrawingPanel.
   * @param e
   */
  public void mousePressed(MouseEvent e)
  {
    this.mouseEventSetup(e.getX(), e.getY());
                
    //move node menu selected
    if (_applicationGui.getUserOptionChoice() == 3)
    {
      _nodeClickedOn = false;

      for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
      {
        Node tempNode   = (Node) _applicationGuiUtilities.getNodeArray().get(x);
        _nodeClickedOn  = _applicationGui.getDrawingPanel().checkNodeClicked
        (_tempPoint, tempNode);

        if (_nodeClickedOn)
        {
          //set the node to be moved - utilised in mouseDragged()
          _nodeToMove   = x;
          _returnTempPoint.setLocation(tempNode.getCoOrdinates());          
        }
      }
    }
  }
    
  /**
   * Mouse dragging on the DrawingPanel.
   * @param e
   */
  public void mouseDragged(MouseEvent e)
  {
    this.mouseEventSetup(e.getX(), e.getY());
          
    //move node menu selected
    if (_applicationGui.getUserOptionChoice() == 3)
    {
      if (_nodeToMove != -1)
      {
        Node tempNode = (Node) _applicationGuiUtilities.getNodeArray().get
        (_nodeToMove);
        tempNode.getCoOrdinates().setLocation(_tempPoint);
        _applicationGuiUtilities.getNodeArray().set
        (_applicationGuiUtilities.getNodeArray().indexOf(tempNode), tempNode);

        //reset any edge's points that are attached to the node
        for (int x = 0; x < _applicationGuiUtilities.getEdgeArray().size(); x++)
        {
          Edge tempEdge = (Edge) _applicationGuiUtilities.getEdgeArray().get(x);

          if (tempEdge.getStartNode() == tempNode.getNumber())
          {
            tempEdge.getStartNodePoint().setLocation(_tempPoint);
            tempEdge.setStartNodeX(_tempPoint.getX());
            tempEdge.setStartNodeY(_tempPoint.getY());
            tempEdge.setWeight(_applicationGuiUtilities.calculateWeight
            (tempEdge.getStartNodePoint(), tempEdge.getEndNodePoint()));
            _applicationGuiUtilities.getEdgeArray().set
            (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge),
            tempEdge);
          }
          else if (tempEdge.getEndNode() == tempNode.getNumber())
          {
            tempEdge.getEndNodePoint().setLocation(_tempPoint);
            tempEdge.setEndNodeX(_tempPoint.getX());
            tempEdge.setEndNodeY(_tempPoint.getY());
            tempEdge.setWeight(_applicationGuiUtilities.calculateWeight
            (tempEdge.getStartNodePoint(), tempEdge.getEndNodePoint()));
            _applicationGuiUtilities.getEdgeArray().set
            (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge),
            tempEdge);
          }
        }        
        _applicationGui.getDrawingPanel().paintAll();
        
        //Set the status bar.
        _applicationGui.getStatusBar().setText
        (new String[] {"The node " + tempNode.getName() +
        " has been moved."});
      }
    }
  }
    
  /**
   * The end of a mouse dragged operation on the DrawingPanel.
   * @param e
   */
  public void mouseReleased(MouseEvent e)
  {
    this.mouseEventSetup(e.getX(), e.getY());
                
    //move node menu selected
    if (_applicationGui.getUserOptionChoice() == 3)
    {
      _nodeClickedOn  = false;
      _nodeExists     = false;
        
      if (_applicationGuiUtilities.getImage() != null)
      {
        //nodes cannot be placed outside the image border
        if (_mouseX >= _applicationGuiUtilities.getImage().getWidth()-8 |
        _mouseY >= _applicationGuiUtilities.getImage().getHeight()-8)
        {
          Error.showMessage(_applicationGui, "You cannot place a node here.");
            
          Node tempNode = (Node) _applicationGuiUtilities.getNodeArray().get
          (_nodeToMove);
          tempNode.getCoOrdinates().setLocation(_returnTempPoint);
          _applicationGuiUtilities.getNodeArray().set
          (_applicationGuiUtilities.getNodeArray().indexOf(tempNode), tempNode);
  
          //reset any edge's points that are attached to the node
          for (int x = 0; x < _applicationGuiUtilities.getEdgeArray().size(); x++)
          {
            Edge tempEdge = (Edge) _applicationGuiUtilities.getEdgeArray().get
            (x);
  
            if (tempEdge.getStartNode() == tempNode.getNumber())
            {
              tempEdge.getStartNodePoint().setLocation(_returnTempPoint);
              tempEdge.setStartNodeX(_returnTempPoint.getX());
              tempEdge.setStartNodeY(_returnTempPoint.getY());
              tempEdge.setWeight(_applicationGuiUtilities.calculateWeight
              (tempEdge.getStartNodePoint(), tempEdge.getEndNodePoint()));
              _applicationGuiUtilities.getEdgeArray().set
              (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge),
              tempEdge);
            }  
            else if (tempEdge.getEndNode() == tempNode.getNumber())
            {
              tempEdge.getEndNodePoint().setLocation(_returnTempPoint);
              tempEdge.setEndNodeX(_returnTempPoint.getX());
              tempEdge.setEndNodeY(_returnTempPoint.getY());
              tempEdge.setWeight(_applicationGuiUtilities.calculateWeight
              (tempEdge.getStartNodePoint(), tempEdge.getEndNodePoint()));
              _applicationGuiUtilities.getEdgeArray().set
              (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge),
              tempEdge);
            }
          }
          _applicationGui.getDrawingPanel().paintAll();       
          
          //Set the status bar.
          _applicationGui.getStatusBar().setText
          (new String[] {"The node " + tempNode.getName() +
          " has been moved back to its previous position."});
        }        
      }

      //nodes cannot be placed on top of other nodes
      for (int y = 0;  y < _applicationGuiUtilities.getNodeArray().size(); y++)
      {
        Node tempNode2  = (Node) _applicationGuiUtilities.getNodeArray().get(y);
        _nodeClickedOn  = _applicationGui.getDrawingPanel().checkNodeClicked
        (_tempPoint, tempNode2);

        if (_nodeClickedOn)
        {
          if (y != _nodeToMove)
          {
            Error.showMessage(_applicationGui,
            "A node has already been placed here.");
             
            Node tempNode3 = (Node) _applicationGuiUtilities.getNodeArray().get
            (_nodeToMove);
            tempNode3.getCoOrdinates().setLocation(_returnTempPoint);
            _applicationGuiUtilities.getNodeArray().set
            (_applicationGuiUtilities.getNodeArray().indexOf(tempNode3),
            tempNode3);

            //reset any edge's points that are attached to the node
            for (int z = 0; z < _applicationGuiUtilities.getEdgeArray().size(); z++)
            {
              Edge tempEdge2 = (Edge) _applicationGuiUtilities.getEdgeArray()
              .get(z);

              if (tempEdge2.getStartNode() == tempNode3.getNumber())
              {
                tempEdge2.getStartNodePoint().setLocation(_returnTempPoint);
                tempEdge2.setStartNodeX(_returnTempPoint.getX());
                tempEdge2.setStartNodeY(_returnTempPoint.getY());
                tempEdge2.setWeight(_applicationGuiUtilities.calculateWeight
                (tempEdge2.getStartNodePoint(), tempEdge2.getEndNodePoint()));
                _applicationGuiUtilities.getEdgeArray().set
                (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge2),
                tempEdge2);
              }
              else if (tempEdge2.getEndNode() == tempNode3.getNumber())
              {
                tempEdge2.getEndNodePoint().setLocation(_returnTempPoint);
                tempEdge2.setEndNodeX(_returnTempPoint.getX());
                tempEdge2.setEndNodeY(_returnTempPoint.getY());
                tempEdge2.setWeight(_applicationGuiUtilities.calculateWeight
                (tempEdge2.getStartNodePoint(), tempEdge2.getEndNodePoint()));
                _applicationGuiUtilities.getEdgeArray().set
                (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge2),
                tempEdge2);
              }
            }
            _applicationGui.getDrawingPanel().paintAll();
            
            //Set the status bar.
            _applicationGui.getStatusBar().setText
            (new String[] {"The node " + tempNode3.getName() +
            " has been moved back to its previous position."});
          }          
        }
      }             
    }      
    _nodeToMove = -1;      
  }
    
  /**
   * Mouse clicking on the DrawingPanel.
   * @param e
   */
  public void mouseClicked(MouseEvent e)
  {
    this.mouseEventSetup(e.getX(), e.getY());
    ArrayList removeTheseEdges = new ArrayList();
    int edgeWeight;
    
    // **insert node menu selected**
    if (_applicationGui.getUserOptionChoice() == 1)
    {
      _nodeClickedOn  = false;
      _nodeExists     = false;
        
      //nodes cannot be placed outside the image border
      if (_mouseX >= _applicationGuiUtilities.getImage().getWidth()-8 |
      _mouseY >= _applicationGuiUtilities.getImage().getHeight()-8)
      {
        Error.showMessage(_applicationGui, "You cannot place a node here.");          
      }        
      else
      {
        for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
        {
          Node tempNode   = (Node) _applicationGuiUtilities.getNodeArray().get
          (x);
          _nodeClickedOn  = _applicationGui.getDrawingPanel().checkNodeClicked
          (_tempPoint, tempNode);
              
          //nodes cannot be placed on top of other nodes
          if (_nodeClickedOn)
          {
            Error.showMessage(_applicationGui,
            "A node has already been placed here.");              
            _nodeExists   = true;
          }
        }
  
        if (!_nodeExists)
        {
          //all criteria are met - add node to array and paint
          Node tempNode2 = new Node
          (_applicationGuiUtilities.getNoOfNodes(), "Choice Point", _tempPoint);
          _applicationGuiUtilities.getNodeArray().add(tempNode2);
          _applicationGuiUtilities.setNoOfNodes
          (_applicationGuiUtilities.getNoOfNodes() + 1);
          _applicationGui.getDrawingPanel().paintAll();
          _applicationGui.getStatusBar().setText
          (new String[] {"A new node has been added."});
          
          //enable menus if appropriate
          if (_applicationGuiUtilities.getNodeArray().size() == 1)
          {
            _applicationGui.getMenuInsertEdge().setEnabled(false);
            _applicationGui.getMenuMoveNode().setEnabled(true);
            _applicationGui.getMenuNameNode().setEnabled(true);
            _applicationGui.getMenuRemoveItem().setEnabled(true);
            _applicationGui.getMenuRemoveAll().setEnabled(true);
            
            _applicationGui.getMenuHideGraph().setEnabled(true);
            _applicationGui.getMenuShowGraph().setEnabled(true);
            
            _applicationGui.getMenuRun().setEnabled(false);
            
            _applicationGui.getInsertEdgeButton().setEnabled(false);
            _applicationGui.getMoveNodeButton().setEnabled(true);
            _applicationGui.getNameNodeButton().setEnabled(true);
            _applicationGui.getRemoveItemButton().setEnabled(true);
            _applicationGui.getRemoveAllButton().setEnabled(true);
            
            _applicationGui.getHideGraphButton().setEnabled(true);
            _applicationGui.getShowGraphButton().setEnabled(true);
              
            _applicationGui.getGetRouteButton().setEnabled(false);
          }
          else if (_applicationGuiUtilities.getNodeArray().size() > 1)
          {
            _applicationGui.getMenuInsertEdge().setEnabled(true);
            _applicationGui.getMenuMoveNode().setEnabled(true);
            _applicationGui.getMenuNameNode().setEnabled(true);
            _applicationGui.getMenuRemoveItem().setEnabled(true);
            _applicationGui.getMenuRemoveAll().setEnabled(true);
            
            _applicationGui.getMenuHideGraph().setEnabled(true);
            _applicationGui.getMenuShowGraph().setEnabled(true);
            
            _applicationGui.getMenuRun().setEnabled(true);
            
            _applicationGui.getInsertEdgeButton().setEnabled(true);
            _applicationGui.getMoveNodeButton().setEnabled(true);
            _applicationGui.getNameNodeButton().setEnabled(true);
            _applicationGui.getRemoveItemButton().setEnabled(true);
            _applicationGui.getRemoveAllButton().setEnabled(true);
            
            _applicationGui.getHideGraphButton().setEnabled(true);
            _applicationGui.getShowGraphButton().setEnabled(true);
              
            _applicationGui.getGetRouteButton().setEnabled(true);
          }
        }
      }
    }
      
    // **insert edge menu selected**
    else if (_applicationGui.getUserOptionChoice() == 2)
    {
      //the first node that is clicked (start node)
      if (_applicationGui.getWhichNode() == 1)
      {
        _nodeClickedOn      = false;

        for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
        {
          Node tempNode     = (Node) _applicationGuiUtilities.getNodeArray().get
          (x);
          _nodeClickedOn    = _applicationGui.getDrawingPanel().checkNodeClicked
          (_tempPoint, tempNode);

          if (_nodeClickedOn)
          {
            _edgeStartPoint = tempNode.getCoOrdinates();
            _startNodeNo    = tempNode.getNumber();
            
            //set this node as first node for new edge and repaint its colour
            tempNode.setForEdge(true);
            _applicationGuiUtilities.getNodeArray().set(x, tempNode);
            _applicationGui.getDrawingPanel().paintAll();
            
            _applicationGui.setWhichNode(2);
          }
        }
      }
      
      //the second node clicked (end node)
      else if (_applicationGui.getWhichNode() == 2)
      {
        _nodeClickedOn      = false;

        for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
        {
          Node tempNode     = (Node) _applicationGuiUtilities.getNodeArray().get
          (x);
          _nodeClickedOn    = _applicationGui.getDrawingPanel().checkNodeClicked
          (_tempPoint, tempNode);

          if (_nodeClickedOn)
          {
            _edgeEndPoint   = tempNode.getCoOrdinates();
            edgeWeight      = _applicationGuiUtilities.calculateWeight
            (_edgeStartPoint, _edgeEndPoint);
            Edge tempEdge   = new Edge(_startNodeNo, tempNode.getNumber(),
            _edgeStartPoint, _edgeEndPoint, edgeWeight);

            //check for multiple edge entries - first edge is allowed entry
            if (_applicationGuiUtilities.getEdgeArray().size() == 0)
            {
              if (_startNodeNo == tempNode.getNumber())
              {
                Error.showMessage(_applicationGui,
                "The start and end nodes must not be the same.");                 
                  
                //reset edge place marker and selected node colours
                _applicationGui.setWhichNode(1);  
                _applicationGuiUtilities.resetSelectedNodeColour();
              }                
              else
              {
                _applicationGuiUtilities.getEdgeArray().add(tempEdge);
                Edge tempEdge2 = new Edge(tempNode.getNumber(), _startNodeNo, 
                _edgeEndPoint, _edgeStartPoint, edgeWeight);
                _applicationGuiUtilities.getEdgeArray().add(tempEdge2);
                
                //reset edge place marker and selected node colours
                _applicationGui.setWhichNode(1); 
                _applicationGuiUtilities.resetSelectedNodeColour();
                
                //Get the start node's name for setting the status.
                Node startNode = (Node) _applicationGuiUtilities.
                getNodeArray().get(_startNodeNo);                
                _applicationGui.getStatusBar().setText
                (new String[] {"A new edge has been added between nodes " + 
                startNode.getName() + " and " + tempNode.getName() + "."});
              }
            }
            else
            {
              boolean edgeExists = false;

              for (int y = 0;  y < _applicationGuiUtilities.getEdgeArray().size(); y++)
              {
                Edge tempEdge2 = (Edge) _applicationGuiUtilities.getEdgeArray()
                .get(y);

                //multiple edge entries are not allowed
                if (_startNodeNo        == tempEdge2.getStartNode() &
                   tempNode.getNumber() == tempEdge2.getEndNode())
                {
                  Error.showMessage(_applicationGui,
                  "This edge has already been placed.");                   
                  edgeExists = true;
                    
                  //reset edge place marker and selected node colours
                  _applicationGui.setWhichNode(1);
                  _applicationGuiUtilities.resetSelectedNodeColour();
                }
              }

              if (!edgeExists)
              {
                //the end node must also not be the start node
                if (_startNodeNo == tempNode.getNumber())
                {
                  Error.showMessage(_applicationGui,
                  "The start and end nodes must not be the same.");
                                       
                  //reset edge place marker and selected node colours
                  _applicationGui.setWhichNode(1);  
                  _applicationGuiUtilities.resetSelectedNodeColour();
                }                  
                else
                {
                  //all criteria are met - add edge to array and paint
                  _applicationGuiUtilities.getEdgeArray().add(tempEdge);
                    
                  //add same edge but reversed, to ensure graph is undirected
                  Edge tempEdge3 = new Edge(tempNode.getNumber(), _startNodeNo,
                  _edgeEndPoint, _edgeStartPoint, edgeWeight);
                  _applicationGuiUtilities.getEdgeArray().add(tempEdge3);
                  
                  //reset edge place marker and selected node colours
                  _applicationGui.setWhichNode(1); 
                  _applicationGuiUtilities.resetSelectedNodeColour();
                  
                  //Get the start node's name for setting the status.
                  Node startNode = (Node) _applicationGuiUtilities.
                  getNodeArray().get(_startNodeNo);                
                  _applicationGui.getStatusBar().setText
                  (new String[] {"A new edge has been added between nodes " + 
                  startNode.getName() + " and " + tempNode.getName() + "."});
                }
              }
            }
          }
        }
      }
    }

    // **name node menu selected**
    else if (_applicationGui.getUserOptionChoice() == 5)
    {
      _nodeClickedOn = false;

      for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
      {
        Node tempNode   = (Node) _applicationGuiUtilities.getNodeArray().get(x);
        _nodeClickedOn  = _applicationGui.getDrawingPanel().checkNodeClicked
        (_tempPoint, tempNode);

        if (_nodeClickedOn)
        {
          BuildingsDialog buildingsDialog = new BuildingsDialog
          (_applicationGui, "Name node", true);
                        
          //if node is a CP display "Choice Point" and not its number
          if (tempNode.getName().startsWith("Choice Point"))
          {
            buildingsDialog.setNodeNameText("Choice Point");
          }            
          else
          {
            buildingsDialog.setNodeNameText(tempNode.getName());
          }           
            
          buildingsDialog.setLocationRelativeTo(_applicationGui);
          buildingsDialog.setVisible(true);
          boolean updateNodeName = true;

          //if btn OK clicked on buildings dialog
          if (buildingsDialog.getBtnChoice() == 1)
          {
            //check node name is not same as new node name
            if ((!buildingsDialog.getBuildingChoice().startsWith
               ("Choice Point")) && (tempNode.getName().equals
               (buildingsDialog.getBuildingChoice())))
            {
              Error.showMessage(_applicationGui,
              "The new node name is the same as the old name.");               
              updateNodeName = false;
            }              
              
            //check new name does not exist in an existing node
            else if (!buildingsDialog.getBuildingChoice().startsWith
                    ("Choice Point"))
            {
              for (int y = 0; y < _applicationGuiUtilities.getBuildingsList()
              .getItemCount(); y++)
              {
                if (buildingsDialog.getBuildingChoice().equals
                   (_applicationGuiUtilities.getBuildingsList().getItem(y)))
                {
                  Error.showMessage(_applicationGui,
                  "The new node name already exists on another node.");
                  updateNodeName = false;
                }
              }    
            }           
              
            //check new name does not include "choice point" as text
            if (buildingsDialog.getBuildingChoice().length() >= 12)
            {
              if (!buildingsDialog.getBuildingChoice().equals
                 ("Choice Point"))
              {
                if (buildingsDialog.getBuildingChoice().substring(0,12)
                   .equalsIgnoreCase("choice point"))
                {
                  JOptionPane.showMessageDialog(_applicationGui,
                  "This node's name will be set to 'Choice Point'.",
                  "Name Node", JOptionPane.INFORMATION_MESSAGE);
                    
                  buildingsDialog.setBuildingChoice("Choice Point");
                  updateNodeName = true;
                }
              }
            }
              
            //update the selected node's name if updateNodeName is true
            if (updateNodeName)
            {
              //set the new name for the Node and its place in array
              String tempName = tempNode.getName();
              tempNode.setName(buildingsDialog.getBuildingChoice());
              _applicationGuiUtilities.getNodeArray().set
              (_applicationGuiUtilities.getNodeArray().indexOf(tempNode),
              tempNode);
              _applicationGuiUtilities.createBuildingsList();              
                
              //repaint node colour if name no longer Choice Point 
              _applicationGui.getDrawingPanel().paintAll();  
              
              //Set the status bar.
              _applicationGui.getStatusBar().setText
              (new String[] {"The node " + tempName +
              " has been renamed to " + tempNode.getName() + "."});
            }                        
          }
        }
      }
    }

    // **remove item menu selected**
    else if (_applicationGui.getUserOptionChoice() == 4)
    {
      _nodeClickedOn                      = false;
      boolean edgeClickedOn               = false;

      //check for edges that are clicked on
      for (int x = 0;  x < _applicationGuiUtilities.getEdgeArray().size(); x++)
      {
        Edge tempEdge   = (Edge) _applicationGuiUtilities.getEdgeArray().get(x);
        edgeClickedOn   = _applicationGui.getDrawingPanel().checkEdgeClicked
        (_tempPoint, tempEdge);

        if (edgeClickedOn)
        {
          int tempStart = tempEdge.getStartNode();
          int tempEnd   = tempEdge.getEndNode();

          _applicationGuiUtilities.getEdgeArray().remove
          (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge));

          for (int y = 0;  y < _applicationGuiUtilities.getEdgeArray().size(); y++)
          {
            Edge tempEdge2 = (Edge) _applicationGuiUtilities.getEdgeArray().get
            (y);
              
            if (tempEdge2.getStartNode()  == tempEnd &
                tempEdge2.getEndNode()    == tempStart)
            {
              _applicationGuiUtilities.getEdgeArray().remove
              (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge2));
            }
          }

          //remove drawing of edge by calling repaint with updated edges
          _applicationGui.getDrawingPanel().paintAll();
          
          //Set the status bar.
          Node startNode  = (Node) _applicationGuiUtilities.getNodeArray().get(tempStart);
          Node endNode    = (Node) _applicationGuiUtilities.getNodeArray().get(tempEnd);
          
          _applicationGui.getStatusBar().setText
          (new String[] {"The edge between node " + startNode.getName() +
          " and node " + endNode.getName() + " has been removed."});
        }
      }

      //check for nodes that are clicked on
      for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
      {
        Node tempNode   = (Node) _applicationGuiUtilities.getNodeArray().get(x);
        _nodeClickedOn  = _applicationGui.getDrawingPanel().checkNodeClicked
        (_tempPoint, tempNode);

        if (_nodeClickedOn)
        {
          //remove edges connected to the node that was clicked on
          if (!_applicationGuiUtilities.getEdgeArray().isEmpty())
          {
            int tempEdgeCounter = _applicationGuiUtilities.getEdgeArray()
            .size();

            for (int y = 0; y < tempEdgeCounter; y++)
            {
              Edge deleteEdge   = new Edge();
              deleteEdge        = (Edge) _applicationGuiUtilities.getEdgeArray()
              .get(y);

              if (deleteEdge.getStartNodePoint().equals
                 (tempNode.getCoOrdinates()))
              {
                removeTheseEdges.add(deleteEdge);
              }                
              else if (deleteEdge.getEndNodePoint().equals
                      (tempNode.getCoOrdinates()))
              {
                removeTheseEdges.add(deleteEdge);
              }
            }
          }

          //remove the actual nodes and edges from the arrays
          for (int z = 0; z < removeTheseEdges.size(); z++)
          {
            _applicationGuiUtilities.getEdgeArray().remove
            (removeTheseEdges.get(z));
          }

          _applicationGuiUtilities.getNodeArray().remove
          (_applicationGuiUtilities.getNodeArray().indexOf(tempNode));

          //remove drawing of node by calling repaint with updated nodes
          _applicationGui.getDrawingPanel().paintAll();
          
          //Set the status bar.
          _applicationGui.getStatusBar().setText
          (new String[] {"The node " + tempNode.getName() +
          " and any connecting edges have been removed."});

          //remove the node's name from the buildingsList
          if (!tempNode.getName().startsWith("Choice Point"))
          {
            _applicationGuiUtilities.createBuildingsList();              
          }

          //replace the node's number by re-applying noOfNodes
          for (int j = 0; j < _applicationGuiUtilities.getNodeArray().size(); j++)
          {
            Node tempNode2 = (Node) _applicationGuiUtilities.getNodeArray().get
            (j);
            tempNode2.setNumber(j);
            _applicationGuiUtilities.getNodeArray().set(j, tempNode2);
          }

          _applicationGuiUtilities.setNoOfNodes
          (_applicationGuiUtilities.getNoOfNodes() - 1);

          //replace each edge's start/end numbers
          for (int i = 0; i < _applicationGuiUtilities.getEdgeArray().size(); i++)
          {
            Edge tempEdge2 = (Edge) _applicationGuiUtilities.getEdgeArray().get
            (i);
              
            if (tempEdge2.getStartNode() > tempNode.getNumber())
            {
              tempEdge2.setStartNode(tempEdge2.getStartNode() - 1);
            }

            if (tempEdge2.getEndNode() > tempNode.getNumber())
            {
              tempEdge2.setEndNode(tempEdge2.getEndNode() - 1);
            }
              
            _applicationGuiUtilities.getEdgeArray().set
            (_applicationGuiUtilities.getEdgeArray().indexOf(tempEdge2),
            tempEdge2);
          }
        }
      }
      
      //disbale menus if appropriate
      if (_applicationGuiUtilities.getNodeArray().size() == 1)
      {
        _applicationGui.getMenuInsertEdge().setEnabled(false);
        _applicationGui.getMenuMoveNode().setEnabled(true);
        _applicationGui.getMenuNameNode().setEnabled(true);
        _applicationGui.getMenuRemoveItem().setEnabled(true);
        _applicationGui.getMenuRemoveAll().setEnabled(true);
            
        _applicationGui.getMenuHideGraph().setEnabled(true);
        _applicationGui.getMenuShowGraph().setEnabled(true);
            
        _applicationGui.getMenuRun().setEnabled(false);
        
        _applicationGui.getInsertEdgeButton().setEnabled(false);
        _applicationGui.getMoveNodeButton().setEnabled(true);
        _applicationGui.getNameNodeButton().setEnabled(true);
        _applicationGui.getRemoveItemButton().setEnabled(true);
        _applicationGui.getRemoveAllButton().setEnabled(true);
            
        _applicationGui.getHideGraphButton().setEnabled(true);
        _applicationGui.getShowGraphButton().setEnabled(true);
              
        _applicationGui.getGetRouteButton().setEnabled(false);       
      }
      else if (_applicationGuiUtilities.getNodeArray().size() == 0)
      {
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
      }
    }
      
    // **zoom in/out menu selected**
    else if (_applicationGui.getUserOptionChoice() == 6)
    {
      //get the mouse button (left or right) that was clicked
      if (e.getModifiers() == MouseEvent.BUTTON1_MASK)
      {
        _applicationGuiUtilities.setMouseButton(0);        
      }                    
      else
      {
        _applicationGuiUtilities.setMouseButton(1);
      }
        
      //run the zoom method for the image's drawingPanel
      _applicationGui.getDrawingPanel().zoom(1);
        
      //zoom code different as it needs to scroll to correct co-ordinates
      if (_applicationGuiUtilities.getZoomFactor() != 1)
      {
        if (_applicationGuiUtilities.getZoomFactor() > 1)
        {
          _mouseX = _mouseX * _applicationGuiUtilities.getZoomFactor();
          _mouseY = _mouseY * _applicationGuiUtilities.getZoomFactor();            
        }        
        else if (_applicationGuiUtilities.getZoomFactor() < 1)
        {
          _mouseX = _mouseX / _applicationGuiUtilities.getZoomFactor();
          _mouseY = _mouseY / _applicationGuiUtilities.getZoomFactor();            
        }
          
        //three Points to devise how the mouse click point is centred
        Point p1 = new Point(_mouseX, _mouseY);
        Point p2 = new Point(p1.x, p1.y - 
        (_applicationGui.getFrameJScrollPane().getViewport().getHeight() / 2));
        Point p3 = new Point(p2.x - 
        (_applicationGui.getFrameJScrollPane().getViewport().getWidth() / 2),
        p2.y);        
        _applicationGui.getFrameJScrollPane().getViewport().setViewPosition(p3);          
      }         
    }
  }
    
  /**
   * Mouse entering the DrawingPanel.
   * @param e
   */
  public void mouseEntered(MouseEvent e)
  {
    // **zoom in/out menu selected**
    if (_applicationGui.getUserOptionChoice() == 6)
    {
      _applicationGui.getDrawingPanel().setCursor
      (new Cursor(Cursor.CROSSHAIR_CURSOR));      
    }
  }

  /**
   * Mouse exiting the DrawingPanel.
   * @param e
   */
  public void mouseExited(MouseEvent e)
  {
    // **zoom in/out menu selected**
    if (_applicationGui.getUserOptionChoice() == 6)
    {
      _applicationGui.getDrawingPanel().setCursor
      (new Cursor(Cursor.DEFAULT_CURSOR));
    }
  } 
  
  /**
   * Mouse moving around the DrawingPanel.
   * @param e
   */
  public void mouseMoved(MouseEvent e)
  {                  
  }
    
  /**
   * A private setup method for all mouse events in this class.
   * @param y
   * @param x
   */
  private void mouseEventSetup(int x, int y)
  {
    _mouseX = x;
    _mouseY = y;
      
    if (_applicationGuiUtilities.getZoomFactor() != 1)
    {
      if (_applicationGuiUtilities.getZoomFactor() > 1)
      {
        _mouseX = _mouseX / _applicationGuiUtilities.getZoomFactor();
        _mouseY = _mouseY / _applicationGuiUtilities.getZoomFactor();
      }        
      else if (_applicationGuiUtilities.getZoomFactor() < 1)
      {
        _mouseX = _mouseX * _applicationGuiUtilities.getZoomFactor();
        _mouseY = _mouseY * _applicationGuiUtilities.getZoomFactor();
      }        
    }
      
    _tempPoint = new Point(_mouseX, _mouseY);
  }
} 