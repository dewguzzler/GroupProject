import javax.swing.ImageIcon;


/**
 * @author Jesse Perkins
 * Written: November 20th, 2013
 * 
 * EnemyMissile - creates an image to represent the laser the Enemy shoots in the game
 * 
 * Purpose - To display the enemy shooting
 */

public class EnemyMissile extends Ship
{
 private static final long serialVersionUID = 1L;

 /** Method: EnemyMissile* pulls an image to display the enemy's "bullet"
  * 
  * 
  */

 public EnemyMissile()
 {
  super(new ImageIcon(EnemyMissile.class.getResource("res/images/bullet.png")),
    200, 500, 0);
 }
}