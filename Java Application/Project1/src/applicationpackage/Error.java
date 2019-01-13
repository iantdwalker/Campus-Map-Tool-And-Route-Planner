package applicationpackage;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * A class containing utility methods for handling errors.
 */
public class Error 
{
  /**
   * A static method for displaying an error message dialog.
   * @param parent
   * @param message
   */
  public static void showMessage(Component parent, String message)
  {
    JOptionPane.showMessageDialog(parent, message, "Error",
    JOptionPane.ERROR_MESSAGE);
  }
}