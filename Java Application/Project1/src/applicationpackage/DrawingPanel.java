package applicationpackage;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A class that extends JPanel for drawing the map image and shapes.
 */
class DrawingPanel extends JPanel
{
  Node                        _drawNode;
  Edge                        _drawEdge;
  Edge                        _drawEdge2;
  Point                       _loopPoint;
  Point                       _loopPoint2;
  Point                       _loopPoint3;
  Point                       _loopPoint4;
  Point                       _loopPoint5;
  int                         _paintChoice;
  BasicStroke                 _basicStroke;
  double                      _imageWidth;
  double                      _imageHeight;
  ApplicationGui              _applicationGui;
  ApplicationGuiUtilities     _applicationGuiUtilities;
    
  /**
   * Constructor.
   * @param applicationGui
   */
  public DrawingPanel(ApplicationGui applicationGui)
  {
    _applicationGui           = applicationGui;
    _applicationGuiUtilities  = _applicationGui.getApplicationGuiUtilities();
    _drawNode                 = new Node();
    _drawEdge                 = new Edge();
    _drawEdge2                = new Edge();
    _loopPoint                = new Point();
    _loopPoint2               = new Point();
    _loopPoint3               = new Point();
    _loopPoint4               = new Point();
    _loopPoint5               = new Point();
    _basicStroke              = new  BasicStroke(3.0f);    
    
    this.setOpaque(false);    
  }
    
  /**
   * A method to read an image file and paint it.
   */
  public void displayMap()
  {
    try
    {      
      _applicationGuiUtilities.setImage(ImageIO.read
      (_applicationGuiUtilities.getMapFile()));
      
      _imageWidth   = _applicationGuiUtilities.getImage().getWidth(this);
      _imageHeight  = _applicationGuiUtilities.getImage().getHeight(this);
      
      this.setPreferredSize(new Dimension
      (_applicationGuiUtilities.getImage().getWidth(), 
      _applicationGuiUtilities.getImage().getHeight()));  
      
      repaint();
    }
    catch (Exception e)
    {        
      Error.showMessage(_applicationGui, "Image I/O Error: map image could " +
      "not be read.");      
    }     
  }    
  
  /**
   * Paint all nodes and edges stored in the arrays.
   */
  public void paintAll()
  {
    _paintChoice = 1;
    repaint();
  }

  /**
   * Paint a selected route's edges only.
   */
  public void paintRoute()
  {
    _paintChoice = 2;
    repaint();
  }
  
  /**
   * Paint nothing, thus hiding the graph.
   */
  public void hideGraph()
  {
    _paintChoice = 3;
    repaint();
  } 

  /**
   * Check whether the specified Point lies within the specified Node.
   * @return boolean
   * @param node
   * @param point
   */
  public boolean checkNodeClicked(Point point, Node node)
  {
    Node checkNode  = node;
    _loopPoint       = checkNode.getCoOrdinates();    
    Shape aNode     = (new Ellipse2D.Double(_loopPoint.getX()-8,
                      _loopPoint.getY()-8, 16, 16));

    if (aNode.contains(point))
    {
      return true;
    }
      
    return false;
  }

  /**
   * Check whether the specified Point lies within the specified Edge.
   * @return 
   * @param edge
   * @param point
   */
  public boolean checkEdgeClicked(Point point, Edge edge)
  {
    Edge checkEdge  = edge;
    _loopPoint2      = checkEdge.getStartNodePoint();
    _loopPoint3      = checkEdge.getEndNodePoint();
    Shape aEdge     = _basicStroke.createStrokedShape(new Line2D.Double
                      (_loopPoint2.getX(), _loopPoint2.getY(), 
                      _loopPoint3.getX(), _loopPoint3.getY()));

    if (aEdge.contains(point))
    {
      return true;
    }
      
    return false;
  }
    
  /**
   * Increase/decrease scale of the map image to paint depending on zoomFactor.
   * @param zoomFactor
   */
  public void zoom(int zoomFactor)
  {
    if (_applicationGuiUtilities.getMouseButton() == 0)
    {
      _applicationGuiUtilities.setZoomFactor
      (_applicationGuiUtilities.getZoomFactor() + zoomFactor);
    }            
    else
    {
      _applicationGuiUtilities.setZoomFactor
      (_applicationGuiUtilities.getZoomFactor() - zoomFactor);        
    }
      
    if (_applicationGuiUtilities.getZoomFactor() <= 0)
    {
      Error.showMessage(_applicationGui, "You cannot zoom out any further.");
      
      _applicationGuiUtilities.setZoomFactor(1);
      repaint();
      revalidate();
    }      
    else
    {
      //set panel size twice for instant update of drawingPanel size
      setPreferredSize(new Dimension((int)(_imageWidth * 
      _applicationGuiUtilities.getZoomFactor()), (int)(_imageHeight * 
      _applicationGuiUtilities.getZoomFactor())));
      
      setSize(new Dimension((int)(_imageWidth * 
      _applicationGuiUtilities.getZoomFactor()), (int)(_imageHeight * 
      _applicationGuiUtilities.getZoomFactor())));
      
      repaint();
      revalidate();        
    }      
  }

  /**
   * Paint method.
   * @param g
   */
  public void paintComponent(Graphics g)
  {
    //paint background
    super.paintComponent(g);
      
    Graphics2D g2 = (Graphics2D) g;
    g2.scale(_applicationGuiUtilities.getZoomFactor(),
    _applicationGuiUtilities.getZoomFactor());
      
    //check if image is null first and draw if not null
    if (_applicationGuiUtilities.getImage() != null)
    {
      g2.drawImage(_applicationGuiUtilities.getImage(), 0, 0, this);
    }

    //**CHOICE 1: paint all nodes and edges**
    if (_paintChoice == 1)
    {
      //nodes
      for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
      {
        _drawNode = (Node) _applicationGuiUtilities.getNodeArray().get(x);
        
        if (_drawNode.getForEdge())
        {
          //paint selected nodes for new edges
          _loopPoint = _drawNode.getCoOrdinates();
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
          RenderingHints.VALUE_ANTIALIAS_ON );
          g2.setColor(_applicationGui.getApplicationGuiColourScheme()
          .getNodeForEdgeColour());
          g2.fillOval(_loopPoint.x-8, _loopPoint.y-8, 16, 16);
        }
        else
        {
          //paint choice points and buildings different colours.
          if (_drawNode.getName().startsWith("Choice Point"))
          {
            _loopPoint = _drawNode.getCoOrdinates();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setColor(_applicationGui.getApplicationGuiColourScheme()
            .getChoicePointColour());
            g2.fillOval(_loopPoint.x-8, _loopPoint.y-8, 16, 16);                   
          }          
          else
          {
            _loopPoint = _drawNode.getCoOrdinates();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setColor(_applicationGui.getApplicationGuiColourScheme()
            .getBuildingColour());
            g2.fillOval(_loopPoint.x-8, _loopPoint.y-8, 16, 16);
          }
        }        
      }
        
      //edges
      for (int y = 0;  y < _applicationGuiUtilities.getEdgeArray().size(); y++)
      {
        _drawEdge    = (Edge) _applicationGuiUtilities.getEdgeArray().get(y);
        _loopPoint2  = _drawEdge.getStartNodePoint();
        _loopPoint3  = _drawEdge.getEndNodePoint();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON );
        g2.setColor(_applicationGui.getApplicationGuiColourScheme()
        .getEdgeColour());
        Shape aLine = _basicStroke.createStrokedShape(new Line2D.Double
        (_loopPoint2.x ,_loopPoint2.y, _loopPoint3.x, _loopPoint3.y));
        g2.fill(aLine);
        g2.draw(aLine);
      }
    }
      
    //**CHOICE 2: paint a selected route**
    else if (_paintChoice == 2)
    {
      //draw route with full graph
      if (_applicationGuiUtilities.getDispayFullGraph())
      {
        //nodes
        for (int x = 0;  x < _applicationGuiUtilities.getNodeArray().size(); x++)
        {
          _drawNode = (Node) _applicationGuiUtilities.getNodeArray().get(x);
          
          if (_drawNode.getForEdge())
          {
            //paint selected nodes for new edges
            _loopPoint = _drawNode.getCoOrdinates();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setColor(_applicationGui.getApplicationGuiColourScheme()
            .getNodeForEdgeColour());
            g2.fillOval(_loopPoint.x-8, _loopPoint.y-8, 16, 16);
          }
          else
          {
            //paint choice points and buildings different colours.
            if (_drawNode.getName().startsWith("Choice Point"))
            {
              _loopPoint = _drawNode.getCoOrdinates();
              g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
              RenderingHints.VALUE_ANTIALIAS_ON );
              g2.setColor(_applicationGui.getApplicationGuiColourScheme()
              .getChoicePointColour());
              g2.fillOval(_loopPoint.x-8, _loopPoint.y-8, 16, 16);                   
            }          
            else
            {
              _loopPoint = _drawNode.getCoOrdinates();
              g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
              RenderingHints.VALUE_ANTIALIAS_ON );
              g2.setColor(_applicationGui.getApplicationGuiColourScheme()
              .getBuildingColour());
              g2.fillOval(_loopPoint.x-8, _loopPoint.y-8, 16, 16);
            }
          }        
        }
          
        //edges
        for (int y = 0;  y < _applicationGuiUtilities.getEdgeArray().size(); y++)
        {
          _drawEdge    = (Edge) _applicationGuiUtilities.getEdgeArray().get(y);
          if (_applicationGuiUtilities.getResultingEdges().contains(_drawEdge) == false)
          {
            _loopPoint2  = _drawEdge.getStartNodePoint();
            _loopPoint3  = _drawEdge.getEndNodePoint();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setColor(_applicationGui.getApplicationGuiColourScheme()
            .getEdgeColour());
            Shape aLine = _basicStroke.createStrokedShape(new Line2D.Double
            (_loopPoint2.x ,_loopPoint2.y, _loopPoint3.x, _loopPoint3.y));
            g2.fill(aLine);
            g2.draw(aLine);
          }
        }      
        
        for (int z = 0; z < _applicationGuiUtilities.getResultingEdges().size(); z++)
        {
          _drawEdge2   = (Edge) _applicationGuiUtilities.getResultingEdges()
          .get(z);
          _loopPoint4  = _drawEdge2.getStartNodePoint();
          _loopPoint5  = _drawEdge2.getEndNodePoint();
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
          RenderingHints.VALUE_ANTIALIAS_ON );
          g2.setColor(_applicationGui.getApplicationGuiColourScheme()
          .getRouteColour());
          Shape aLine = _basicStroke.createStrokedShape(new Line2D.Double
          (_loopPoint4.x, _loopPoint4.y, _loopPoint5.x, _loopPoint5.y));
          g2.fill(aLine);
          g2.draw(aLine);
        }
      }
      else
      {
        //draw route alone
        for (int z = 0; z < _applicationGuiUtilities.getResultingEdges().size(); z++)
        {
          _drawEdge2   = (Edge) _applicationGuiUtilities.getResultingEdges()
          .get(z);
          _loopPoint4  = _drawEdge2.getStartNodePoint();
          _loopPoint5  = _drawEdge2.getEndNodePoint();
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
          RenderingHints.VALUE_ANTIALIAS_ON );
          g2.setColor(_applicationGui.getApplicationGuiColourScheme()
          .getRouteColour());
          Shape aLine = _basicStroke.createStrokedShape(new Line2D.Double
          (_loopPoint4.x, _loopPoint4.y, _loopPoint5.x, _loopPoint5.y));
          g2.fill(aLine);
          g2.draw(aLine);
        }
      }
    }
      
    //**CHOICE 3: Paint nothing - hide graph**
    else if (_paintChoice == 3)
    {
    }   
  }
}