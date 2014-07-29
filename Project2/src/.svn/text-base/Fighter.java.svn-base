import java.util.ArrayList;

import org.newdawn.slick.SlickException;


public class Fighter extends FiringUnit{
	// Image path for Fighter and its missiles
	private static final String FIGHTER_PATH = "/units/fighter.png";
	private static final String MISSILE_PATH = "/units/missile-enemy.png";
	
    // Instance variables
    private static int shield = 24;
    private static int damage = 9;
    private static int firepower = 0;
    
    // Fighter constructor
    /** Creates an Fighter. 
	 * 
	 * @param x	The Initial X-Coordinates Spawn Point of the Fighter. 
	 * @param y The Initial Y-Coordinates Spawn Point of the Fighter. 
	 * @throws SlickException
	 */
	public Fighter(double x, double y) 
			throws SlickException {
		super(x, y, Game.ASSETS_PATH+FIGHTER_PATH, shield, damage,firepower);
	}
	
	/** Interaction between Fighter and world
	 * 
	 * @param world The world of game
	 * @param delta Time frame
	 * @param rnpc An array list of rendered units in the world
	 */
	@Override
	public void interact(World world, double delta, ArrayList<Unit> rnpc) 
			throws SlickException {
		// cool down timer for firing
		this.setCooldownTime((int) (this.getCooldownTime() - delta));
		double new_x = this.getX();
        double new_y = this.getY() + delta * this.getMoveSPEED();
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
        if (this.getCooldownTime()<=0){
        	world.getProj().add(new Projectile(new_x,new_y+50,Game.ASSETS_PATH+MISSILE_PATH,1));
        	this.setCooldownTime(1000 - (80 * this.getFirepower()));
        }  
        // commit new coordinate of unit
        this.setX(new_x);
        this.setY(new_y);
    }
}
