package appletpackage;
import java.awt.Point;

public class Node 
{
    int nodeNo;
    String nodeName;
    Point coOrdinates;
    Point centrePoint;
    int xPos;
    int yPos;
        
    public Node()
    {
    }
    
    public Node(int noIn,String nameIn,Point pointIn,Point centrePointIn)
    {
        nodeNo = noIn;
        nodeName = nameIn;
        coOrdinates = pointIn;
        centrePoint = centrePointIn;
        xPos = pointIn.x;
        yPos = pointIn.y;     
    }
  
    public void setNodeNo(int newNodeNoIn)
    {
        nodeNo = newNodeNoIn;
    }
    
    public void setNodeName(String newNodeNameIn)
    {
        nodeName = newNodeNameIn;
    }
    
    public void setCoOrdinates(Point newPointIn)
    {
        coOrdinates = newPointIn;
    }
    
    public void setCentrePoint(Point newCentrePointIn)
    {
        coOrdinates = newCentrePointIn;
    }
    
    public void setXPos(int newXPosIn)
    {
        xPos = newXPosIn;
    }
    
    public void setYPos(int newYPosIn)
    {
        yPos = newYPosIn;
    }
    
    public int getNodeNo()
    {
        return nodeNo;
    }
    
    public String getNodeName()
    {
        return nodeName;
    } 
    
    public Point getCoOrdinates()
    {
        return coOrdinates;
    }
    
    public Point getCentrePoint()
    {
        return centrePoint;
    }
    
    public int getXPos()
    {
        return xPos;
    }
    
    public int getYPos()
    {
        return yPos;
    }
}