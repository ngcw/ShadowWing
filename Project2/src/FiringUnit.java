import org.newdawn.slick.SlickException;

public class FiringUnit extends Unit {
	// Instance variables
    private int firepower;
    private int cooldownTime;
    
    // FiringUnit Constructors
    /**
     * 
     * @param x		The Initial X-Coordinates Spawn Point of Unit. 
	 * @param y		The Initial Y-Coordinates Spawn Point of Unit. 
	 * @param image_path	The Path to Image of Unit. 
	 * @param shd	The Initial Shield Value of Unit. 
	 * @param dmg	The Damage Value of Unit. 
     * @param fp	The Firepower value of Unit
     * @throws SlickException
     */
	public FiringUnit(double x, double y, String image_path, int shd, int dmg, int fp)
			throws SlickException {
		super(x, y, image_path, shd, dmg);
		this.setFirepower(fp);
	}

	// Getters and Setters
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
		this.firepower = firepower;
	}

	/**
	 * @return the cooldownTime
	 */
	public int getCooldownTime() {
		return cooldownTime;
	}

	/**
	 * @param cooldownTime the cooldownTime to set
	 */
	public void setCooldownTime(int cooldownTime) {
		this.cooldownTime = cooldownTime;
	}
}
