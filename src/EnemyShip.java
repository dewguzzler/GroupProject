import javax.swing.ImageIcon;

/**
 * @Everett Rucker
 */

public class EnemyShip extends Ship{

 private static final long serialVersionUID = 1L;
 private static ImageIcon shipCon;
 public int color;
 public static int points;
 public EnemyShip(int x, int y, int color)
 {
  super(shipCon(color), x, y, colorPoints(color));
  
  
  
  
  
   
       
     
       
  
 }
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
 