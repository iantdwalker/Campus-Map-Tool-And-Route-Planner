package appletpackage;
import java.awt.Point;

public class Edge 
{
    int startNode;
    int endNode;
    int weight;
    Point startNodePoint;
    Point endNodePoint;
    double startNodeX;
    double startNodeY;
    double endNodeX;
    double endNodeY;  
    
    public Edge()
    {
    }
    
    public Edge(int startNodeIn,int endNodeIn, Point startNodePointIn,
                Point endNodePointIn, int weightIn)
    {
        startNode = startNodeIn;
        endNode = endNodeIn;      
        startNodePoint = startNodePointIn;
        endNodePoint = endNodePointIn;
        weight = weightIn;
        startNodeX = startNodePointIn.getX();
        startNodeY = startNodePointIn.getY();
        endNodeX = endNodePointIn.getX();
        endNodeY = endNodePointIn.getY();
    }
  
    public void setStartNode(int newStartNodeIn)
    {
        startNode = newStartNodeIn;
    }
    
    public void setEndNode(int newEndNodeIn)
    {
        endNode = newEndNodeIn;
    }
    
    public void setWeight(int newWeightIn)
    {
        weight = newWeightIn;
    }
    
    public void setStartNodePoint(Point newStartNodePointIn)
    {
        startNodePoint = newStartNodePointIn;
    }
    
    public void setEndNodePoint(Point newEndNodePointIn)
    {
        endNodePoint = newEndNodePointIn;
    }
    
    public int getStartNode()
    {
        return startNode;
    }
    
    public int getEndNode()
    {
        return endNode;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public Point getStartNodePoint()
    {
        return startNodePoint;
    }
    
    public Point getEndNodePoint()
    {
        return endNodePoint;
    }
  
    public double getStartNodeX()
    {
        return startNodeX;
    }
  
    public double getStartNodeY()
    {
        return startNodeY;
    }
  
    public double getEndNodeX()
    {
        return endNodeX;
    }
  
    public double getEndNodeY()
    {
        return endNodeY;
    }
}