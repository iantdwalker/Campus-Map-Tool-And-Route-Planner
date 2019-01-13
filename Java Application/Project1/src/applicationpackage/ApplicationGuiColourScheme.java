package applicationpackage;

import java.awt.Color;

/**
 * A class that sets the colour scheme for the application.
 */
public class ApplicationGuiColourScheme 
{
  private Color   _buildingColour;
  private Color   _choicePointColour;
  private Color   _edgeColour;
  private Color   _routeColour;
  private Color   _nodeForEdgeColour;
  
  /**
   * Constructor.
   */
  public ApplicationGuiColourScheme()
  {
    //default scheme
    _buildingColour     = Color.BLUE;
    _choicePointColour  = Color.YELLOW;
    _edgeColour         = Color.RED;
    _routeColour        = Color.GREEN; 
    _nodeForEdgeColour  = Color.BLACK;
  }
  
  /**
   * Gets the Color for buildings nodes.
   * @return Colour
   */
  public Color getBuildingColour()
  {
    return _buildingColour;
  }
  
  /**
   * Sets the Color for buildings nodes.
   * @param colour
   */
  public void setBuildingColour(Color colour)
  {
    _buildingColour = colour;
  }
  
  /**
   * Gets the Color for choice point nodes.
   * @return Color
   */
  public Color getChoicePointColour()
  {
    return _choicePointColour;
  }
  
  /**
   * Sets the Color for choice point nodes.
   * @param colour
   */
  public void setChoicePointColour(Color colour)
  {
    _choicePointColour = colour;
  }
  
  /**
   * Gets the Color for edges.
   * @return Color
   */
  public Color getEdgeColour()
  {
    return _edgeColour;
  }
  
  /**
   * Sets the Color for edges.
   * @param colour
   */
  public void setEdgeColour(Color colour)
  {
    _edgeColour = colour;
  }
  
  /**
   * Gets the Color for routes.
   * @return Color
   */
  public Color getRouteColour()
  {
    return _routeColour;
  }
  
  /**
   * Sets the Color for routes.
   * @param colour
   */
  public void setRouteColour(Color colour)
  {
    _routeColour = colour;
  }
  
  /**
   * Gets the Color for selected nodes during edge creation.
   * @return Color
   */
  public Color getNodeForEdgeColour()
  {
    return _nodeForEdgeColour;
  }
  
  /**
   * Sets the Color for selected nodes during edge creation.
   * @param colour
   */
  public void setNodeForEdgeColour(Color colour)
  {
    _nodeForEdgeColour = colour;
  }
}