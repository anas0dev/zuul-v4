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
        Room cocoyashi = new Room("Cocoyashi");
        Room nooberland = new Room("Nooberland");
        Room wano_kuni = new Room("Wano_kuni");
        Room water7 = new Room("Water7");
        Room kalen = new Room("Kalen");
        Room ortopia = new Room("Ortopia");
        Room alabasta = new Room("Alabasta");
        Room krakenland = new Room("Krakenland");
        Room amazone_lily = new Room("Amazone_lily");
        Room skypia = new Room("Skypia");
        Room paris8 = new Room("Paris8, il semble que vous avez découvert une île absente sur votre carte, et si vous l'exploriez ?");
        Room rafel = new Room("Rafel, ~votre log pose n'arrête pas de s'agiter ...~");
        
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
        System.out.println("Merci d'avoir joué, à très vite !");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenue dans One Piece treasure");
        System.out.println("Imergez vous dans une odyssée de pirate purement ludique et palpitante");
        System.out.println("Le but du jeu est de parcourir toute la mer afin de trouver le One Piece et devenir le roi des pirates !");
        System.out.println("Les îles connues à ce jour sont les suivantes : \n"
        		+ "Alabasta, Amazone Lily, Cocoyashi, Kalen, Krakenland, Nooberland, Ortopia, Raftel, Skypia, Water7, Wano-Kuni");
        System.out.println("Tapez 'help' si vous avez besoin d'aide.");
        System.out.println();
        System.out.print("Bienvenue à " + currentRoom.getExitString());
        System.out.println();
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
        else if (commandWord.equals("look"))
            look();
        else if (commandWord.equals("eat"))
            eat();
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
        System.out.println("Vous êtes perdus sur cette vague mer");
        //System.out.println("around at the university.");
        System.out.println();
        System.out.println("Vos mots de commande sont :");
        System.out.println("   go quit eat look help");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Où souhaitez-vous aller?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExit("north");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExit("east");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExit("south");
        }
        if(direction.equals("west")) {
        	nextRoom = currentRoom.getExit("west");
        }
        if(direction.equals("northwest")) {
            nextRoom = currentRoom.getExit("northwest");
        }
        if(direction.equals("northeast")) {
            nextRoom = currentRoom.getExit("northeast");
        }
        if(direction.equals("southwest")) {
            nextRoom = currentRoom.getExit("southwest");
        }
        if(direction.equals("southeast")) {
            nextRoom = currentRoom.getExit("southeast");
        }

        if (nextRoom == null) {
            System.out.println("Il ny a pas de sortie!");
        }
        else {
            currentRoom = nextRoom;
            System.out.print("Bienvenue à " + currentRoom.getExitString());
            System.out.println();	
        }
    }
    /**
     * Cette méthode permet de connaître sa position
     */
    private void look()
    {
        System.out.println("Vous êtes actuellement à " + currentRoom.getLongDescription());
    }
    
    /**
     * Cette méthode permet de manger
     */
     private void eat() 
    {
        System.out.println("Tu as mangé maintenant et tu n'as plus faim.");
        
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
