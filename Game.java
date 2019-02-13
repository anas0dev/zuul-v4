/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {

      
        // create the rooms
        Room cocoyashi = new Room("Bienvenue à Cocoyashi");
        Room nooberland = new Room("Bienvenue à Nooberland");
        Room wano_kuni = new Room("Bienvenue à Wano_kuni");
        Room water7 = new Room("Bienvenue à Water7");
        Room kalen = new Room("Bienvenue à Kalen");
        Room ortopia = new Room("Bienvenue à Ortopia");
        Room alabasta = new Room("Bienvenue à Alabasta");
        Room krakenland = new Room("Bienvenue à Krakenland");
        Room amazone_lily = new Room("Bienvenue à Amazone_lily");
        Room skypia = new Room("Bienvenue à Skypia");
        Room paris8 = new Room("Bienvenue à Paris8");
        Room rafel = new Room("Bienvenue à Rafel, ~votre log pose n'arrête pas de s'agiter ...~");
        
        // initialise room exits
        cocoyashi.setExits(nooberland, null, null, null, null, null, null, null);
        nooberland.setExits(null, water7, cocoyashi, wano_kuni, kalen, alabasta, null, null);
        wano_kuni.setExits(null, nooberland, null, null, null, null, null, null);
        water7.setExits(null, null, null, nooberland, null, null, null, null);
        kalen.setExits(skypia, null, null, null, null, null, null, nooberland);
        ortopia.setExits(krakenland, null, null, kalen, null, amazone_lily, null, null);
        alabasta.setExits(null, null, null, null, null, null, nooberland, null);
        krakenland.setExits(null, null, ortopia, skypia, null, null, null, null);
        amazone_lily.setExits(null, null, null, null, null, null, ortopia, null);
        skypia.setExits(paris8, krakenland, kalen, null, null, rafel, null, null);
        paris8.setExits(null, null, skypia, null, null, null, null, null);
        rafel.setExits(null, null, null, null, null, null, skypia, null);

        currentRoom = cocoyashi;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }
        if(direction.equals("northwest")) {
            nextRoom = currentRoom.northWestExit;
        }
        if(direction.equals("northeast")) {
            nextRoom = currentRoom.northEastExit;
        }
        if(direction.equals("southwest")) {
            nextRoom = currentRoom.southWestExit;
        }
        if(direction.equals("southeast")) {
            nextRoom = currentRoom.southEastExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }
    
    private void printLocationInfo () 
    {
    	System.out.println(currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.northExit != null)
            System.out.print("north ");
        if(currentRoom.eastExit != null)
            System.out.print("east ");
        if(currentRoom.southExit != null)
            System.out.print("south ");
        if(currentRoom.westExit != null)
            System.out.print("west ");
        if(currentRoom.northWestExit != null)
            System.out.print("northwest ");
        if(currentRoom.northEastExit != null)
            System.out.print("northeast ");
        if(currentRoom.southWestExit != null)
            System.out.print("southwest ");
        if(currentRoom.southEastExit != null)
            System.out.print("southeast ");
        System.out.println();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game newGame = new Game();
		newGame.play();
	}
}
