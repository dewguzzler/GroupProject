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

public class EnemyMissileRunnable extends JPanel implements Runnable {

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

	public static final Lock lock = new ReentrantLock();

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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

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
