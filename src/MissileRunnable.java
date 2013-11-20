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
	public static Graphics g;
	public static GamePanel gp;
	public static Image i;
	private static Missileship ms;
	private static String dir;

	public MissileRunnable(Graphics g, int startX, int startY, Missileship ms, GamePanel gp, String dir) {
		MissileRunnable.startX = startX;
		MissileRunnable.startY = startY;
		MissileRunnable.g = g;
		MissileRunnable.ms = ms;
		MissileRunnable.gp = gp;
		MissileRunnable.dir = dir;
		System.out.println(startY);
		// gp.stopBackgroundMusic();
		// gp.playBackgroundMusic();
	}

	@Override
	public void run() {
		try {
			moveMissile(startX, startY, dir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// g.drawImage(i, startX+30, startY, 20, 50, o);

	}

	public void moveMissile(int x, int y, String dir) throws Exception {
		System.out.println("get here");
		
		
		if (dir.equalsIgnoreCase("up")) {

			if (y > 0 && !ms.isHit()) {
//				startY = startY - 30;
				//y = startY;
				// if (y < 200) {
				// ms.setHit(true);
				// //gp.playBackgroundMusic();
				//
				// }
				 System.out.println(y);
				
				gp.paintMissile(ms, y-30);
					Thread.sleep(200);
					//gp.repaint();
					moveMissile(x, y-30, dir);
				//gp.repaint();
			} else {
				gp.repaint();
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

}
