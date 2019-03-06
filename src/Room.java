import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
class Room
{
	private String description;
	private HashMap<String, Room> exits;
/**
* Create a room described "description". Initially, it
* has no exits. "description" is something like "a
* kitchen" or "an open court yard".
*/
	public Room(String description)
	{
		this.description = description;
		exits = new HashMap<String, Room>();
	}
/**
* Define the exits of this room. Every direction either
* leads to another room or is null (no exit there).
*/
	public void setExits(Room north, Room east, Room south,Room west, Room northWest, Room northEast, Room southWest, Room southEast)
	{
		if(north != null)
			exits.put("north", north);
		if(east != null)
			exits.put("east", east);
		if(south != null)
			exits.put("south", south);
		if(west != null)
			exits.put("west", west);
		if(northEast != null)
			exits.put("northeast", northEast);
		if(northWest != null)
			exits.put("northwest", northWest);
		if(southEast != null)
			exits.put("southeast", southEast);
		if(southWest != null)
			exits.put("southwest", southWest);
	}
	
	 public void setExit(String direction, Room voisin)
	    {
	        exits.put(direction, voisin);
	    }
/**
* Return the room that is reached if we go from this
* room in direction "direction". If there is no room in
* that direction, return null.
*/
	public Room getExit(String direction)
	{
		return exits.get(direction);
	}
	
	public String getExitString()
    {
        StringBuilder returnString = new StringBuilder("Sorties:");
        for ( String chaine : exits.keySet() )
            returnString.append( " " +  chaine);
        return description + ".\n" + returnString.toString();
    }
	 public String getLongDescription()
	 {
	    return getExitString();
	 }
/**
* Return the description of the room (the one that was
* defined in the constructor).
*/
	public String getDescription()
	{
		return description;
	}
}