import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**Clas: Missileship
 * @author Jesse Perkins
 * Date: November 23, 2013
 * 
 * This class - displays an image representing the missile
 * 
 * purpose - So the user can see the missile while shooting
 *
 */
public class Missileship extends Ship
{
 private static final long serialVersionUID = 1L;
 /** Method: Missileship* shows an image of the missile
  */
 public Missileship()
 {
  super(new ImageIcon(Missileship.class.getResource("res/images/bullet.png")),
    100, 15, 0);
 }
}