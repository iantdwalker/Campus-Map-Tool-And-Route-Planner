package mypackage1;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.List;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class BuildingsDialog extends JDialog 
{
  private JLabel jLabel1 = new JLabel();
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTextField1 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  private JButton jButton3 = new JButton();

  public BuildingsDialog()
  {
    this(null, "", false);
  }

  /**
   * 
   * @param modal
   * @param title
   * @param parent
   */
  public BuildingsDialog(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(294, 292));
    this.getContentPane().setLayout(null);
    jLabel1.setText("Input a new node name:");
    jLabel1.setBounds(new Rectangle(10, 65, 265, 50));
    jLabel1.setFont(new Font("Tahoma", 1, 12));
    jButton1.setText("OK");
    jButton1.setBounds(new Rectangle(30, 195, 75, 45));
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOKClicked(e);
        }
      });
    jButton2.setText("Cancel");
    jButton2.setBounds(new Rectangle(180, 195, 75, 45));
    jButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancelClicked(e);
        }
      });
    jLabel2.setText("The current node name is:");
    jLabel2.setBounds(new Rectangle(10, 5, 265, 50));
    jLabel2.setFont(new Font("Tahoma", 1, 12));
    jTextField1.setBounds(new Rectangle(10, 45, 265, 25));
    jTextField1.setEditable(false);
    jTextField2.setBounds(new Rectangle(10, 105, 265, 25));
    jTextField2.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          jTextField2_keyPressed(e);
        }
      });
    jButton3.setText("This node is a choice point");
    jButton3.setBounds(new Rectangle(10, 150, 265, 30));
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jTextField2, null);
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel1, null);
  }

  private void btnOKClicked(ActionEvent e)
  {
  }

  private void btnCancelClicked(ActionEvent e)
  {
  }

  private void jTextField2_keyPressed(KeyEvent e)
  {
    System.out.println(e.getKeyChar());
  }
  
  
}