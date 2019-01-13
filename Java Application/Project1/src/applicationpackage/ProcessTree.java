package applicationpackage;

import java.awt.*;
import java.util.*;

import org.w3c.dom.*;

/**
 * A class that processes XML document objects.
 */
public class ProcessTree
{
  //global attributes  
  private Document  _document;
  private NodeList  _xmlNodes;
  private NodeList  _xmlNodes2;
  private NodeList  _xmlNodes3;
  private ArrayList _nodeArray;
  private ArrayList _edgeArray;
  private boolean   _correctTitle;
  private Component _parent;

  //node object attributes
  private int       _nodeNumber;
  private String    _nodeName;
  private int       _xPosition;
  private int       _yPosition;
  private Point     _nodePoint;
  
  //edge object attributes
  private int       _startNode;
  private int       _endNode;
  private int       _edgeWeight;
  private double    _startNodeX;
  private double    _startNodeY;
  private double    _endNodeX;
  private double    _endNodeY;
  private Point     _startPoint;
  private Point     _endPoint;

  /**
   * Constructor.
   */
  public ProcessTree(Component parent)
  {
    _parent       = parent;
    _nodeArray    = new ArrayList();
    _edgeArray    = new ArrayList();
    _correctTitle = false;    
  }  
    
  /**
   * Constructor.
   * @param document
   * @param edgeArray
   * @param nodeArray
   * @param parent
   */
  public ProcessTree(Component parent, ArrayList nodeArray, ArrayList edgeArray,
  Document document)
  {
    _parent       = parent;
    _nodeArray    = nodeArray;
    _edgeArray    = edgeArray;
    _correctTitle = false;
    _document     = document;
  }

  /**
   * Set the document.
   * @param document
   */
  public void setDocument(Document document)
  {
    _document = document;
  }

  /**
   * Get the document.
   * @return Document
   */
  public Document getDocument()
  {
    return _document;
  }

  /**
   * Get the Node ArrayList.
   * @return ArrayList
   */
  public ArrayList getNodes()
  {
    return _nodeArray;
  }

  /**
   * Get the Edge ArrayList.
   * @return ArrayList
   */
  public ArrayList getEdges()
  {
    return _edgeArray;
  } 
  
  /**
   * Get the value indicating whether or not the XML document contains the
   * correct title for use with this application.
   * @return boolean
   */
  public boolean getCorrectTitle()
  {
    return _correctTitle;
  }

  /**
   * A method that is used to load an XML file into the ApplicationGui.
   */
  public void processDocument()
  {
    _xmlNodes = _document.getChildNodes();

    //extract the root element "Campus_Map_Tool_and_Route_Planner"
    for (int x = 0; x < _xmlNodes.getLength(); x++)
    {
      org.w3c.dom.Node xmlNode = _xmlNodes.item(x);
      
      if (xmlNode.getNodeName().equals("Campus_Map_Tool_and_Route_Planner"))
      {
        _correctTitle = true;
      }
      
      _xmlNodes2 = xmlNode.getChildNodes();

      //extract the nodes "Node" and "Edge"
      for (int y = 0; y < _xmlNodes2.getLength(); y++)
      {
        org.w3c.dom.Node xmlNode2 = _xmlNodes2.item(y);

        if (xmlNode2.getNodeName().equals("Node"))
        {
          //extract all the child nodes for each "Node" element
          _xmlNodes3 = xmlNode2.getChildNodes();
          
          for (int z = 0; z < _xmlNodes3.getLength(); z++)
          {
            org.w3c.dom.Node xmlNode3   = _xmlNodes3.item(z);

            try
            {            
              if (xmlNode3.getNodeName().equals("Number"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _nodeNumber               = Integer.parseInt
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("Name"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _nodeName                 = xmlNode4.getNodeValue();
              }
              else if (xmlNode3.getNodeName().equals("XPosition"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _xPosition                = Integer.parseInt
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("YPosition"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _yPosition                = Integer.parseInt
                (xmlNode4.getNodeValue());
  
                //calculate point object for this node
                _nodePoint                = new Point(_xPosition, _yPosition);
                
                //create the Node object and add to array
                Node tempNode             = new Node(_nodeNumber, _nodeName,
                _nodePoint);
                _nodeArray.add(tempNode);
              }
            }
            catch (NumberFormatException ex)
            {
              Error.showMessage(_parent, "Error processing XML document: \n" +
              ex.getMessage() + "\nReason:\n" + ex.toString());
              ex.printStackTrace();
            }
          }
        }
        else if (xmlNode2.getNodeName().equals("Edge"))
        {
          //extract all the child nodes for each "Edge" element
          _xmlNodes3 = xmlNode2.getChildNodes();
          
          for (int z = 0; z < _xmlNodes3.getLength(); z++)
          {
            org.w3c.dom.Node xmlNode3   = _xmlNodes3.item(z);

            try
            {
              if (xmlNode3.getNodeName().equals("StartNode"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _startNode                = Integer.parseInt
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("EndNode"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _endNode                  = Integer.parseInt
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("Weight"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _edgeWeight               = Integer.parseInt
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("StartNodeX"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _startNodeX               = Double.parseDouble
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("StartNodeY"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _startNodeY               = Double.parseDouble
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("EndNodeX"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _endNodeX                 = Double.parseDouble
                (xmlNode4.getNodeValue());
              }
              else if (xmlNode3.getNodeName().equals("EndNodeY"))
              {
                org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                _endNodeY                 = Double.parseDouble
                (xmlNode4.getNodeValue());
  
                //calculate point objects for this edge
                _startPoint               = new Point();
                _startPoint.setLocation(_startNodeX, _startNodeY);
                _endPoint                 = new Point();
                _endPoint.setLocation(_endNodeX, _endNodeY);
  
                //create the Edge object and add to array
                Edge tempEdge             = new Edge(_startNode, _endNode, 
                _startPoint, _endPoint, _edgeWeight);
                _edgeArray.add(tempEdge);
              }
            }
            catch (NumberFormatException ex)
            {
              Error.showMessage(_parent, "Error processing XML document: \n" 
              + ex.getMessage() + "\nReason:\n" + ex.toString());      
              ex.printStackTrace();
            }
          }
        }
      }
    }
  }

 /**
   * A method that is used for saving node and edge arrays into an XML file.
   */
  public void createDocument()
  {
    try
    {
      //create route element and attach
      Element root = _document.createElement
      ("Campus_Map_Tool_and_Route_Planner");
      _document.appendChild(root);
  
      //run through nodes and attch elements to the document
      for (int x = 0; x < _nodeArray.size(); x++)
      {
        Node tempNode             = (Node) _nodeArray.get(x);
        Element nodeElement       = _document.createElement("Node");
        root.appendChild(nodeElement);
  
        Element numberElement     = _document.createElement("Number");
        Text numberText           = _document.createTextNode
        ("" + tempNode.getNumber());
        numberElement.appendChild(numberText);
        nodeElement.appendChild(numberElement);
  
        Element nameElement       = _document.createElement("Name");
        Text nameText             = _document.createTextNode
        (tempNode.getName());
        nameElement.appendChild(nameText);
        nodeElement.appendChild(nameElement);
  
        Element xElement          = _document.createElement("XPosition");
        Text xText                = _document.createTextNode
        ("" + tempNode.getCoOrdinates().x);
        xElement.appendChild(xText);
        nodeElement.appendChild(xElement);
  
        Element yElement          = _document.createElement("YPosition");
        Text yText                = _document.createTextNode
        ("" + tempNode.getCoOrdinates().y);
        yElement.appendChild(yText);
        nodeElement.appendChild(yElement);
      }
  
      //run through edges and attch elements to the document
      for (int y = 0; y < _edgeArray.size(); y++)
      {
        Edge tempEdge             = (Edge) _edgeArray.get(y);
        Element edgeElement       = _document.createElement("Edge");
        root.appendChild(edgeElement);
  
        Element startNodeElement  = _document.createElement("StartNode");
        Text startNodeText        = _document.createTextNode
        ("" + tempEdge.getStartNode());
        startNodeElement.appendChild(startNodeText);
        edgeElement.appendChild(startNodeElement);
  
        Element endNodeElement    = _document.createElement("EndNode");
        Text endNodeText          = _document.createTextNode
        ("" + tempEdge.getEndNode());
        endNodeElement.appendChild(endNodeText);
        edgeElement.appendChild(endNodeElement);
  
        Element weightElement     = _document.createElement("Weight");
        Text weightText           = _document.createTextNode
        ("" + tempEdge.getWeight());
        weightElement.appendChild(weightText);
        edgeElement.appendChild(weightElement);
  
        Element startNodeXElement = _document.createElement("StartNodeX");
        Text startNodeXText       = _document.createTextNode
        ("" + tempEdge.getStartNodeX());
        startNodeXElement.appendChild(startNodeXText);
        edgeElement.appendChild(startNodeXElement);
  
        Element startNodeYElement = _document.createElement("StartNodeY");
        Text startNodeYText       = _document.createTextNode
        ("" + tempEdge.getStartNodeY());
        startNodeYElement.appendChild(startNodeYText);
        edgeElement.appendChild(startNodeYElement);
  
        Element endNodeXElement   = _document.createElement("EndNodeX");
        Text endNodeXText         = _document.createTextNode
        ("" + tempEdge.getEndNodeX());
        endNodeXElement.appendChild(endNodeXText);
        edgeElement.appendChild(endNodeXElement);
  
        Element endNodeYElement   = _document.createElement("EndNodeY");
        Text endNodeYText         = _document.createTextNode
        ("" + tempEdge.getEndNodeY());
        endNodeYElement.appendChild(endNodeYText);
        edgeElement.appendChild(endNodeYElement);
      }
    }
    catch (DOMException ex)
    {
      Error.showMessage(_parent, "Error creating XML document: \n" 
      + ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
    }
  }
}