import java.awt.Graphics;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

public class EnemyMovement extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EnemyShip[][] ens;
	private static Graphics gs;
	private static GamePanel gnp;
	public static final Lock lock = new ReentrantLock();
	private static HeroShip hns;
	private static Game theGame;
	private static boolean playerHit = false;
	private static int pL;
	private static boolean startingOver = false;
	public volatile boolean execute;
	public int count = 1;

	public EnemyMovement(EnemyShip[][] es, Graphics g, GamePanel gp,
			HeroShip hs, Game stop, boolean startOver, int lives) {
		EnemyMovement.ens = es;
		EnemyMovement.gs = g;
		EnemyMovement.gnp = gp;
		EnemyMovement.hns = hs;
		EnemyMovement.theGame = stop;
		EnemyMovement.startingOver = startOver;
		EnemyMovement.pL = lives;
		this.execute = true;
	}

	@Override
	public void run() {
		while (execute) {
			try {
				moveEnemy(EnemyMovement.ens, EnemyMovement.gs,
						EnemyMovement.gnp, "right", hns);
				System.out.println("omg");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// g.drawImage(i, startX+30, startY, 20, 50, o);

	public void moveEnemy(EnemyShip[][] es, Graphics g, GamePanel gp,
			String direction, HeroShip hs) throws Exception {
		while (pL > 0) {
			System.out.println(execute);
			if (startingOver) {
				playerHit = false;
			}

			if (direction.equalsIgnoreCase("right") && getBottom(es) < 485) {
				moveRight(es, g, gp, hs);
			}
			if (direction.equalsIgnoreCase("left") && getBottom(es) < 485) {
				moveLeft(es, g, gp, hs);
			} else {
				gp.paintPlayerExplosion(hs.getX(), hs.getY(), theGame);
				pL = pL - 1;
				System.out.println(pL);
				if (pL > 0) {
					// stopExecuting();
					// startingOver = true;
					resetEnemy(es);
					gp.repaint();
					Thread.currentThread().sleep(2000);
					//
					moveRight(es, g, gp, hs);
				} else {
					gp.paintPlayerExplosion(hs.getX(), hs.getY(), theGame);
				}
			}

			//
			// System.out.println(getBottom(es));
		}
	}

	public void stopExecuting() {
		execute = false;
	}

	public void moveRight(EnemyShip[][] es, Graphics g, GamePanel gp,
			HeroShip hs) throws Exception {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				EnemyShip er = es[i][j];
				er.setX(er.getX() + 20);

			}
		}
		gnp.repaint();
		Thread.currentThread().sleep(300);
		if (getRight(es) < 800) {

			System.out.println(getLeft(es) + " " + getRight(es));
			moveEnemy(es, g, gp, "right", hns);
		} else {
			moveDown(es, g, gp);
			moveEnemy(es, g, gp, "left", hns);
		}
	}

	public void moveLeft(EnemyShip[][] es, Graphics g, GamePanel gp, HeroShip hs)
			throws Exception {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				EnemyShip er = es[i][j];
				er.setX(er.getX() - 20);

			}
		}
		gnp.repaint();
		Thread.currentThread().sleep(300);
		if (getLeft(es) > 0) {

			System.out.println(getLeft(es) + " " + getRight(es));
			moveEnemy(es, g, gp, "left", hns);
		} else {
			moveDown(es, g, gp);
			moveEnemy(es, g, gp, "right", hns);
		}
	}

	public void moveDown(EnemyShip[][] es, Graphics g, GamePanel gp)
			throws Exception {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				EnemyShip er = es[i][j];
				er.setY(er.getY() + 100);

			}
		}
		gnp.repaint();
		Thread.currentThread().sleep(300);

	}

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

	public void resetEnemy(EnemyShip[][] es) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				es[i][j] = new EnemyShip(100 * j, 100 + 40 * i);

			}
		}
	}

}
