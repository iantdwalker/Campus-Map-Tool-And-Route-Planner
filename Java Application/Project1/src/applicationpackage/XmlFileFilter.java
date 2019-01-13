package applicationpackage;

import java.io.*;

import javax.swing.*;

/**
 * A class that extends FileFilter for XML files.
 */
public class XmlFileFilter extends javax.swing.filechooser.FileFilter
{
  private String  _DESCRIPTION;
  
  /**
   * Constructor.
   */
  public XmlFileFilter()
  {
    super();
    _DESCRIPTION = "XML Files";
  }
  
  /**
   * Decide which file filters this class will display based on the file
   * specified.
   * @return boolean
   * @param file
   */
  public boolean accept(File file)
  {
    if (file.isDirectory())
    {
      return true;
    }
      
    String extension = FileFilterUtilities.getExtension(file);
      
    if (extension != null)
    {
      if (extension.equals("xml") || extension.equals("XML"))
      {
        return true;
      }        
      else
      {
        return false;
      }
    }
      
    return false;
  }   
  
  /**
   * The description of the filter.
   * @return String
   */
  public String getDescription()
  {
    return _DESCRIPTION;
  }
}