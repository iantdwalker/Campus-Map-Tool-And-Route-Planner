package applicationpackage;

import java.io.File;

/**
 * A class that contains utility methods for extentions of FileFilter.
 */
public class FileFilterUtilities 
{
  /**
   * A static method to get the extension of a filename.
   * @return String
   * @param file
   */
  public static String getExtension(File file)
  {
    String  extension = null;
    String  fileName  = file.getName();
    int     x         = fileName.lastIndexOf('.');

    if (x > 0 && x < fileName.length() - 1)
    {
      extension = fileName.substring(x + 1).toLowerCase();
    }
      
    return extension;
  }
}