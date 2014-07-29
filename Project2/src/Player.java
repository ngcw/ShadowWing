/* SWEN20003 Object Oriented Software Development
 * Author: Chin Wai Ng (ngcw)
 */
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Player extends FiringUnit
{

	//initialize Image variable to store player image
    private static final String PLAYER_PATH = "/units/player.png";
    private static final String MISSILE_PATH = "/units/missile-player.png";
    
    // Constant variables
    private static final double moveSPEED = 0.4;
	// starting position of player
    private static final int START_X = 1296;
    private static final int START_Y = 13716;
    
    // Instance variables
    private static int shield = 100;
	private static int fullshield = 100;
    private static int damage = 10;
    private static int firepower = 0;
    
	/** Creates a new Player.
     */
    public Player()
        throws SlickException{
    	super(START_X, START_Y, Game.ASSETS_PATH + PLAYER_PATH, shield, damage, firepower);
    }

    /** Interaction between player and the world
     * Prevents the player from moving outside the map space
     * @param world The world the player is on (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param fire	The player's fire missiles command (0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param npc	Arraylist of non-playable components
     * @throws SlickException 
     */
    public void interact(World world, double dir_x, double dir_y, double fire, double delta, 
    		ArrayList<Unit> npc) throws SlickException
    {
    	// Cool down timer for firing
    	this.setCooldownTime((int) (this.getCooldownTime() - delta));
        // Move the player by dir_x, dir_y, as a multiple of delta * speed
        double new_x = this.getX() + dir_x * delta * moveSPEED;
        double new_y = this.getY() + dir_y * delta * moveSPEED;
        double radius = this.getImage().getWidth()/2 - 5;
        
        // player stop moving automatically when top y-coordinate reaches 0
        if (world.getCam().getCamY()  > 0){
        	new_y = this.getY() + dir_y - delta * World.SPEED;
        }
        // assign boundaries of camera
        int camTopBound = (int)(world.getCam().getCamY() + radius);
        int camBtmBound = (int)(world.getCam().getCamY() + Game.screenheight 
        		- radius - Panel.PANEL_HEIGHT);
        
        // Check if the player is on a tile which blocks movement, and stop
        if (world.terrainBlocks(new_x, new_y, radius)){
     		// Reset either the x or the y or both to the old one
     		if (!world.terrainBlocks(this.getX(), new_y, radius))
     			new_x = this.getX();
     		else if (!world.terrainBlocks(new_x, this.getY(), radius))
   				new_y = this.getY();
   			else {
     			new_x = this.getX();
     			new_y = this.getY();
     		}
     	}
        // prevents player from moving off top boundaries of the camera
        if ((int)new_y <= camTopBound){
        	new_y = this.getY() + 1;
        // prevents player from moving off bottom boundaries of the camera
        }else if ((int)new_y >= camBtmBound  && new_y != this.getY())
        {
        	new_y = this.getY() - 1;
        // force player out of camera vision if blocked and player is killed immediately
        }else if ((int)new_y >= camBtmBound && new_y == this.getY())
        {
        	new_y = this.getY();
        	if (this.getY()-radius > camBtmBound){
        		this.setAlive(false);
        	}
        }
        // check for collision with npc units
        for (Unit u: npc){
        	this.checkCollision(new_x,new_y, u);
        }
        if ((fire !=0) && (this.getCooldownTime()<=0)){
        	world.getProj().add(new Projectile(new_x,new_y-50,Game.ASSETS_PATH+MISSILE_PATH,-1));
        	this.setCooldownTime(300 - (80 * this.getFirepower()));
        }       
        // Commit to the new location
        this.setX(new_x);
        this.setY(new_y);
    }
    
    /** Check if player is alive and respawn if player dies
     * 
     * @param cam Camera of world
     * @param world	World of game
     * @throws SlickException
     * @throws IOException
     */
	public void checkAlive(Camera cam, World world) throws SlickException, IOException {
		int[] checkpoints = {13716, 9756, 7812, 5796, 2844, 0};
		if(this.getShield() <= 0) {
			this.setAlive(false);
		}
		if(!isAlive()) {
			// resets everything on respawn
			setX(START_X);
			cam.setCamX(START_X);
			for (int i = 0; i < checkpoints.length; i++){
				if (this.getY() >= checkpoints[i]){
					setY(checkpoints[i-1]);
					cam.setCamY(checkpoints[i-1] - Game.screenheight + 72);
					break;
				}
			}
			setAlive(true);
			setFullshield(100);
			setShield(getFullshield());
			setFirepower(0);
			world.reset();
		}
	}
	
	// Getters and Setters
	/**
	 * @return the shield
	 */
	public int getShield() {
		return shield;
	}

	/**
	 * @param shield the shield to set
	 */
	public void setShield(int shield) {
		Player.shield = shield;
	}

	/**
	 * @return the fullshield
	 */
	public int getFullshield() {
		return fullshield;
	}

	/**
	 * @param fullshield the fullshield to set
	 */
	public void setFullshield(int fullshield) {
		Player.fullshield = fullshield;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		Player.damage = damage;
	}

	/**
	 * @return the firepower
	 */
	public int getFirepower() {
		return firepower;
	}

	/**
	 * @param firepower the firepower to set
	 */
	public void setFirepower(int firepower) {
		Player.firepower = firepower;
	}
    
}
