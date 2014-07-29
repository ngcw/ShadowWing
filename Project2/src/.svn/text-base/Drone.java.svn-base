import java.util.ArrayList;

import org.newdawn.slick.SlickException;


public class Drone extends Unit{
	// Image path to Drone
	private static final String DRONE_PATH = "/units/drone.png";
	
    // Instance variables
    private static int shield = 16;
    private static int damage = 8;
    
    // Drone constructor
    /** Creates an Drone. 
	 * 
	 * @param x	The Initial X-Coordinates Spawn Point of the Drone. 
	 * @param y The Initial Y-Coordinates Spawn Point of the Drone. 
	 * @throws SlickException
	 */
	public Drone(double x, double y)
			throws SlickException {
		super(x, y, Game.ASSETS_PATH+DRONE_PATH, shield, damage);
	}
	
	/** Interaction between Drone and world
	 * 
	 * @param world The world of game
	 * @param delta Time frame
	 * @param rnpc An array list of rendered units in the world
	 */
	@Override
	public void interact(World world, double delta, ArrayList<Unit> rnpc){
		// Algorithm of Drone chase
		double dist_x = this.getX() - world.getPlayer().getX();
		double dist_y = this.getY() - world.getPlayer().getY();
		double dist_t = Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2));
		double new_x = this.getX() - delta * dist_x/dist_t * this.getMoveSPEED();
        double new_y = this.getY() - delta * dist_y/dist_t * this.getMoveSPEED();
        double radius = this.getImage().getWidth()/2 - 5;
        
        // Check of terrain blocking
        if (world.terrainBlocks(new_x, new_y, radius))
     	{
     		this.setShield(this.getShield()-this.getDamage());
     	}
        // removes the current unit
        int index = rnpc.indexOf(this);
        rnpc.remove(index);
        // check for collisions between current unit and other units
        for (Unit u: rnpc){
        	this.checkCollision(this.getX(),new_y, u);
        }
        // restore current unit in arraylist
        rnpc.add(index, this);
        // commit new coordinate of unit
        this.setX(new_x);
        this.setY(new_y);
    }
}
