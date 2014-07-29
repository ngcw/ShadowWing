/* SWEN20003 Object Oriented Software Development
 * Space Game Engine - Skeleton
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** Initialize world components */
	private Player player;
	private TiledMap map;
	private Camera cam ;
	private Panel panel;
	private ArrayList<Unit> npc;
	private ArrayList<Unit> rnpc;
	private ArrayList<Item> items;
	private ArrayList<Projectile> proj;
	
	/** Initialize constant variables */
	static final double SPEED = 0.25;
	private static final String itemPath = "data/items.txt";
	private static final String unitPath = "data/units.txt";
	
    /** Create a new World object. 
     * @throws IOException */
    public World()
    throws SlickException, IOException
    {
        map = new TiledMap(Game.ASSETS_PATH + Game.MAP_PATH ,Game.ASSETS_PATH);
        player = new Player();
        cam = new Camera(1296,13488);
        panel = new Panel();
        npc = new ArrayList<Unit>();
        rnpc = new ArrayList<Unit>();
        items = new ArrayList<Item>();
        proj = new ArrayList<Projectile>();
        itemParser(items);
        npcParser(npc);
        for (Unit u:npc){
        	rnpc.add(u);
        }
    }
    
    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param fire 	The player's fire missiles command (0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     * @throws IOException 
     */
    public void update(double dir_x, double dir_y, double fire, int delta)
    		throws SlickException, IOException{
    	// move player as time(delta) passes with a -0.25 decrease
    	// of pixels in y to represent a constant 0.25 speed 
    	// upwards
    	player.interact(this, dir_x, dir_y, fire, delta, npc);
    	// check if player is alive
    	player.checkAlive(cam, this);
    	// Update the camera based on the player's position
    	cam.move(this,delta);
    	// trigger to remove GameObject at its specific index
    	boolean removeTrig = false;
    	int remove = 0;
    	// Unit update phase
    	for (Unit u: npc){
    		if ((u.getY() > (cam.getCamY() - Game.screenheight/2))
        			&& (u.getY() < (cam.getCamY() + Game.screenheight))){
    			u.interact(this, delta, rnpc);
    			u.checkAlive();
    			if (!u.isAlive()){
    				remove = npc.indexOf(u);
            		removeTrig = true;
            	}
        	}
        }
    	if(removeTrig) {
        	npc.remove(remove);
    		rnpc.remove(remove);
    	}
        npc.trimToSize();
        rnpc.trimToSize();
        
        removeTrig = false;

        // Items Update Phase
        for(Item u : items) {
        	removeTrig = u.pickedup(player);
        	if(removeTrig) {
        		remove = items.indexOf(u);
        		break;
        	}
        }
        if(removeTrig)
        	items.remove(remove);
        items.trimToSize();
        
        removeTrig = false;
        
        // Projectile update phase
        for(Projectile p: proj){
    		p.move(this, delta, npc);
    		p.checkAlive();
    		// remove projectile if out of camera bounds
         	if (p.getY() <= 0){
         		remove = proj.indexOf(p);
        		removeTrig = true;
         	}else if (p.getY() >= cam.getCamY()+Game.screenheight){
         		remove = proj.indexOf(p);
        		removeTrig = true;
         	}
         	if (!p.isAlive()){
				remove = proj.indexOf(p);
        		removeTrig = true;
        	}
    	}
        if (removeTrig){
        	proj.remove(remove);
        }
        proj.trimToSize();
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    		throws SlickException{
    	// Calculate the camera location (in tiles) and offset (in pixels)
        int x = (int)cam.getCamX() % map.getTileWidth(); //offset
        int sx = (int)cam.getCamX() / map.getTileWidth(); //tiles
        int y = (int)cam.getCamY() % map.getTileHeight();
        int sy = (int)cam.getCamY() / map.getTileHeight();
        int width = Game.screenwidth / map.getTileWidth() + 2;    // 13
        int height = Game.screenheight / map.getTileHeight() + 2; // 10
        map.render(-x, -y, sx, sy,width,height);
        
        // render units, items and projectiles
        for (Unit u: npc){
        	u.render(g, cam.getCamX(), cam.getCamY());
        }
        for (Item i: items){
        	i.render(g, cam.getCamX(), cam.getCamY());
        }
        for (Projectile p: proj){
        	p.render(g, cam.getCamX(), cam.getCamY());
        }
        // Render the player and panel
        player.render(g, cam.getCamX(), cam.getCamY());
        panel.render(g, player.getShield(), player.getFullshield(), player.getFirepower());
    }
    
    /** Determines whether a particular map coordinate blocks movement.
     * @param x Map x coordinate (in pixels).
     * @param y Map y coordinate (in pixels).
     * @param radius Look ahead distance.
     * @return true if the coordinate blocks movement.
     */
	public boolean terrainBlocks(double x, double y, double radius)
    {
    	int tile_bounds[] = {(int) (x+radius)/ map.getTileWidth(),
    						 (int) (y+radius) / map.getTileHeight(),
    						 (int) (x+radius)/ map.getTileWidth(),
    						 (int) (y-radius) / map.getTileHeight(),
    						 (int) (x-radius)/ map.getTileWidth(),
    						 (int) (y+radius) / map.getTileHeight(),
    						 (int) (x-radius)/ map.getTileWidth(),
    						 (int) (y-radius) / map.getTileHeight()}; 
    	for (int i = 0; i < tile_bounds.length;i+=2){
	        int tileid = map.getTileId(tile_bounds[i], tile_bounds[i+1], 0);
	        String block = map.getTileProperty(tileid, "block", "0");
	        if (!block.equals("0")){
	        	return true;
	        }
    	}
		return false;
    }
	/** Resets all npc in world and clear all projectiles
	 * 
	 * @throws IOException
	 * @throws SlickException
	 */
	public void reset() throws IOException, SlickException{
		npc.clear();
		rnpc.clear();
		proj.clear();
		npcParser(npc);
		for (Unit u:npc){
        	rnpc.add(u);
        }
	}
	
	/** Parsing the npc from text file
	 * 
	 * @param npc Arraylist to parse all Units to
	 * @throws IOException
	 * @throws SlickException
	 */
	public void npcParser(ArrayList<Unit> npc) throws IOException, SlickException{
    	BufferedReader brUnit = new BufferedReader(new FileReader(unitPath));
		try {
			boolean prtNow = false;
	        String line = brUnit.readLine();
	        while (line != null) {
	        	if (prtNow){
	        		String[] words;
	        		if (line.contains("Asteroid")){
	        			words = line.split("	");
	        		}else{
	        			words = line.split("		");
	        		}
	        		if (words.length > 1){
	        			String[] coord = words[1].split(", ");
        				double x = Double.parseDouble(coord[0]);
        				double y = Double.parseDouble(coord[1]);
	        			if (words[0].equals("Fighter")){
	        				npc.add(new Fighter(x,y));
	        			}else if(words[0].equals("Asteroid")){
	        				npc.add(new Asteroid(x,y));
	        			}else if(words[0].equals("Drone")){
	        				npc.add(new Drone(x,y));
	        			}else if(words[0].equals("Boss")){
	        				npc.add(new Boss(x,y));
	        			}
	        		}
	        	}
	        	if (line.contains("Player		")){
	        		prtNow = true;
	        	}
	            line = brUnit.readLine();
	        }
	    } finally {
	        brUnit.close();
	    }
    }
	
	/** Parsing items from text file
	 * 
	 * @param items Arraylist to parse all items to
	 * @throws IOException
	 * @throws SlickException
	 */
    public void itemParser(ArrayList<Item> items) throws IOException, SlickException{
    	BufferedReader brItem = new BufferedReader(new FileReader(itemPath));
		try {
			boolean prtNow = false;
	        String line = brItem.readLine();
	        while (line != null) {
	        	if (line.contains("Shield		")){
	        		prtNow = true;
	        	}
	        	if (prtNow){
	        		String[] words;
	        		if (line.contains("Firepower")){
	        			words = line.split("	");
	        		}else{
	        			words = line.split("		");
	        		}
	        		if (words.length > 1){
	        			String[] coord = words[1].split(", ");
        				double x = Double.parseDouble(coord[0]);
        				double y = Double.parseDouble(coord[1]);
	        			if (words[0].equals("Repair")){
	        				items.add(new Repair(x,y));
	        			}else if(words[0].equals("Shield")){
	        				items.add(new Shield(x,y));
	        			}else if(words[0].equals("Firepower")){
	        				items.add(new Firepower(x,y));
	        			}
	        		}
	        	}
	            line = brItem.readLine();
	        }
	    } finally {
	        brItem.close();
	    }
    }
    
    // Getters and Setters
    /** get player */
	public Player getPlayer()
	{
		return player;
	}
	/** get camera */
	public Camera getCam()
	{
		return cam;
	}
	/** get projectile array list */
	public ArrayList<Projectile> getProj() {
		return proj;
	}
}
