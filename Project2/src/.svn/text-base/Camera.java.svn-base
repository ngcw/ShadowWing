/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Chin Wai Ng
 */
public class Camera {
	// Camera location, in pixels
    private double cam_x,cam_y;
    
    /** Camera constructor */
    public Camera(double x, double y)
    {
    	setCamX(x);
    	setCamY(y);
    }
    
    /**
     * 
     * @param world The World of Game	
     * @param delta	Time passed since last frame (milliseconds).
     */
    public void move(World world, double delta)
    {
        // Move the player by dir_x, dir_y, as a multiple of delta * speed
    	double new_x = world.getPlayer().getX()- (Game.screenwidth/2);
    	double new_y = this.cam_y  - delta * World.SPEED;;
    	// camera stop moving when top y-coordinate is 0
    	if (this.cam_y  < 0)
    	{
    		new_y = this.cam_y;
    	}

        // Commit to the new location
        this.cam_x = new_x;
        this.cam_y = new_y;
    }
    
    // Getter and Setters
    /** Camera X location, in pixels*/
    public double getCamX()
    {
        return cam_x;
    }

    /** Camera Y location, in pixels */
    public double getCamY()
    {
        return cam_y;
    }
    /** Set Camera X location, in pixels */
    public void setCamX(double newCam_X)
    {
        cam_x = newCam_X;
    }

    /** Set Camera Y location, in pixels */
    public void setCamY(double newCam_Y)
    {
    	cam_y = newCam_Y;
    }
}
