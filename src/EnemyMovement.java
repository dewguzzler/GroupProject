import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
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
	private static Game theGame;
	public static final Lock lock = new ReentrantLock();
	private static HeroShip hns;
	public volatile boolean execute = true;
	public int count = 1;
	public static boolean allHit = false;

	public EnemyMovement(EnemyShip[][] es, Graphics g, GamePanel gp,
			HeroShip hs, Game stop, boolean startOver, int lives) {
		EnemyMovement.ens = es;
		EnemyMovement.gs = g;
		EnemyMovement.gnp = gp;
		EnemyMovement.hns = hs;
	}

	@Override
	public void run() {
		System.out.println(lock);
		while (execute) {
			if (EnemyMovement.lock.tryLock()) {
				try {
					System.out.println(lock);
					moveEnemy(EnemyMovement.ens, EnemyMovement.gs,
							EnemyMovement.gnp, "right", hns);
					// System.out.println(execute);
				} catch (InterruptedException p) {
					System.out.println("interupption");
					EnemyMovement.lock.unlock();
					System.out.println(lock);
					Thread.currentThread().interrupt();
					if(Thread.currentThread().isInterrupted()){
						execute = false;
						//gnp.livesUp();
					}
					System.out.println(Thread.currentThread().isInterrupted());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// g.drawImage(i, startX+30, startY, 20, 50, o);

	public void moveEnemy(EnemyShip[][] es, Graphics g, GamePanel gp,
			String direction, HeroShip hs) throws Exception {
		// System.out.println(pL);
		System.out.println(getBottom(es));
		System.out.println(isEnemyEmpty(es));
		if (isEnemyEmpty(es)) {
			System.out.println("next level please");
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
			allHit = false;
			Thread.currentThread().sleep(5000);
			gp.paintPlayerExplosion(hs.getY(), hs.getX(), theGame );
			// gnp.showGameOverScreen(theGame);
			System.out.println("done");
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
				System.out.println(getLeft(es));
				if (getLeft(es) > 0) {
					moveEnemy(es, g, gp, "left", hs);
				} else if (getLeft(es) <= 0) {
					moveDown(es, g, gp);
					moveEnemy(es, g, gp, "right", hs);
				}
			}

			else {
				System.out.println(getLeft(es));
				throw new InterruptedException();
			}
		} else {
			System.out.println("not sure why");
			throw new InterruptedException();
		}
	}

	//
	// System.out.println(getBottom(es));

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

	public void clearEnemy(EnemyShip[][] es) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				es[i][j] = new EnemyShip(1000, 1000, 1);

			}
		}
	}

}
