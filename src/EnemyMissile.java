import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author jesse
 *
 */
public class EnemyMissile extends Ship
{
	private static final long serialVersionUID = 1L;

	public EnemyMissile()
	{
		super(new ImageIcon(EnemyMissile.class.getResource("res/images/bullet.png")),
				200, 500, 0);
	}
}