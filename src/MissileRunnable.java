import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;
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
	public Graphics g;
	public static GamePanel gp;
	public static Image i;
	private static Missileship ms;
	private static String dir;

	public MissileRunnable(Graphics g, int startX, int startY, Missileship ms,
			BufferedImage i, GamePanel gp, String dir) {
		MissileRunnable.startX = startX;
		MissileRunnable.startY = startY;
		this.g = g;
		MissileRunnable.i = i;
		MissileRunnable.ms = ms;
		MissileRunnable.gp = gp;
		MissileRunnable.dir = dir;
		System.out.println(startY);
		//gp.stopBackgroundMusic();
		//gp.playBackgroundMusic();
	}

	@Override
	public void run() {
		ms.setX(startX);
		ms.setY(startY);
		gp.repaint();
		ms.setOpaque(false);
		ms.draw(g);
		// g.drawImage(i, startX+30, startY, 20, 50, o);
		if (dir.equalsIgnoreCase("up")) {
			

			while (startY > 0 && !ms.isHit()) {
				try {
					Thread.sleep(24);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				startY = startY - 30;
				int y = startY;
//				if (y < 200) {
//					ms.setHit(true);
//					//gp.playBackgroundMusic();
//				
//				}
				// System.out.println(y);
				moveMissile(startX, y, dir);
				// ms.draw(g);
			}
		} else {

			while (startY < 600 && !ms.isHit()) {
				try {
					Thread.sleep(24);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				startY = startY + 30;
				int y = startY;
//				if (ms.getY()) {
//					ms.setHit(true);
//				}
				// System.out.println(y);
				moveMissile(startX, y, dir);
				// ms.draw(g);
			}

		}

	}

	public void moveMissile(int x, int y, String dir) {
		// g.drawImage(i, x, y-30, 20, 50, o);
		// startY = y -30;
		ms.setX(x);
		ms.setY(startY);
		ms.draw(g);
		gp.repaint();
		ms.setOpaque(false);
		

	}

}
