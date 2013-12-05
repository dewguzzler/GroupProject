import javax.swing.ImageIcon;

/**
 * @Everett Rucker
 *  Written: November 22, 2013
 *
 *
 * EnemyShip – Creates an image of a ship, its worth by points and 
 * describes it's coordinates in the game panel
 *
 * Purpose: – To create targets for the hero to shoot at
 */

public class EnemyShip extends Ship{

 private static final long serialVersionUID = 1L;
 private static ImageIcon shipCon; //image of an enemyship
 public int color; //int representing which color should be used for an enemyship
 public static int points; //number of points an enemy ship is worth
 
 /** Method: EnemyShip determines the x and y coordinates of a ship and returns an Icon
 * to represent the ship. 
 * @param x  x represents the x coordinate of the enemy ship in the panel
 * @param y  y represents the x coordinate of the enemy ship in the panel
 * @param color color represents a certain color of a ship
 * 
 */
 
 public EnemyShip(int x, int y, int color)
 {
  super(shipCon(color), x, y, colorPoints(color));
  
  
 }
 
 /** Method: shipCon determines which image is shown to represent a color.
 * An image is returned for all 3 colors.
 * @param color represents a certain color of a ship
 */
 
 public static ImageIcon shipCon(int color){
  if(color == 1){
   shipCon = new ImageIcon(EnemyShip.class.getResource("res/images/BigBoss_Transparent.png"));
  }
  if(color == 2){
   shipCon = new ImageIcon(EnemyShip.class.getResource("res/images/Purp_Transparent.png"));
  }
  if(color == 3){
   shipCon = new ImageIcon(EnemyShip.class.getResource("res/images/Enemy1.png"));
  }
  
  return shipCon;
 }
 
 /** Method: colorPoints determines the number of points given for each color of ship hit
 * @param color color represents a certain color of a ship
 */
 
public static int colorPoints(int color){
 if(color == 1){
   points = 30;
  }
  if(color == 2){
   points = 20;
  }
  if(color == 3){
   points = 15;
  }
  
  return points;
}
 
 

} 
 