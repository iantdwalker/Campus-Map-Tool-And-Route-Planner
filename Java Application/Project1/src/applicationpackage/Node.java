package applicationpackage;

import java.awt.Point;

/**
 * A class that contains data for representing a graph node/vertex.
 */
public class Node
{
  private int     _number;
  private String  _name;
  private Point   _coOrdinates;
  private boolean _forEdge;
    
  /**
   * Constructor.
   */
  public Node()
  {  
  }
  
  /**
   * Constructor.
   * @param point
   * @param name
   * @param number
   */
  public Node(int number, String name, Point point)
  {
    _number       = number;
    _name         = name;
    _coOrdinates  = point;
    _forEdge      = false;    
  }
  
  /**
   * Get the node's number.
   * @return int
   */
  public int getNumber()
  {
    return _number;
  }

  /**
   * Get the node's name.
   * @return String
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the node's Co-ordinates.
   * @return Point
   */
  public Point getCoOrdinates()
  {
    return _coOrdinates;
  } 
  
  /**
   * Get the node's for edge value.
   * @return boolean
   */
  public boolean getForEdge()
  {
    return _forEdge;
  }
  
  /**
   * Set the node's number.
   * @param number
   */
  public void setNumber(int number)
  {
    _number = number;
  }

  /**
   * Set the node's name.
   * @param name
   */
  public void setName(String name)
  {
    _name = name;
  }

  /**
   * Set the node's co-ordinates.
   * @param point
   */
  public void setCoOrdinates(Point point)
  {
    _coOrdinates = point;
  }  
  
   /**
   * Set the node's for edge value.
   * @param forEdge
   */
  public void setForEdge(boolean forEdge)
  {
    _forEdge = forEdge;
  }
}