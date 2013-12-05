import javax.swing.ImageIcon;

/** Class: HeroShip.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Nov 14, 2013
 *This class calls an image to represent the hero of the game and is extended to ship
 *Purpose: – Obtain an image that can be used as the hero in the game, which can be called at any time
 */
public class HeroShip extends Ship
{
 private static final long serialVersionUID = 1L;
 
  /** Method: HeroShip* obtains an image of the hero ship
  * 
  */
 
 public HeroShip()
 {
  super(new ImageIcon(HeroShip.class.getResource("res/images/HeroShip.png")),
    380, 500, 0);
 }
}