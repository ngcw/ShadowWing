
import java.util.ArrayList;

import org.newdawn.slick.SlickException;


public class Unit extends GameObject{
	
	// Instance variables
	private int shield;
	private int damage;
	private boolean alive;
	
	// Constant variables
    private static final double moveSPEED = 0.2;

 // Unit constructor subclass of GameObject
    /** Creates a Unit. 
	 * 
	 * @param x	The Initial X-Coordinates Spawn Point of Unit. 
	 * @param y The Initial Y-Coordinates Spawn Point of Unit. 
	 * @param image_path	The Path to Image of Unit. 
	 * @param shd The Initial Shield Value of Unit. 
	 * @param dmg The Damage Value of Unit.  
	 * @throws SlickException
	 */
	public Unit(double x, double y, String image_path, int shd, int dmg) 
			throws SlickException {
		super(x, y, image_path);
		this.setShield(shd);
		this.setDamage(dmg);
		this.alive = true;
	}
	
	/** 
	 * Checks if unit is alive
	 * by checking for shield points
	 */
	public void checkAlive() {
		if(this.getShield() <= 0) {
			this.setAlive(false);
		}
	}
	
	/** check for collision between units and inflict damage if collision occurs
	 * @param x	X-Coordinate of Midpoint of Unit
	 * @param y	Y-Coordinate of Midpoint of Unit
	 * @param npc	Unit to be Checked Against for Collision
	 */
	public void checkCollision(double x, double y, Unit npc)
	{
		// using half of image width with an offset of 5 as radius
		double radius = this.getImage().getWidth()/2 - 5;
		int check_bounds[] = {(int) (x+radius),(int) (y+radius),(int) (x+radius),(int) (y-radius),
				 (int) (x-radius),(int) (y+radius),(int) (x-radius),(int) (y-radius)}; 
		int npc_x_n = (int) (npc.getX() - radius);
		int npc_x_p = (int) (npc.getX() + radius);
		int npc_y_n = (int) (npc.getY() - radius);
		int npc_y_p = (int) (npc.getY() + radius);
		
		for (int i = 0; i<check_bounds.length; i+=2){
				if ((check_bounds[i] >= npc_x_n) && (check_bounds[i] <= npc_x_p)
						&& (check_bounds[i+1] >= npc_y_n) && (check_bounds[i+1] <= npc_y_p)){
					this.setShield(this.getShield()-npc.getDamage());
					npc.setShield(npc.getShield()-this.getDamage());
					break;
			}
		}

	}
	
	/**
	 * 
	 * @param world The world of game
	 * @param delta Time frame
	 * @param npc ArrayList of non-playable-components
	 * @throws SlickException
	 */
	public void interact(World world, double delta, ArrayList<Unit> npc) throws SlickException
	{

	}
	
	// Getter and Setters
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
		this.shield = shield;
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
		this.damage = damage;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return the movespeed
	 */
	public double getMoveSPEED() {
		return moveSPEED;
	}
}
