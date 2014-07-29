import org.newdawn.slick.SlickException;

public class Firepower extends Item{
	// Image path for Firepower item
	private static final String FIREPOWER_PATH = "/items/firepower.png";
	
	// Firepower Constructor
	/**
	 * 
	 * @param x	The Initial X-Coordinates Spawn Point of the Firepower. 
	 * @param y The Initial Y-Coordinates Spawn Point of the Firepower. 
	 * @throws SlickException
	 */
	public Firepower(double x, double y)
			throws SlickException {
		super(x, y, Game.ASSETS_PATH+FIREPOWER_PATH);
	}
	
	/** Checks for collision between player and item and increases player
	 * firepower if collision happens
	 */
	public boolean pickedup(Player player) {
		double x = player.getX();
		double y = player.getY();
		double radius = this.getImage().getWidth()/2 - 5;
		double p_radius = player.getImage().getWidth()/2 -5;
		int check_bounds[] = {(int) (x+p_radius),(int) (y+p_radius),(int) (x+p_radius),(int) (y-p_radius),
				 (int) (x-p_radius),(int) (y+p_radius),(int) (x-p_radius),(int) (y-p_radius)}; 
		int npc_x_n = (int) (this.getX() - radius);
		int npc_x_p = (int) (this.getX() + radius);
		int npc_y_n = (int) (this.getY() - radius);
		int npc_y_p = (int) (this.getY() + radius);

		for (int i = 0; i<check_bounds.length; i+=2){
				if ((check_bounds[i] >= npc_x_n) && (check_bounds[i] <= npc_x_p)
						&& (check_bounds[i+1] >= npc_y_n) && (check_bounds[i+1] <= npc_y_p)){
					if (player.getFirepower() < 3){
						player.setFirepower(player.getFirepower()+1);
					}
					return true;
			}
		}
		return false;
	}
}
