import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Projectile extends Unit{
	// Constant variables
	private static final double PROJSPEED= 0.7;
	private static final int DAMAGE = 8;
	private static final int SHIELD = 1;
	// Instance variable
	private int dir;
	
	/**
	 * @param x The Initial X-Coordinates Spawn Point of the Projectile. 
	 * @param y	The Initial Y-Coordinates Spawn Point of the Projectile. 
	 * @param image_path The image file path
	 * @param dir The direction of projectile movement
	 * @throws SlickException
	 */
	public Projectile(double x, double y, String image_path, int dir)
			throws SlickException {
		super(x, y, image_path, SHIELD, DAMAGE);
		this.setDir(dir);
	}
	
	/** Movement and interaction of Projectile with other units and world
	 * 
	 * @param world The world of game
	 * @param delta Time passed since last frame (milliseconds).
	 * @param npc	Array list of non-playable components
	 */
	public void move(World world, double delta, ArrayList<Unit> npc){
		double radius = 5;
        double new_y = this.getY() + this.getDir()*(delta * PROJSPEED);
        // Collision with tiles
        if (world.terrainBlocks(this.getX(), new_y, radius))
     	{
			this.setShield(this.getShield()-this.getDamage());
     	} 
        // Collision with Units
        for (Projectile p: world.getProj()){
        	if (this.getDir() == -1){
        		for (Unit u: npc){
        			if ((u.getY() > (world.getCam().getCamY() - Game.screenheight/2))
                			&& (u.getY() < (world.getCam().getCamY() + Game.screenheight))){
        				p.projHit(this.getX(), new_y, u);
        			}
        		}
        	}else{
        		p.projHit(this.getX(), new_y, world.getPlayer());
        		break;
        	}
        }
        // Commit to the new location
        this.setY(new_y);
    }
	
	/** Damage dealt on Units when Projectile hits
	 * @param x The Initial X-Coordinates of the Projectile. 
	 * @param y	The Initial Y-Coordinates of the Projectile. 
	 * @param npc The Unit of collision check
	 */
	public void projHit(double x, double y, Unit npc){
		double radius = 5;
		double unit_radius = this.getImage().getWidth()/2 - 5;
		int check_bounds[] = {(int) (x+radius),(int) (y+radius),(int) (x+radius),(int) (y-radius),
				 (int) (x-radius),(int) (y+radius),(int) (x-radius),(int) (y-radius)}; 
		int npc_x_n = (int) (npc.getX() - unit_radius);
		int npc_x_p = (int) (npc.getX() + unit_radius);
		int npc_y_n = (int) (npc.getY() - unit_radius);
		int npc_y_p = (int) (npc.getY() + unit_radius);
		for (int i = 0; i<check_bounds.length; i+=2){
				if ((check_bounds[i] >= npc_x_n) && (check_bounds[i] <= npc_x_p)
						&& (check_bounds[i+1] >= npc_y_n) && (check_bounds[i+1] <= npc_y_p)){
					this.setShield(this.getShield()-npc.getDamage());
					npc.setShield(npc.getShield()-DAMAGE);
					break;
			}
		}
	}
	
	// Getters and Setters
	/**
	 * @return the dir
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}
	

	

}
