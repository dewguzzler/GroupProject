import javax.swing.ImageIcon;

/** Class: HeroShip.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Nov 14, 2013
 *
 */
public class HeroShip extends Ship
{
	private static final long serialVersionUID = 1L;

	public HeroShip()
	{
		super(new ImageIcon(HeroShip.class.getResource("res/images/HeroShip.png")),
				380, 500);
	}
}