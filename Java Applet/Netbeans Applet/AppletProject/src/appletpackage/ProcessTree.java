package appletpackage;
import java.awt.Point;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class ProcessTree 
{
    /** Generic attributes */
    Document ptDoc;
    NodeList myNodes, myNodes2, myNodes3;
    ArrayList ptNodes = new ArrayList();
    ArrayList ptEdges = new ArrayList();
  
    /** Node object attributes */
    int nodeNumber;
    String nodeName;
    int xPos;
    int yPos;
    Point nodePoint;
    Point nodeCentre; 
   
    /** Edge object attributes */
    int startNode;
    int endNode;
    int edgeWeight;
    double startNodeX;
    double startNodeY;
    double endNodeX;
    double endNodeY;
    Point startPoint = new Point();
    Point endPoint = new Point();  
  
    /** Default constructor */
    public ProcessTree()
    {
    }
  
    /** Constructor for use by gui when creating new XML file */
    public ProcessTree(ArrayList nodeArrayIn,ArrayList edgeArrayIn,Document docIn)
    {
        ptNodes = nodeArrayIn;
        ptEdges = edgeArrayIn;
        ptDoc = docIn;  
    }  
  
    public void setDocument(Document newDocIn)
    {
        ptDoc = newDocIn;
    }
  
    public Document getDocument()
    {
        return ptDoc;
    }
  
    public ArrayList getNodes()
    {
        return ptNodes;
    }
  
    public ArrayList getEdges()
    {
        return ptEdges;
    }
  
    public void processDocument()
    {
        myNodes = ptDoc.getChildNodes();
    
        /** Extract the root element "Map" */
        for (int x = 0; x < myNodes.getLength(); x++)
        {
            org.w3c.dom.Node xmlNode = myNodes.item(x);
            myNodes2 = xmlNode.getChildNodes();
      
            /** Extract the nodes "Node" and "Edge" */
            for (int y = 0; y < myNodes2.getLength(); y++)
            {
                org.w3c.dom.Node xmlNode2 = myNodes2.item(y);
        
                if (xmlNode2.getNodeName().equals("Node"))
                {
                    /** Extract all the child nodes for each "Node" element */
                    myNodes3 = xmlNode2.getChildNodes();
                    for (int z = 0; z < myNodes3.getLength(); z++)
                    {
                        org.w3c.dom.Node xmlNode3 = myNodes3.item(z);
            
                        if (xmlNode3.getNodeName().equals("Number"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            nodeNumber = Integer.parseInt(xmlNode4.getNodeValue());
                        }
            
                        else if (xmlNode3.getNodeName().equals("Name"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            nodeName = xmlNode4.getNodeValue();
                        }

                        else if (xmlNode3.getNodeName().equals("XPosition"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            xPos = Integer.parseInt(xmlNode4.getNodeValue());
                        }
            
                        else if (xmlNode3.getNodeName().equals("YPosition"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            yPos = Integer.parseInt(xmlNode4.getNodeValue());
              
                            /** Calculate point objects for this node */
                            nodePoint = new Point(xPos,yPos);
                            nodeCentre = new Point();
                            nodeCentre.setLocation(nodePoint.x + 7.5, nodePoint.y + 7.5);
              
                            /** Create the Node object and add to array */
                            Node tempNode = new Node(nodeNumber,nodeName,nodePoint,
                                                    nodeCentre);
                            ptNodes.add(tempNode);
                        }
                    }
                }
        
                else if (xmlNode2.getNodeName().equals("Edge"))
                {
                    /** Extract all the child nodes for each "Edge" element */
                    myNodes3 = xmlNode2.getChildNodes();
                    for (int z = 0; z < myNodes3.getLength(); z++)
                    {
                        org.w3c.dom.Node xmlNode3 = myNodes3.item(z);
            
                        if (xmlNode3.getNodeName().equals("StartNode"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            startNode = Integer.parseInt(xmlNode4.getNodeValue());
                        }
            
                        else if (xmlNode3.getNodeName().equals("EndNode"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            endNode = Integer.parseInt(xmlNode4.getNodeValue());
                        }

                        else if (xmlNode3.getNodeName().equals("Weight"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            edgeWeight = Integer.parseInt(xmlNode4.getNodeValue());
                        }
            
                        else if (xmlNode3.getNodeName().equals("StartNodeX"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            startNodeX = Double.parseDouble(xmlNode4.getNodeValue());
                        }

                        else if (xmlNode3.getNodeName().equals("StartNodeY"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            startNodeY = Double.parseDouble(xmlNode4.getNodeValue());
                        }

                        else if (xmlNode3.getNodeName().equals("EndNodeX"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            endNodeX = Double.parseDouble(xmlNode4.getNodeValue());
                        }
            
                        else if (xmlNode3.getNodeName().equals("EndNodeY"))
                        {
                            org.w3c.dom.Node xmlNode4 = xmlNode3.getFirstChild();
                            endNodeY = Double.parseDouble(xmlNode4.getNodeValue());

                            /** Calculate point objects for this edge */
                            startPoint = new Point();
                            startPoint.setLocation(startNodeX,startNodeY);
                            endPoint = new Point();
                            endPoint.setLocation(endNodeX,endNodeY);

                            /** Create the Edge object and add to array */
                            Edge tempEdge = new Edge(startNode,endNode,startPoint,endPoint,
                                                    edgeWeight);
                            ptEdges.add(tempEdge);              
                        }
                    }
                }
            }
        }
    }
  
    public void createDocument()
    {
        /** Create route element and attach */
        Element root = ptDoc.createElement("Map");
        ptDoc.appendChild(root);
    
        /** Run through nodes and attch elements to the document */   
        for (int x = 0; x < ptNodes.size(); x++)
        {
            Node tempNode = (Node) ptNodes.get(x);
            Element nodeElement = ptDoc.createElement("Node");
            root.appendChild(nodeElement);
      
            Element numberElement = ptDoc.createElement("Number");
            Text numberText = ptDoc.createTextNode("" + tempNode.getNodeNo());
            numberElement.appendChild(numberText);
            nodeElement.appendChild(numberElement);
      
            Element nameElement = ptDoc.createElement("Name");
            Text nameText = ptDoc.createTextNode(tempNode.getNodeName());
            nameElement.appendChild(nameText);
            nodeElement.appendChild(nameElement);
      
            Element xElement = ptDoc.createElement("XPosition");
            Text xText = ptDoc.createTextNode("" + tempNode.getXPos());
            xElement.appendChild(xText);
            nodeElement.appendChild(xElement);
      
            Element yElement = ptDoc.createElement("YPosition");
            Text yText = ptDoc.createTextNode("" + tempNode.getYPos());
            yElement.appendChild(yText);
            nodeElement.appendChild(yElement);     
        }
    
        /** Run through edges and attch elements to the document */   
        for (int y = 0; y < ptEdges.size(); y++)
        {
            Edge tempEdge = (Edge) ptEdges.get(y);
            Element edgeElement = ptDoc.createElement("Edge");
            root.appendChild(edgeElement);
      
            Element startNodeElement = ptDoc.createElement("StartNode");
            Text startNodeText = ptDoc.createTextNode("" + tempEdge.getStartNode());
            startNodeElement.appendChild(startNodeText);
            edgeElement.appendChild(startNodeElement);
      
            Element endNodeElement = ptDoc.createElement("EndNode");
            Text endNodeText = ptDoc.createTextNode("" + tempEdge.getEndNode());
            endNodeElement.appendChild(endNodeText);
            edgeElement.appendChild(endNodeElement);
      
            Element weightElement = ptDoc.createElement("Weight");
            Text weightText = ptDoc.createTextNode("" + tempEdge.getWeight());
            weightElement.appendChild(weightText);
            edgeElement.appendChild(weightElement);
      
            Element startNodeXElement = ptDoc.createElement("StartNodeX");
            Text startNodeXText = ptDoc.createTextNode("" + tempEdge.getStartNodeX());
            startNodeXElement.appendChild(startNodeXText);
            edgeElement.appendChild(startNodeXElement);
      
            Element startNodeYElement = ptDoc.createElement("StartNodeY");
            Text startNodeYText = ptDoc.createTextNode("" + tempEdge.getStartNodeY());
            startNodeYElement.appendChild(startNodeYText);
            edgeElement.appendChild(startNodeYElement);
      
            Element endNodeXElement = ptDoc.createElement("EndNodeX");
            Text endNodeXText = ptDoc.createTextNode("" + tempEdge.getEndNodeX());
            endNodeXElement.appendChild(endNodeXText);
            edgeElement.appendChild(endNodeXElement);
      
            Element endNodeYElement = ptDoc.createElement("EndNodeY");
            Text endNodeYText = ptDoc.createTextNode("" + tempEdge.getEndNodeY());
            endNodeYElement.appendChild(endNodeYText);
            edgeElement.appendChild(endNodeYElement);     
        }
    }
}