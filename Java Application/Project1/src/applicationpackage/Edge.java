package applicationpackage;

import java.awt.Point;

/**
 * A class that contains data for representing a graph edge/path.
 */
public class Edge
{
  private int     _startNode;
  private int     _endNode;
  private int     _weight;
  private Point   _startNodePoint;
  private Point   _endNodePoint;
  private double  _startNodeX;
  private double  _startNodeY;
  private double  _endNodeX;
  private double  _endNodeY;
  
  /**
   * Constructor.
   */
  public Edge()
  {    
  }  

  /**
   * Constructor.
   * @param weight
   * @param endNodePoint
   * @param startNodePoint
   * @param endNode
   * @param startNode
   */
  public Edge(int startNode, int endNode, Point startNodePoint, 
  Point endNodePoint, int weight)
  {
    _startNode      = startNode;
    _endNode        = endNode;
    _startNodePoint = startNodePoint;
    _endNodePoint   = endNodePoint;
    _weight         = weight;
    _startNodeX     = startNodePoint.getX();
    _startNodeY     = startNodePoint.getY();
    _endNodeX       = endNodePoint.getX();
    _endNodeY       = endNodePoint.getY();
  }

  /**
   * Set the start node number.
   * @param startNode
   */
  public void setStartNode(int startNode)
  {
    _startNode = startNode;
  }

  /**
   * Set the end node number.
   * @param endNode
   */
  public void setEndNode(int endNode)
  {
    _endNode = endNode;
  }

  /**
   * Set the weight for this edge.
   * @param weight
   */
  public void setWeight(int weight)
  {
    _weight = weight;
  }

  /**
   * Set the start node Point.
   * @param startNodePoint
   */
  public void setStartNodePoint(Point startNodePoint)
  {
    _startNodePoint = startNodePoint;
  }

  /**
   * Set the end node Point.
   * @param endNodePoint
   */
  public void setEndNodePoint(Point endNodePoint)
  {
    _endNodePoint = endNodePoint;
  }

  /**
   * Set the start node's X position.
   * @param startNodeX
   */
  public void setStartNodeX(double startNodeX)
  {
    _startNodeX = startNodeX;
  }

  /**
   * Set the start node's Y position.
   * @param startNodeY
   */
  public void setStartNodeY(double startNodeY)
  {
    _startNodeY = startNodeY;
  }

  /**
   * Set the end node's X position.
   * @param endNodeX
   */
  public void setEndNodeX(double endNodeX)
  {
    _endNodeX = endNodeX;
  }

  /**
   * Set the end node's Y position.
   * @param endNodeY
   */
  public void setEndNodeY(double endNodeY)
  {
    _endNodeY = endNodeY;
  }

  /**
   * Get the start node number.
   * @return int
   */
  public int getStartNode()
  {
    return _startNode;
  }

  /**
   * Get the end node number.
   * @return int
   */
  public int getEndNode()
  {
    return _endNode;
  }

  /**
   * Get the weight for this edge.
   * @return int
   */
  public int getWeight()
  {
    return _weight;
  }

  /**
   * Get the start node Point.
   * @return Point
   */
  public Point getStartNodePoint()
  {
    return _startNodePoint;
  }

  /**
   * Get the end node Point.
   * @return Point
   */
  public Point getEndNodePoint()
  {
    return _endNodePoint;
  }

  /**
   * Get the start node's X position.
   * @return double
   */
  public double getStartNodeX()
  {
    return _startNodeX;
  }

  /**
   * Get the start node's Y position.
   * @return double
   */
  public double getStartNodeY()
  {
    return _startNodeY;
  }

  /**
   * Get the end node's X position.
   * @return double
   */
  public double getEndNodeX()
  {
    return _endNodeX;
  }

  /**
   * Get the end node's Y position.
   * @return double
   */
  public double getEndNodeY()
  {
    return _endNodeY;
  }
}