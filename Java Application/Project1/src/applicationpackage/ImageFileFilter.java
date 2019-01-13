package applicationpackage;

import java.io.*;

import javax.swing.*;

/**
 * A class that extends FileFilter for image files.
 */
public class ImageFileFilter extends javax.swing.filechooser.FileFilter
{
  private String  _DESCRIPTION;
  
  /**
   * Constructor.
   */
  public ImageFileFilter()
  {
    super();
    _DESCRIPTION = "Images of type GIF, JPG or PNG";
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
      if (extension.equals("gif") || extension.equals("jpg") ||
      extension.equals("png"))
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
   * @return 
   */
  public String getDescription()
  {
    return _DESCRIPTION;
  }
}