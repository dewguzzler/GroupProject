import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

/**
 * Class: MissileRunnable
 * 
 * @author Jesse Perkins Date: November 24th 2013
 * 
 *         This class - runs a thread for shooting missiles
 * 
 *         Purpose - so the player can shoot enemies
 */

public class MissileRunnable extends JPanel implements Runnable {

	/**
  * 
  */
	private static final long serialVersionUID = 1L;
	public static int startX; // initial x coordinate of the missile
	public static int startY; // initial y coordinate of the missile
	public static Graphics g; // graphics representation
	public static GamePanel gp; // represents the public GamePanel method in
								// GamePanel.java
	public static Image i; // represents the missile image
	private static Missileship ms; // represents the method Missileship from
									// Missileship.java
	private static String dir; // direction missile is moving in
	private static HeroShip hs; // represents the method HeroShip from
								// HeroShip.java
	private static Game jp; // represents the method Game from Game.java
	private static EnemyShip[][] ens; // creates an array of enemy ships through
										// the method EnemyShip from
										// EnemyShip.java
	private static EnemyShip eShipHit; // determines if a ship is hit
	private static int points; // # of points added for a ship being hit

	public static final Lock lock = new ReentrantLock(); // locks the thread

	public MissileRunnable(Graphics g, int startX, int startY, Missileship ms,
			GamePanel gp, String dir, HeroShip hs, Game jp, EnemyShip[][] es) {
		MissileRunnable.startX = startX;
		MissileRunnable.startY = startY;
		MissileRunnable.g = g;
		MissileRunnable.ms = ms;
		MissileRunnable.gp = gp;
		MissileRunnable.dir = dir;
		MissileRunnable.hs = hs;
		MissileRunnable.jp = jp;
		MissileRunnable.ens = es;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void run() {
		if (MissileRunnable.lock.tryLock()) {
			try {

				gp.playFiringMusic();
				moveMissile(startX, startY, dir, hs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				MissileRunnable.lock.unlock();
			}
		}

	}

	/**
	 * Method: moveMissile* moves a missile up to shoot enemies
	 * 
	 * @param int x returns x coordinate of the missile
	 * @param int y returns the y coordinate of the missile
	 * @param String
	 *            dir is the direction the missile moves
	 * @param HeroShip
	 *            hs is the method HeroShip from HeroShip.java
	 */

	public void moveMissile(int x, int y, String dir, HeroShip hs)
			throws Exception {

		if (dir.equalsIgnoreCase("up")) {

			if (y > 0 && !ms.isHit()) {
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 5; j++) {
						EnemyShip es = ens[i][j];
						if (x >= es.getX() && x <= es.getX() + 100
								&& y <= es.getY()) {
							ms.setHit(true);
							eShipHit = es;
							es.setX(1000);
							es.setY(1000);
							points = es.getPoint();
							es.setHit(true);

							break;
						}

					}
				}
				gp.paintMissile(ms, y, x);
				Thread.currentThread().sleep(21);
				gp.repaint();
				Thread.currentThread().sleep(21);
				if (ms.isHit()) {

					gp.paintEnemyExplosion(y - 15, x - 20, points);

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
			if (y < 600 && !ms.isHit()) {

				gp.paintMissile(ms, y, x);
				gp.playFiringMusic();
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
