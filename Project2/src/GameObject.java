import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class GameObject {
	//Instance Variables
	private double x;
	private double y;
	private Image image;

	// GameObject Constructor
	/** Creates a typical game object with no functionalities whatsoever
	 * 
	 * @param x The X-Coordinates of the Game Object
	 * @param y The Y-Coordinates of the Game Object
	 * @param image_path The image file path 
	 * @throws SlickException 
	 */
	public GameObject(double x, double y, String image_path) 
	throws SlickException {
		this.x = x;
		this.y = y;
		this.image = new Image(image_path);
	}
	
	/** Draw the game object to the screen at the correct place.
	 * @param g 	Graphical Component for Game Object.
	 * @param cam_x Camera x position in pixels.
	 * @param cam_y Camera y position in pixels.
	 */
	public void render(Graphics g, double cam_x, double cam_y)
	{
		// Calculate the units's on-screen location from the camera
		int screen_x, screen_y;
		screen_x = (int) (this.getX() - cam_x);
		screen_y = (int) (this.getY() - cam_y);
		this.getImage().drawCentered(screen_x, screen_y);
	}
	
	//Getter and Setters
	/** GETTER: Gets the X-Coordinates of a Game Object. 
	 * @return x	X-Coordinates of a Game Object.
	 */
	public double getX() {
		return x;
	}
		
	/** SETTER: Sets the X-Coordinates of a Game Object.
	 * @param x 	X-Coordinates of a Game Object.
	 */
	public void setX(double x) {
		this.x = x;
	}
		
	/** GETTER: Gets the Y-Coordinates of a Game Object.
	 * @return y 	The Y-Coordinates of a Game Object.
	 */
	public double getY() {
		return y;
	}

	/** SETTER: Sets the Y-Coordinates of a Game Object. 
	 * @param y 	The Y-Coordinates of a Game Object.
	 */
	public void setY(double y) {
		this.y = y;
	}
		
	/** GETTER: Gets the Image of a Game Object.
	 * @return image 	The image of a Game Object.
	 */
	public Image getImage() {
		return image;
	}
}