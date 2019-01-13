package university2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.w3c.dom.Document;
import java.net.URL;
import java.net.URLConnection;
import appletpackage.*;

public class ProcessMobileImage
{
    /** Campus map JPG **********************************************************/
    DrawingPanel drawingPanel = new DrawingPanel();   
   
    /** Drawing *****************************************************************/
    ArrayList nodeArray = new ArrayList();
    ArrayList edgeArray = new ArrayList();  
    ArrayList buildings = new ArrayList();
    BufferedImage scaledImage;
    String type;
    int northX, northY, southX, southY, eastX, eastY, westX, westY;
    boolean firstTime = true;
       
    /** Dijkstra results ********************************************************/
    MapGraph myMapGraph;
    Dijkstra myDijkstra = new Dijkstra();
    int[] myPred;
    ArrayList resultingEdges = new ArrayList();
    int startChoice = -1;
    String startChoiceText;
    int goalChoice = -1;
    String goalChoiceText;
    int screenWidth;
    int screenHeight;
    boolean runDijkstra = false;
    String[] dijkstraResults;
    String flag;
    int tempStartNode;
    int tempEndNode;

    /** XML processing **********************************************************/
    URL myUrl;
    XmlParser myXmlParser = new XmlParser();
    ProcessTree myProcessTree = new ProcessTree();
    Document myDocument;

    public ProcessMobileImage(URL xmlMapIn)
    {
        try
        {
            myUrl = xmlMapIn;
            constructImage();
        }
    
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
  
    public void constructImage() throws Exception, IOException
    {
        /** XML processing 1 */
        //URL myUrl = appletpackage.AppletGui.class.getResource("Map.xml");
        openURL(myUrl);

        /** XML processing 2 */
        myDocument = myXmlParser.getDocument();
        myProcessTree.setDocument(myDocument);

        /** XML processing 3 */
        myProcessTree.processDocument();
        nodeArray = myProcessTree.getNodes();
        edgeArray = myProcessTree.getEdges();

        drawingPanel.setPreferredSize(new Dimension(1031,705)); 
        String[] listNodes = new String[nodeArray.size()];    
            
        for (int x = 0; x < nodeArray.size(); x++)
        {
            Node tempNode = (Node) nodeArray.get(x);
            listNodes[x] = tempNode.getNodeName();
        }
    
        Arrays.sort(listNodes);
       
        for (int x = 0; x < listNodes.length; x++)
        {
            if (listNodes[x].startsWith("Choice Point") == false)
            {
                buildings.add(listNodes[x]);            
            }
        }
    
        /** Create the graph */
        createGraph();
    }
  
    /**************************************************************************/

    public void getRoute()
    {
        /** Remove all edges stored from previous routes */
        int removeEdgeCounter = resultingEdges.size();
        
        if (resultingEdges.size() != 0)
        {
            for (int w = 0; w <= removeEdgeCounter; w++)
            {
                resultingEdges.remove(0);
                w = 0;
                removeEdgeCounter = removeEdgeCounter - 1;
            }
        }

        if (runDijkstra == true)
        {
            boolean nowContinue;
            nowContinue = runDijkstra();

            /** Check if the graph is fully connected and leave if not */
            if (nowContinue != true)
            {
                System.out.println("This graph is not fully connected");        
            }

            else
            {
                /** Cycle through string array of Dijkstra paths from source */
                for (int x = 0; x < dijkstraResults.length; x++)
                {
                    String tempString = dijkstraResults[x];

                    /** If path from source to goal is located in array process here */
                    if (tempString.endsWith(goalChoiceText + "]"))
                    {
                        /** Split string into seperate nodes stored in string array */
                        String[] result = tempString.split(", ");

                        for (int y = 0; y < result.length; y++)
                        {
                            if (result[y].startsWith("["))
                            {
                                for (int jj = 0; jj < nodeArray.size(); jj++)
                                {
                                    Node tempNode = (Node) nodeArray.get(jj);
                                    if (tempNode.getNodeName().equals(result[y].substring(1)))
                                    {
                                        tempStartNode = tempNode.getNodeNo();
                                        flag = "end";
                                    }
                                }
                            }

                            else if (result[y].endsWith("]"))
                            {
                                for (int jj = 0; jj < nodeArray.size(); jj++)
                                {
                                    Node tempNode = (Node) nodeArray.get(jj);
                                    String[] result2 = result[y].split("]");
                                    if (tempNode.getNodeName().equals(result2[0]))
                                    {
                                        tempEndNode = tempNode.getNodeNo();
                                        for (int t = 0; t < edgeArray.size(); t++)
                                        {
                                            Edge tempEdge = (Edge) edgeArray.get(t);
                                            if (tempEdge.getStartNode() == tempStartNode &&
                                            tempEdge.getEndNode() == tempEndNode)
                                            {
                                                resultingEdges.add(tempEdge);
                                            }
                                        }
                                    }
                                }
                            }

                            else if (flag.equals("end"))
                            {
                                flag = "start";
                                for (int jj = 0; jj < nodeArray.size(); jj++)
                                {
                                    Node tempNode = (Node) nodeArray.get(jj);
                                    if (tempNode.getNodeName().equals(result[y]))
                                    {
                                        tempEndNode = tempNode.getNodeNo();
                                        for (int t = 0; t < edgeArray.size(); t++)
                                        {
                                            Edge tempEdge = (Edge) edgeArray.get(t);
                                            if (tempEdge.getStartNode() == tempStartNode &&
                                            tempEdge.getEndNode() == tempEndNode)
                                            {
                                                resultingEdges.add(tempEdge);
                                            }
                                        }
                                    }
                                }
                            }

                            if (flag.equals("start"))
                            {
                                flag = "end";
                                for (int jj = 0; jj < nodeArray.size(); jj++)
                                {
                                    Node tempNode = (Node) nodeArray.get(jj);
                                    if (tempNode.getNodeName().equals(result[y]))
                                    {
                                        tempStartNode = tempNode.getNodeNo();
                                    }
                                }
                            }
                        } //end for
                    } //end if
                } //end for

                for (int z = 0;  z < resultingEdges.size(); z++)
                {
                    Edge drawEdge = (Edge) resultingEdges.get(z);
                    System.out.println("Shortest path route edges: " +
                    drawEdge.getStartNode() + " " + drawEdge.getEndNode() + " " +
                    drawEdge.getWeight());
          
                    if (type.equals("Sub-Section Zoom") == true)
                    {
                        double temp1 = drawEdge.getStartNodeX();
                        double temp2 = drawEdge.getStartNodeY();
                        double temp3 = drawEdge.getEndNodeX();
                        double temp4 = drawEdge.getEndNodeY();
          
                        if (firstTime == true)
                        {              
                            northX = (int) temp1;
                            northY = (int) temp2;
                            southX = (int) temp1;
                            southY = (int) temp2;
                            eastX = (int) temp1;
                            eastY = (int) temp2;
                            westX = (int) temp1;
                            westY = (int) temp2;
                    
                            firstTime = false;
                        }
          
                        else
                        {
                            /** north */
                            if (temp2 < northY)
                            {
                                northX = (int) temp1;  
                                northY = (int) temp2;
                            }
                    
                            if(temp4 < northY)
                            {
                                northX = (int) temp3;
                                northY = (int) temp4;
                            }

                            /** south */
                            if (temp2 > southY)
                            {
                                southX = (int) temp1;  
                                southY = (int) temp2;
                            }
                            if(temp4 > southY)
                            {
                                southX = (int) temp3;
                                southY = (int) temp4;
                            }

                            /** east */
                            if (temp1 > eastX)
                            {
                                eastX = (int) temp1;  
                                eastY = (int) temp2;
                            }

                            if(temp3 > eastX)
                            {
                                eastX = (int) temp3;
                                eastY = (int) temp4;
                            }

                            /** west */
                            if (temp1 < westX)
                            {
                                westX = (int) temp1;  
                                westY = (int) temp2;
                            }

                            if(temp3 < westX)
                            {
                                westX = (int) temp3;
                                westY = (int) temp4;
                            }
                        }
                    }
                }  
        
                northY = northY - 20;
                southY = southY + 20;
                eastX = eastX + 20;
                westX = westX - 20;
        
                if (southY > 705)
                {
                    southY = 705;
                }
        
                drawingPanel.paintDijkstra();
            } //end else
        } //end if
    } //end getRoute()

    /**************************************************************************/
  
    public void createGraph()
    {
        myMapGraph = new MapGraph(nodeArray.size());

        for (int y = 0; y < nodeArray.size(); y++)
        {
            Node tempNode = (Node) nodeArray.get(y);
            myMapGraph.addNode(tempNode.getNodeNo(),tempNode.getNodeName());
        }

        for (int z = 0; z < edgeArray.size(); z++)
        {
            Edge tempEdge = (Edge) edgeArray.get(z);
            myMapGraph.addEdge(tempEdge.getStartNode(),tempEdge.getEndNode(),
            tempEdge.getWeight());
        }
    }
  
    /**************************************************************************/
  
    public ArrayList getBuildings()
    {
        return buildings;
    }
  
    /**************************************************************************/
  
    public BufferedImage getScaledImage()
    {
        return scaledImage;
    }
  
    /**************************************************************************/
  
    public boolean runDijkstra()
    {
        myPred = myDijkstra.execute(myMapGraph, startChoice); //Map and source int

        if (myDijkstra.getGraphConnected() == false)
        {
            return false;
        }

        else
        {
            dijkstraResults = new String[nodeArray.size()];

            for (int n = 0; n < nodeArray.size(); n++)
            {
                dijkstraResults[n] = myDijkstra.printPath(myMapGraph,myPred,startChoice,n);
            }
            
            return true;
        }
    }
  
    /**************************************************************************/
  
    public void openURL(URL urlIn)
    {
        InputStream is;
        URLConnection urlc = null;

        try
        {
            URL a = urlIn;
            urlc = a.openConnection();
            is = urlc.getInputStream();

            myXmlParser.loadXmlFile(is);

            is.close();
        }
    
        catch (IOException e)
        {
            System.out.println("I/O Exception occured");
        }
    }
  
    /**************************************************************************/

    public void processRequest(String startIn, String goalIn, String widthIn, String heightIn, String typeChoiceIn)
    {
        startChoice = -1;
        goalChoice = -1;

        startChoiceText = startIn;
        goalChoiceText = goalIn;
        screenWidth = Integer.parseInt(widthIn);
        screenHeight = Integer.parseInt(heightIn);
        type = typeChoiceIn;
    
        for (int x = 0; x < nodeArray.size(); x++)
        {
            Node TempNode = (Node) nodeArray.get(x);

            if (startChoiceText.equals(TempNode.getNodeName()))
            {
                startChoice = TempNode.getNodeNo();
            }

            if (goalChoiceText.equals(TempNode.getNodeName()))
            {
                goalChoice = TempNode.getNodeNo();
            }
        }

        if (startChoice == -1)
        {
            System.out.println("The selected start location has not been placed in the graph");
        }

        if (goalChoice == -1)
        {
            System.out.println("The selected goal location has not been placed in the graph");
        }

        if (startChoice != -1 & goalChoice != -1)
        {
            runDijkstra = true;
        
            /** Get the routes from start location to goal location */
            getRoute();
        }
    }

    /** JPanel for drawimg on *************************************************/
  
    public class DrawingPanel extends JPanel
    {
        Edge drawEdge2 = new Edge();
        Point loopPoint4 = new Point();
        Point loopPoint5 = new Point();
        int paintChoice;
        BasicStroke myStroke = new  BasicStroke(3.0f);
        BufferedImage map;
        Graphics2D bufferGraphics;
    
        public DrawingPanel()
        {
            setOpaque(false);
            try
            {
                map = new BufferedImage(1031,705,BufferedImage.TYPE_INT_ARGB);
                map = javax.imageio.ImageIO.read(appletpackage.AppletGui.class.getResource("newMap.JPG"));
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(map,0);      
      
                try
                {
                    tracker.waitForID(0);
                }
      
                catch (InterruptedException e)
                {
                    System.out.println("Map image loading failed");
                }
                
                bufferGraphics = (Graphics2D) map.getGraphics();
            }

            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void paintDijkstra()
        {
            paintChoice = 2;
            paintImage();
        }

        public void paintImage()
        {
            if (paintChoice == 2) // paint the route
            {
                for (int z = 0;  z < resultingEdges.size(); z++)
                {
                    drawEdge2 = (Edge) resultingEdges.get(z);
                    loopPoint4 = drawEdge2.getStartNodePoint();
                    loopPoint5 = drawEdge2.getEndNodePoint();
                    bufferGraphics.setColor(Color.green);
                    Shape aLine = myStroke.createStrokedShape(new Line2D.Double
                    (loopPoint4.x,loopPoint4.y,loopPoint5.x,loopPoint5.y));
                    bufferGraphics.fill(aLine);
                    bufferGraphics.draw(aLine);
                }
            }
      
            /** Scale according to user choice */
            if (type.equals("Fully Scaled") == true)
            {
                /** Scale image */
                scaledImage = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D scaleG = (Graphics2D) scaledImage.getGraphics();
                Image temp = map.getScaledInstance(screenWidth,screenHeight,Image.SCALE_SMOOTH);
                scaleG.drawImage(temp,0,0,null);
            }
      
            else if (type.equals("Large Scrollable") == true)
            {
                /** Scale image */
                scaledImage = new BufferedImage(856,530,BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D scaleG = (Graphics2D) scaledImage.getGraphics();
                Image temp = map.getScaledInstance(856,530,Image.SCALE_SMOOTH);
                scaleG.drawImage(temp,0,0,null);
            }
      
            else if (type.equals("Sub-Section Zoom") == true)
            {
                /** Scale image */
                int tempWidth = eastX - westX;
                int tempHeight = southY - northY;
                scaledImage = new BufferedImage(tempWidth,tempHeight,BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D scaleG = (Graphics2D) scaledImage.getGraphics();
                BufferedImage temp = map.getSubimage(westX,northY,tempWidth,tempHeight);
                scaleG.drawImage(temp,0,0,null);
            }
        }
    }
}