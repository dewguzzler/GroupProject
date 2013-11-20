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
public class Missileship extends Ship
{
	private static final long serialVersionUID = 1L;

	public Missileship()
	{
		super(new ImageIcon(Missileship.class.getResource("res/images/bullet.png")),
				380, 500);
	}
}