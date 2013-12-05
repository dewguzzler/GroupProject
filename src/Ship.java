import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * Class: Ship.java
 * 
 * @authors Aida Syrkett, Jesse Perkins, Everett Rucker
 * @version 1.0 Written: Nov 14, 2013 This class - sets the coordinates of ships
 *          and pulls an image to represent each ship
 * 
 *          Purpose- to show ships in the game panel
 * 
 */
public class Ship extends JComponent {
	private static final long serialVersionUID = 1L;
	private int x; // x coordinate of a ship
	private int y; // y coordinate of a ship
	private ImageIcon icon; // image of a ship
	private boolean isHit; // returns true or false if a ship is hit
	public int pointsWorth; // number of points a destroyed ship is worth

	/**
	 * Method Ship sets the following param below:
	 * 
	 * @param ImageIcon
	 *            icon is an image
	 * @param int x is the x coordinate of a ship
	 * @param int y is the y coordinate of a ship
	 * @param int points is the points a ship is worth
	 */

	public Ship(ImageIcon icon, int x, int y, int points) {
		super();
		this.icon = icon;
		this.x = x;
		this.y = y;
		this.pointsWorth = points;
	}

	/**
	 * Method getX gets the x coordinate
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	public int getPoint() {
		return pointsWorth;
	}

	/**
	 * Method: setX
	 * 
	 * @param int x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Method: getY
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * setY sets the y coordinate
	 * 
	 * @param int y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Method: getIcon
	 * 
	 * @return the icon
	 */
	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * Method: setIcon* sets icon
	 * 
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	/**
	 * Method isHit returns the boolean isHit
	 * 
	 * @return the isHit
	 */

	public boolean isHit() {
		return isHit;
	}

	/**
	 * Method: setHit defines isHit
	 * 
	 * @param isHit
	 *            the isHit to set
	 */

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

	/**
	 * Method: draw* draws hero
	 * 
	 * @param Graphics
	 *            g paints image
	 */

	public void draw(Graphics g) {
		g.drawImage(icon.getImage(), x, y, 100, 100, this);
	}

	/**
	 * Method: drawEnemy* draws enemy
	 * 
	 * @param Graphics
	 *            g paints image
	 */

	public void drawEnemy(Graphics g) {

		g.drawImage(icon.getImage(), x, y, 100, 38, this);
	}
}