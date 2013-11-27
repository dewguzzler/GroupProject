import javax.swing.ImageIcon;


public class Explosion extends Ship
{
	private static final long serialVersionUID = 1L;

	public Explosion()
	{
		super(new ImageIcon(HeroShip.class.getResource("res/images/explosion.png")),
				380, 500, 0);
	}
}