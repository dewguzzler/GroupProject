import javax.swing.ImageIcon;

/** Class: HeroShip.java
*
* @author Jesse Perkins
* @version 1.0
* Written: Nov 14, 2013
*This class calls an image to represent an explosion
*Purpose: – used when a ship is hit to show an explosion
*/
public class Explosion extends Ship
{
 private static final long serialVersionUID = 1L;

  /** Method: Explosion returns an image of an explosion
  * 
  */
 
 public Explosion()
 {
  super(new ImageIcon(HeroShip.class.getResource("res/images/explosion.png")),
    380, 500, 0);
 }
}