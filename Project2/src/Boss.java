import java.util.ArrayList;

import org.newdawn.slick.SlickException;


public class Boss extends FiringUnit{
	// Image paths for Boss and its missile
	private static final String BOSS_PATH = "/units/boss.png";
	private static final String MISSILE_PATH = "/units/missile-enemy.png";

    // Instance variables
    private static int shield = 240;
    private static int damage = 100;
    private static int firepower = 3;
    private static int initPos = 1301;
    private static int moveAmt = 250;
    private int moveDir = -1;
	
    // Asteroid constructor
    /** Creates a Boss. 
	 * 
	 * @param x	The Initial X-Coordinates Spawn Point of the Boss. 
	 * @param y The Initial Y-Coordinates Spawn Point of the Boss. 
	 * @throws SlickException
	 */
	public Boss(double x, double y)
			throws SlickException {
		super(x, y, Game.ASSETS_PATH+BOSS_PATH, shield, damage,firepower);
	}
	
	/** Interaction between Boss and World
	 * 
	 * @param world The world of game
	 * @param delta Time frame
	 * @param rnpc An array list of rendered units in the world
	 */
	@Override
	public void interact(World world, double delta, ArrayList<Unit> rnpc) 
			throws SlickException{
		// cool down timer for firing
		this.setCooldownTime((int) (this.getCooldownTime() - delta));
		
		// Decides direction of movement
        if (this.getX() <= initPos-moveAmt){
        	setMoveDir(1);
        }else if (this.getX() >= initPos+moveAmt){
        	setMoveDir(-1);
        }
        double new_x = this.getX() + moveDir*(delta * this.getMoveSPEED());
        
        if (this.getCooldownTime()<=0){
        	world.getProj().add(new Projectile(new_x,this.getY()+50,Game.ASSETS_PATH+MISSILE_PATH,1));
        	this.setCooldownTime(1000 - (80 * this.getFirepower()));
        } 
        // commit new x-coordinate
        this.setX(new_x);
    }
	
	// Getter and Setters
	/**
	 * @param moveDir the moveDir to set
	 */
	public void setMoveDir(int moveDir) {
		this.moveDir = moveDir;
	}
	

}
