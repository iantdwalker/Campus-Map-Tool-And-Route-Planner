package appletpackage;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import org.w3c.dom.Document;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

public class AppletGui extends JApplet
{
    /** Campus map JPG ********************************************************/
    Image image;
    Graphics2D bufferGraphics;
    private DrawingPanel drawingPanel = new DrawingPanel();
    private JScrollPane frameJScrollPane = new JScrollPane(drawingPanel);
    private JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL);
    private JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL);
    int mouseBtn;
   
    /** Drawing ***************************************************************/
    ArrayList nodeArray = new ArrayList();
    ArrayList edgeArray = new ArrayList();
    boolean graphHidden = false;

    /** General applet frame **************************************************/
    private JPanel frameJPanel = new JPanel();
    private JPanel choicePanel = new JPanel();
    private JPanel choicePanel2 = new JPanel();
    private Choice startList = new Choice();
    private Choice endList = new Choice();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JButton btn1 = new JButton();

    /** Dijkstra results ******************************************************/
    MapGraph myMapGraph;
    Dijkstra myDijkstra = new Dijkstra();
    int[] myPred;
    ArrayList resultingEdges = new ArrayList();
    int startChoice = -1;
    String startChoiceText;
    int goalChoice = -1;
    String goalChoiceText;
    boolean runDijkstra = false;
    String[] dijkstraResults;
    String flag;
    int tempStartNode;
    int tempEndNode;

    /** XML processing ********************************************************/
    XmlParser myXmlParser = new XmlParser();
    ProcessTree myProcessTree = new ProcessTree();
    Document myDocument;

    public AppletGui()
    {
    }

    public void init()
    {
        try
        {
            constructApplet();
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void constructApplet() throws Exception, IOException
    {
        /** XML processing 1 */
        //URL myUrl = AppletGui.class.getResource("Map.xml");
        URL myUrl = new URL(getCodeBase() + "xml/Map.xml");
        openURL(myUrl);

        /** XML processing 2 */
        myDocument = myXmlParser.getDocument();
        myProcessTree.setDocument(myDocument);

        /** XML processing 3 */
        myProcessTree.processDocument();
        nodeArray = myProcessTree.getNodes();
        edgeArray = myProcessTree.getEdges();

        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(new Dimension(1100, 1813));
        image = createImage(1031,705);
        bufferGraphics = (Graphics2D) image.getGraphics();

        /** Central panel with scroll bars ************************************/
        frameJPanel.setLayout(new BorderLayout());
        frameJScrollPane.setHorizontalScrollBar(hbar);
        frameJScrollPane.setVerticalScrollBar(vbar);

        hbar.addAdjustmentListener(new MyAdjustmentListener());
        vbar.addAdjustmentListener(new MyAdjustmentListener());

        frameJPanel.add(hbar, BorderLayout.SOUTH);
        frameJPanel.add(vbar, BorderLayout.EAST);
        frameJPanel.add(frameJScrollPane, BorderLayout.CENTER);

        jLabel1.setText("Select a start location:");
        jLabel2.setText("Select a goal location:");
        jLabel3.setText("Left mouse button: zoom in");
        jLabel4.setText("Right mouse button: zoom out");
        btn1.setText("Find Route");
        btn1.addActionListener(new ActionListener()
            {
            public void actionPerformed(ActionEvent e)
            {
                btnClicked(e);
            }
            });
    
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
                startList.add(listNodes[x]);
                endList.add(listNodes[x]);
            }
        }
        
        choicePanel.setLayout(new GridLayout(2,1));
        choicePanel2.setLayout(new GridLayout(7,1));
        choicePanel2.add(jLabel1);
        choicePanel2.add(startList);
        choicePanel2.add(jLabel2);
        choicePanel2.add(endList);
        choicePanel2.add(btn1);
        choicePanel2.add(jLabel3);
        choicePanel2.add(jLabel4);
        choicePanel.add(choicePanel2);

        this.getContentPane().add(frameJPanel, BorderLayout.CENTER);
        this.getContentPane().add(choicePanel, BorderLayout.EAST);

        /** hide graph */
        graphHidden = true;
        drawingPanel.setVisible(true);
        drawingPanel.setEnabled(false);
        
        /** Create the graph */
        createGraph();    

        /** Check parameters in case display a building location only */
        String displayBuilding;
        displayBuilding = getParameter("buildingToDisplay");

        if (displayBuilding == null)
        {
            System.out.println("A parameter has not been passed in");
        }

        else
        {
            System.out.println(displayBuilding);
            drawingPanel.displayLocation(displayBuilding);
            drawingPanel.setVisible(true);
                        
            for (int x = 0;  x < nodeArray.size(); x++)
            {
                Node tempNode = (Node) nodeArray.get(x);
                Point tempPoint = new Point();

                if (tempNode.getNodeName().equals(displayBuilding))
                {
                    tempPoint = tempNode.getCoOrdinates();
                    drawingPanel.scrollRectToVisible(new Rectangle(tempPoint.x,tempPoint.y,0,0));                             
                }
            }    
        }
    } 

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
                JOptionPane.showMessageDialog(this,"This graph is not fully connected"
                ,"Error",JOptionPane.ERROR_MESSAGE);
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
                }

                drawingPanel.paintDijkstra();

                /** Display route if graph is hidden */
                if (graphHidden == true)
                {
                  drawingPanel.setVisible(true);
                  drawingPanel.setEnabled(true);
                }
            } //end else
        } //end if
    } //end getRoute()

    /** Other methods *********************************************************/
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
                dijkstraResults[n] = myDijkstra.printPath
                (myMapGraph,myPred,startChoice,n);
            }
            return true;
        }
    }
    
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
            e.printStackTrace();
        }
    }

    private void btnClicked(ActionEvent e)
    {
        startChoice = -1;
        goalChoice = -1;

        startChoiceText = startList.getSelectedItem();
        goalChoiceText = endList.getSelectedItem();

        if (startChoiceText == null | goalChoiceText == null)
        {
            System.out.println("Default");
            startChoiceText = "Cookworthy";
            goalChoiceText = "Main Hall";
        }

        if (startChoiceText.equals(goalChoiceText))
        {
            JOptionPane.showMessageDialog(this,"Start and goal locations must not" +
            " be the same","Error",JOptionPane.ERROR_MESSAGE);
        }

        else
        {
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
                JOptionPane.showMessageDialog(this,"The selected start location has "
                + "not been placed in the graph","Error",JOptionPane.ERROR_MESSAGE);
            }

            if (goalChoice == -1)
            {
                JOptionPane.showMessageDialog(this,"The selected goal location has "
                + "not been placed in the graph","Error",JOptionPane.ERROR_MESSAGE);
            }

            if (startChoice != -1 & goalChoice != -1)
            {
                runDijkstra = true;
                /** Get the routes from start location to goal location */
                getRoute();
            }
        }
    }

    /** AdjustmentListener class **********************************************/
    class MyAdjustmentListener implements AdjustmentListener
    {
        public void adjustmentValueChanged(AdjustmentEvent e)
        {                        
        }
    }       

    /** JPanel for drawimg on *************************************************/
    class DrawingPanel extends JPanel
    {
        Node drawNode = new Node();
        Edge drawEdge = new Edge();
        Edge drawEdge2 = new Edge();
        Point loopPoint = new Point();
        Point loopPoint2 = new Point();
        Point loopPoint3 = new Point();
        Point loopPoint4 = new Point();
        Point loopPoint5 = new Point();
        int paintChoice;
        BasicStroke myStroke = new  BasicStroke(3.0f);
        String buildingLocation;
    
        BufferedImage map;
        double zoom = 1;
        double iw;
        double ih;

        public DrawingPanel()
        {
            setOpaque(false);   
            setPreferredSize(new Dimension(1031, 705));
            
            try
            {
                map = javax.imageio.ImageIO.read(AppletGui.class.getResource("newMap.JPG"));
            }

            catch(Exception e)
            {
                e.printStackTrace();
            }     
      
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(map,0);      
      
            try
            {
                tracker.waitForID(0);
            }
      
            catch (InterruptedException e){}
      
            iw = map.getWidth(this);
            ih = map.getHeight(this);
                              
            addMouseListener(new MouseAdapter()
            { public void mouseReleased(MouseEvent m)
                {
                    if (m.getModifiers() == MouseEvent.BUTTON1_MASK)
                    {
                      mouseBtn = 0;                      
                    }
                    
                    else
                    {
                      mouseBtn = 1;                      
                    }      
                    
                    zoom(0.15);
                    repaint();
                    revalidate();                    
              }
            });
        }
    
        public void zoom(double z)
        {
            if (mouseBtn == 0)
            {
                zoom = zoom + z;
            }
            
            else
            {
                zoom = zoom - z;
            }       
            
            setPreferredSize(new Dimension((int)(iw*zoom)+2,(int)(ih*zoom)+2));            
        }   

        public void paintDijkstra()
        {
            paintChoice = 2;
            repaint();
        }       

        public void displayLocation(String newBuildingLocationIn)
        {
            buildingLocation = newBuildingLocationIn;
            paintChoice = 3;
            repaint();
        }  

        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g); // paints background
            g2.scale(zoom,zoom);
            bufferGraphics.drawImage(map,0,0,null);

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

            else if (paintChoice == 3) // paint the building location
            {
                for (int x = 0;  x < nodeArray.size(); x++)
                {
                    drawNode = (Node) nodeArray.get(x);

                    if (drawNode.getNodeName().equals(buildingLocation))
                    {
                        loopPoint = drawNode.getCoOrdinates();
                        bufferGraphics.setColor(Color.blue);
                        bufferGraphics.fillOval(loopPoint.x,loopPoint.y,15,15);
                    }
                }
            }
            
            g2.drawImage(image,0,0,null);
        }
    }
}