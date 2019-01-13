package applicationpackage;

import java.awt.Component;
import java.awt.Point;

/**
 * Interface for classes that contain data for representing graph node/vertices.
 */
public interface INode 
{
  /**
   * Get the node's number.
   * @return int
   */
  int getNumber();
  
  String getName();
  
  Point getCoOrdinates();
  
  boolean getForEdge();
  
  void setNumber(int number);
  
  void setName(String name);
  
  void setCoOrdinates(Point point);
  
  void setForEdge(boolean forEdge);
}