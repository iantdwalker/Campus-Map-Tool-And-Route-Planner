package applicationpackage;

import java.awt.image.BufferedImage;

import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A class to setup the ApplicationGui's menu and toolbar icons.
 */
public class ApplicationGuiIcons 
{
  ApplicationGui _applicationGui;
  
  /**
   * Constructor.
   * @param parent
   */
  public ApplicationGuiIcons(ApplicationGui parent)
  {
    _applicationGui = parent;
  }
  
  /**
   * Set the menu item icons.
   * @throws java.lang.Exception
   */
  public void setMenuIcons() throws Exception
  {
    InputStream   inputStream;
    BufferedImage imageIcon;
    
    //open map
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/OpenMap.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuOpenMap().setIcon(new ImageIcon(imageIcon));
        
    //open xml
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/OpenXml.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuOpenXML().setIcon(new ImageIcon(imageIcon));
    
    //save xml
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/Save.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuSaveXML().setIcon(new ImageIcon(imageIcon));
    
    //save as...
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/SaveAs.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuSaveAsXML().setIcon(new ImageIcon(imageIcon));
    
    //exit
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/Exit.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuExit().setIcon(new ImageIcon(imageIcon));
    
    //insert node
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/InsertNode.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuInsertNode().setIcon(new ImageIcon(imageIcon));
    
    //insert edge
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/InsertEdge.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuInsertEdge().setIcon(new ImageIcon(imageIcon));
    
    //move node
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/MoveNode.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuMoveNode().setIcon(new ImageIcon(imageIcon));
    
    //name node
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/NameNode.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuNameNode().setIcon(new ImageIcon(imageIcon));
    
    //remove item
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/RemoveItem.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuRemoveItem().setIcon(new ImageIcon(imageIcon));
    
    //remove all
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/RemoveAll.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuRemoveAll().setIcon(new ImageIcon(imageIcon));
    
    //hide graph
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/HideGraph.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuHideGraph().setIcon(new ImageIcon(imageIcon));
    
    //show graph
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/ShowGraph.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuShowGraph().setIcon(new ImageIcon(imageIcon));
    
    //zoom in/out
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/Zoom.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuZoom().setIcon(new ImageIcon(imageIcon));
    
    //set colour
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/Colour.PNG");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuSetColourScheme().setIcon(new ImageIcon(imageIcon));
    
    //get route
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/GetRoute.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuGetRoute().setIcon(new ImageIcon(imageIcon));
    
    //user help
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/UserHelp.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuUserHelp().setIcon(new ImageIcon(imageIcon));
    
    //about
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/menu/About.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMenuAbout().setIcon(new ImageIcon(imageIcon));
  }
  
  /**
   * Set the toolbar icons and events.
   * @throws java.lang.Exception
   */
  public void setToolbarIcons() throws Exception
  {
    InputStream   inputStream;
    BufferedImage imageIcon;
    
    //open map
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/OpenMap.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getOpenMapButton().setIcon(new ImageIcon(imageIcon));
    
    //open xml
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/OpenXml.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getOpenXmlButton().setIcon(new ImageIcon(imageIcon));
    
    //save xml
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/Save.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getSaveXmlButton().setIcon(new ImageIcon(imageIcon));
    
    //save as...
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/SaveAs.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getSaveAsXmlButton().setIcon(new ImageIcon(imageIcon));
    
    //insert node
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/InsertNode.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getInsertNodeButton().setIcon(new ImageIcon(imageIcon));
    
    //insert edge
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/InsertEdge.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getInsertEdgeButton().setIcon(new ImageIcon(imageIcon));
    
    //move node
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/MoveNode.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getMoveNodeButton().setIcon(new ImageIcon(imageIcon));
    
    //name node
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/NameNode.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getNameNodeButton().setIcon(new ImageIcon(imageIcon));
    
    //remove item
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/RemoveItem.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getRemoveItemButton().setIcon(new ImageIcon(imageIcon));
    
    //remove all
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/RemoveAll.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getRemoveAllButton().setIcon(new ImageIcon(imageIcon));
    
    //hide graph
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/HideGraph.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getHideGraphButton().setIcon(new ImageIcon(imageIcon));
    
    //show graph
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/ShowGraph.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getShowGraphButton().setIcon(new ImageIcon(imageIcon));
    
    //zoom in/out
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/Zoom.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getZoomButton().setIcon(new ImageIcon(imageIcon));
    
    //set colour
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/Colour.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getSetColourButton().setIcon(new ImageIcon(imageIcon));
    
    //get route
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/GetRoute.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getGetRouteButton().setIcon(new ImageIcon(imageIcon));
    
    //user help
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/UserHelp.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();
    
    _applicationGui.getUserHelpButton().setIcon(new ImageIcon(imageIcon));
    
    //about
    inputStream = this.getClass().getResourceAsStream
    ("/Resources/Icons/toolbar/About.png");
    
    imageIcon = ImageIO.read(inputStream);
    inputStream.close();    
    
    _applicationGui.getAboutButton().setIcon(new ImageIcon(imageIcon));
  }
}