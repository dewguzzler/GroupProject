import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JPanel;

/**
 * Class: EnemyMovement.java
 * 
 * @author Jesse Perkins
 * @version 1.0 
 * Written: November 27, 2013
 * This class – Creates the movements for the enemies
 *
 * Purpose: – to make the enemy targets harder to hit
 * 
 */

public class EnemyMovement extends JPanel implements Runnable {

 /**
  * 
  */
 private static final long serialVersionUID = 1L;
 private static EnemyShip[][] ens; //returns an array of enemy ships
 private static Graphics gs; //paints graphics
 private static GamePanel gnp; //GamePanel method from GamePanel.java
 private static Game theGame; //Game method from Game.java
 public static final Lock lock = new ReentrantLock(); //Locks the thread
 private static HeroShip hns; //HeroShip method from HeroShip.java
 public volatile boolean execute = true; //returns true
 public static int level; // what level the player is on
 public static boolean allHit = false; //doesn't allow all ships to be hit at once
 
  /** Method: EnemyMovement defines all param shown below:
  * @param EnemyShip[][] es returns an array of enemy ships
  * @param Graphics g paints graphics
  * @param GamePanel gp is the GamePanel method from GamePanel.java
  * @param HeroShip hs is the HeroShip method from HeroShip.java
  * @param Game stop is the GamePanel method from GamePanel.java
  * @param boolean startOver returns true or false if the game is started over
  * @param int lives is the number of lives left
  * @param int level is the level the player is on
  */
 
 public EnemyMovement(EnemyShip[][] es, Graphics g, GamePanel gp,
   HeroShip hs, Game stop, boolean startOver, int lives, int level) {
  EnemyMovement.ens = es;
  EnemyMovement.gs = g;
  EnemyMovement.gnp = gp;
  EnemyMovement.hns = hs;
  EnemyMovement.level = level;
  
 }

 /** Method: Run* runs the thread for enemies to move.
  * Enemies move right to the screen, then drop down a row when they
  * can no longer move right.
  *
  */
 
 @Override
 public void run() {
  while (execute) {
   if (EnemyMovement.lock.tryLock()) {
    try {
     resetEnemy(ens);
     moveEnemy(EnemyMovement.ens, EnemyMovement.gs,
       EnemyMovement.gnp, "right", hns);
     
    } catch (InterruptedException p) {
     EnemyMovement.lock.unlock();
     Thread.currentThread().interrupt();
     if(Thread.currentThread().isInterrupted()){
      execute = false;
     }
     
    } catch (Exception e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
    }
   }
  }
 }

 /** Method: moveEnemy* moves the enemy array right, downs down, then moves it left
  * @param EnemyShip[][] es returns an array of enemy ships
  * @param Graphics g to repaint the images
  * @param GamePanel gp to represent the GamePanel method in GamePanel.java
  * @param String direction is direction of the movement
  * @heroShip hs represents the HeroShip method in HeroShip.java
  */
 
 public void moveEnemy(EnemyShip[][] es, Graphics g, GamePanel gp,
   String direction, HeroShip hs) throws Exception {
  if (isEnemyEmpty(es)) {
   clearEnemy(es);
   gp.repaint();
   Thread.currentThread().sleep(2000);
   gp.moveUpLevel();
   allHit = false;
   throw new InterruptedException();
  }
  
  else if (getBottom(es) >= hs.getY()) {
   
   // gp.paintPlayerExplosion(hs.getY(), hs.getX(), theGame);
   Thread.currentThread().sleep(500);
   clearEnemy(es);
   gp.repaint();
   Thread.currentThread().sleep(500);
   gp.paintPlayerExplosion(gp.getHeroY(), gp.getHeroX(), theGame );
   allHit = false;
   throw new InterruptedException();
  } else if (getBottom(es) < hs.getY()) {
   if (direction.equalsIgnoreCase("right")) {
    moveRight(es, g, gp, hs);
    if (getRight(es) < 800) {
     moveEnemy(es, g, gp, "right", hs);
    } else if (getRight(es) >= 800) {
     moveDown(es, g, gp);
     moveEnemy(es, g, gp, "left", hs);
    }
    // start moving right
    // will keep moving right until hits 800
    // then moves down and starts move left
   }
   if (direction.equalsIgnoreCase("left")) {
    moveLeft(es, g, gp, hs);
    if (getLeft(es) > 0) {
     moveEnemy(es, g, gp, "left", hs);
    } else if (getLeft(es) <= 0) {
     moveDown(es, g, gp);
     moveEnemy(es, g, gp, "right", hs);
    }
   }

   else {
    throw new InterruptedException();
   }
  } else {
   throw new InterruptedException();
  }
 }

 /** Method: stopExecutiing* stops enemy movement
  * 
  */
 public void stopExecuting() {
  execute = false;
 }

 /** Method: moveRight* moves the enemy array right
  * @param EnemyShip[][] es returns an array of enemy ships
  * @param Graphics g to repaint the image
  * @param GamePanel gp to represent the GamePanel method in GamePanel.java
  * @heroShip hs represents the HeroShip method in HeroShip.java
  */
 
 public void moveRight(EnemyShip[][] es, Graphics g, GamePanel gp,
   HeroShip hs) throws Exception {

  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip er = es[i][j];
    er.setX(er.getX() + 20* level);

   }
  }
  gnp.repaint();
  Thread.currentThread().sleep(300);
 }
 

 /** Method: moveLeft* moves the enemy array left
  * @param EnemyShip[][] es returns an array of enemy ships
  * @param Graphics g to repaint the image
  * @param GamePanel gp to represent the GamePanel method in GamePanel.java
  * @heroShip hs represents the HeroShip method in HeroShip.java
  */
 
 public void moveLeft(EnemyShip[][] es, Graphics g, GamePanel gp, HeroShip hs)
   throws Exception {

  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip er = es[i][j];
    er.setX(er.getX() - 20 * level);

   }
  }
  gnp.repaint();
  Thread.currentThread().sleep(300);
 }
 
 /** Method: moveDown* drops the array down the panel
  * @param EnemyShip[][] es returns an array of enemy ships
  * @param Graphics g to repaint the image
  * @param GamePanel gp to represent the GamePanel method in GamePanel.java
  */
 
 public void moveDown(EnemyShip[][] es, Graphics g, GamePanel gp)
   throws Exception {
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip er = es[i][j];
    er.setY(er.getY() + 20 * level);

   }
  }
  gnp.repaint();
  Thread.currentThread().sleep(300);

 }
 
 /** Method: isEnemyEmpty* returns true or false if an enemy is hit
  * @param EnemyShip[][] es returns an array of enemy ships
  * returns allHit if a ship is hit
  */
 
 public boolean isEnemyEmpty(EnemyShip[][] es) {
  Set<String> tf = new HashSet<String>();
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip ens = es[i][j];
    if(ens.isHit()){
     tf.add("true");
    }
    else {
     tf.add("false");
    }
   }
  }
  if(!tf.contains("false")) {
   allHit = true;
  }
  return allHit;
 }

 /** Method: getLeft* adjusts the movement left sooner if a whole column of enemies on the right is gone
  * @param EnemyShip[][] es returns an array of enemy ships
  * @return left, which is the x coordinate of the furthest left living ship
  */
 
 public int getLeft(EnemyShip[][] es) {
  int left = 1000;
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip ens = es[i][j];
    if (!ens.isHit()) {
     if (ens.getX() < left) {
      left = ens.getX();
     }
    }
   }
  }
  return left;
 }
 
 /** Method: getRight* adjusts the movement right sooner if a whole column of enemies on the left is gone
  * @param EnemyShip[][] es returns an array of enemy ships
  * @return right column of array's x coordinates
  */
 
 public int getRight(EnemyShip[][] es) {
  int right = 0;
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip ens = es[i][j];
    if (!ens.isHit()) {
     if (ens.getX() > right) {
      right = ens.getX();
     }
    }
   }
  }
  return right;
 }

 /** Method: getBottom* adjusts the movement down sooner if a whole row of enemies on the left is gone
  * @param EnemyShip[][] es returns an array of enemy ships
  * returns bottom row of the array's y coordinates
  */
 
 public int getBottom(EnemyShip[][] es) {
  int bottom = 0;
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    EnemyShip ens = es[i][j];
    if (!ens.isHit()) {
     if (ens.getY() > bottom) {
      bottom = ens.getY();
     }
    }
   }
  }
  return bottom;
 }

 /** Method: resetEnemy* resets enemy positions back to initial state
  * @param EnemyShip[][] es returns an array of enemy ships
  * 
  */
 
 public void resetEnemy(EnemyShip[][] es) {
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    if(i == 0){
    es[i][j] = new EnemyShip(100 * j, 100
      + 40 * i, 1);
    }
    else if (i == 1 || i == 2){
     es[i][j] = new EnemyShip(100 * j, 100
       + 40 * i, 2);
    }
    else if (i == 3){
     es[i][j] = new EnemyShip(100 * j, 100
       + 40 * i, 3);
    }

   }
  }
 }

 /** Method: clearEnemy* moves all enemies off the screen
  * @param EnemyShip[][] es returns an array of enemy ships
  * 
  */
 
 public void clearEnemy(EnemyShip[][] es) {
  for (int i = 0; i < 4; i++) {
   for (int j = 0; j < 5; j++) {
    es[i][j] = new EnemyShip(1000, 1000, 1);

   }
  }
 }

}
