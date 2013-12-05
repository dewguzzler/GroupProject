import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Jesse Perkins
 * Written: November 23th, 2013
 * 
 * EnemyMissileRunnable - shoots a missile from the hero's ship when the spacebar is pressed
 * 
 * Purpose - To shoot a missile at enemy targets
 * 
 */

public class EnemyMissileRunnable extends JPanel implements Runnable {

 /**
  * 
  */
 private static final long serialVersionUID = 1L;
 public static int startX; //starting x coordinate of the missile
 public static int startY; //starting y coordinate of the missile
 public static Graphics g; //graphics used
 public static GamePanel gp; //represents the GamePanel method from GamePanel.java
 public static Image i; //represents the missile image
 private static Missileship ms; //represents the Missileship method from Missileship.java
 private static String dir; //represents the direction the missile shoots
 private static HeroShip hs; // represents the HeroShip method from HeroShip.java
 private static Game jp; //represents the Game method from Gama.java

 public static final Lock lock = new ReentrantLock();

 /** Method: EnemyMissileRunnable* defines Graphics g, int startX, int startY,
  * Missileship ms, GamePanel gp. String dir, HeroShip hs and Game jp
  * @param Graphics g paints the images
  * @param int startX is the starting x coordinate of the missile fired
  * @param int startY is the starting y coordinate of the missile fired
  * @param String dir is the direction the missile shoots
  * @param HeroShip hs is the image pulled from the HeroShip method in HeroShip.java
  * @param Game jp is the Game method from Game.java
  */
 
 public EnemyMissileRunnable(Graphics g, int startX, int startY,
   Missileship ms, GamePanel gp, String dir, HeroShip hs, Game jp) {
  EnemyMissileRunnable.startX = startX;
  EnemyMissileRunnable.startY = startY;
  EnemyMissileRunnable.g = g;
  EnemyMissileRunnable.ms = ms;
  EnemyMissileRunnable.gp = gp;
  EnemyMissileRunnable.dir = dir;
  EnemyMissileRunnable.hs = hs;
  EnemyMissileRunnable.jp = jp;
 }

 /** Method: paintComponent * paints graphics
  * @param Graphics g paints the missile image 
  */
 
 public void paintComponent(Graphics g) {
  super.paintComponent(g);
 }

 /** Method: run * Runs the thread to shoot the missile
  * 
  */
 
 @Override
 public void run() {
  if (EnemyMissileRunnable.lock.tryLock()) {
   try {
    moveMissile(startX, startY, dir, hs);
   } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   } finally {
    EnemyMissileRunnable.lock.unlock();
   }
  }
  // g.drawImage(i, startX+30, startY, 20, 50, o);

 }

 /** Method: moveMissile * shoots a missile from the Hero ship and creates an explosion
  * @param int x x is the x coordinate of the missile fired
  * @param int y y is the y coordinate of the missile fired
  * @param String dir dir is the direction the missile shoots
  * @param HeroShip hs hs is the image pulled from the HeroShip method in HeroShip.java
  */

 public void moveMissile(int x, int y, String dir, HeroShip hs)
   throws Exception {

  if (dir.equalsIgnoreCase("up")) {

   if (y > 0 && !ms.isHit()) {
    if (y < 200) {
     ms.setHit(true);

    }

    gp.paintMissile(ms, y, x);
    Thread.currentThread().sleep(21);
    gp.repaint();
    Thread.currentThread().sleep(21);
    if (ms.isHit()) {

     gp.paintEnemyExplosion(y - 15, x - 20, 0);
     Thread.currentThread().sleep(100);
     gp.repaint();

    } else {
     moveMissile(x, y - 30, dir, hs);
    }
   }

   else {
    gp.repaint();
   }
  } else {
   if (y < 1000 && !ms.isHit()) {
    // startY = startY - 30;
    // y = startY;
    if (ms.getX() >= hs.getX() && ms.getX() <= hs.getX() + 100
      && ms.getY() >= hs.getY()) {

     ms.setHit(true);
     // gp.playBackgroundMusic();

    }

    gp.paintMissile(ms, y, x);
    Thread.currentThread().sleep(200);
    gp.repaint();
    Thread.currentThread().sleep(200);
    if (ms.isHit()) {

     gp.paintPlayerExplosion(y - 15, x - 20, jp);
     Thread.currentThread().sleep(1000);
     gp.repaint();

    } else {
     moveMissile(x, y + 30, dir, hs);
    }
   }

   else {
    gp.repaint();
   }
  }
 }

}
