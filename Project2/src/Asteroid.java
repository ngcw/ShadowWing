import java.util.ArrayList;

import org.newdawn.slick.SlickException;


public class Asteroid extends Unit{
	// Image path of asteroid
    private static final String ASTEROID_PATH = "/units/asteroid.png";
    
    // Instance variables
    private static int shield = 24;
    private static int damage = 12;
    
    // Asteroid constructor
    /** Creates an Asteroid. 
	 * 
	 * @param x	The Initial X-Coordinates Spawn Point of the Asteroid. 
	 * @param y The Initial Y-Coordinates Spawn Point of the Asteroid. 
	 * @throws SlickException
	 */
	public Asteroid(double x, double y)
			throws SlickException {
		super(x, y, Game.ASSETS_PATH+ASTEROID_PATH, shield, damage);
	}
	
	/** Interaction between Asteroid and world
	 * 
	 * @param world The world of game
	 * @param delta Time frame
	 * @param rnpc An array list of rendered units in the world
	 */
	@Override
	public void interact(World world, double delta, ArrayList<Unit> rnpc){
        double new_y = this.getY() + delta * this.getMoveSPEED();
        double radius = this.getImage().getWidth()/2 - 5;
        
        // Check of terrain blocking
        if (world.terrainBlocks(this.getX(), new_y, radius)){
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
        this.setY(new_y);
    }
}
