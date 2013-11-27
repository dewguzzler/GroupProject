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
