import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author jesse
 * 
 */

public class MissileRunnable extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int startX;
	public static int startY;
	public static Graphics g;
	public static GamePanel gp;
	public static Image i;
	private static Missileship ms;
	private static String dir;
	private static HeroShip hs;
	private static Game jp;
	private static EnemyShip[][] ens;
	private static EnemyShip eShipHit;
	private static int points;

	public static final Lock lock = new ReentrantLock();

	public MissileRunnable(Graphics g, int startX, int startY, Missileship ms, GamePanel gp, String dir, HeroShip hs,
			Game jp, EnemyShip[][] es) {
		MissileRunnable.startX = startX;
		MissileRunnable.startY = startY;
		MissileRunnable.g = g;
		MissileRunnable.ms = ms;
		MissileRunnable.gp = gp;
		MissileRunnable.dir = dir;
		MissileRunnable.hs = hs;
		MissileRunnable.jp = jp;
		MissileRunnable.ens = es;
		//System.out.println(startY);
		// gp.stopBackgroundMusic();
		// gp.playBackgroundMusic();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	@Override
	public void run() {
		 if(MissileRunnable.lock.tryLock()) {
		try {

			gp.playFiringMusic();
			moveMissile(startX, startY, dir, hs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  finally
         {
             MissileRunnable.lock.unlock();
         }
		 }
		// g.drawImage(i, startX+30, startY, 20, 50, o);

	}

	public void moveMissile(int x, int y, String dir, HeroShip hs) throws Exception {
		
		
		if (dir.equalsIgnoreCase("up")) {

			 
			if (y > 0 && !ms.isHit()) {
//				startY = startY - 30;
				//y = startY;
				for(int i = 0; i< 4; i++) {
					for(int j = 0; j < 5; j++) {
						EnemyShip es = ens[i][j];
						if(x >= es.getX() && x <= es.getX()+ 100  && y <= es.getY()){
							ms.setHit(true);
							eShipHit = es;
							es.setX(1000);
							es.setY(1000);
							points = es.getPoint();
							es.setHit(true);
							//System.out.println(ms.getX() + " " + es.getX()  + " " + ms.getY()  + " " + es.getY() + " " + x + " " + y);
							break;
						}
						
					}
				}
				 //System.out.println(y);
				gp.paintMissile(ms, y, x);
					Thread.currentThread().sleep(21);
					gp.repaint();
					Thread.currentThread().sleep(21);
					if (ms.isHit()) {
						 
						
							gp.paintEnemyExplosion(y-15, x-20, points);
							
							Thread.currentThread().sleep(100);
							gp.repaint();
							
						} else {
					moveMissile(x, y-30, dir, hs);
						}
				//gp.repaint();
			}
			
			else {
				gp.repaint();
			}
		} else {
			if (y < 600 && !ms.isHit()) {
//				startY = startY - 30;
				//y = startY;
//				 if (ms.getX() >= hs.getX() && ms.getX() <= hs.getX()+ 100  && ms.getY() >= hs.getY()) {
//				 System.out.println(ms.getY() + " " + hs.getY()+ " " + hs.getX()+ " " + ms.getX());
//					 ms.setHit(true);
//				 //gp.playBackgroundMusic();
//				
//				 }
				 //System.out.println(y);
				
				gp.paintMissile(ms, y, x);
				gp.playFiringMusic();
					Thread.currentThread().sleep(200);
					gp.repaint();
					Thread.currentThread().sleep(200);
					if (ms.isHit()) {
						 
						
							gp.paintPlayerExplosion(y-15, x-20, jp);
							Thread.currentThread().sleep(1000);
							gp.repaint();
							
						} else {
					moveMissile(x, y+30, dir, hs);
						}
				//gp.repaint();
			}
			
			else {
				gp.repaint();
			}
		}
		}
//		} else {
//
//			while (startY < 600 && !ms.isHit()) {
//				try {
//
//					startY = startY + 30;
//					y = startY;
//					// if (ms.getY()) {
//					// ms.setHit(true);
//					// }
//					// System.out.println(y);
//
//					ms.setY(y);
//					System.out.println(y);
//
//					ms.draw(g);
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// ms.draw(g);
//			}
//
//		}
//
	}

