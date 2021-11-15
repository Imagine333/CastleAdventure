package uk.ac.kent.el334.castleadventure;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


/**
 * General advise:
 * <p>
 * The purpose of this class is to setup and run the game. The method stubs available contain hints on what code
 * you need to generate for each one of them.
 * <p>
 * The class should be kept lean. Most of the behaviour of the game should be handled by the other classes.
 */
public class Game<rooms> {
    private Player player;
    private Guard guard;
    private GateKeeper gatekeeper;
    public ArrayList<Room> rooms;


    /**
     * Strings that contain the text the user has typed. The strings receive values through the method "readInput".
     * The game supports commands with upto 2 extra words.
     * Example commands:
     * "help", "quit" (only firstWord used)
     * "go north", "go east", (using firstWord and secondWord)
     * etc.
     */
    private String firstWord;
    private String secondWord;
    private String thirdWord;


    public Game() {
        player = new Player();
        guard = new Guard();
        gatekeeper = new GateKeeper();
        rooms = new ArrayList<>();
    }

    /**
     * Main method that runs the game. Contains a continuous loop that receives user input and performs
     * the actions for the player.
     */

    public void run() {
        boolean running = true;
        printWelcome();
        setupGame();
        Room currentRoom = player.getCurrentRoom();
        currentRoom.printRoomDescription(currentRoom);


        while (running) {
            System.out.println();
            running = checkGameStatus(running);

            if (running == false){
                break;
            }else{
            readInput();
            }

            /** interprets what you have written and runs correct methods */


            switch (firstWord) {
                case "quit":
                    running = quit(true);
                    break;
                case "help":
                    printHelp();
                    break;
                case "go":
                    if (player.moveTo(secondWord)) {
                        currentRoom = player.getCurrentRoom();
                        currentRoom.printRoomDescription(currentRoom);
                        if (currentRoom == guard.getCurrentRoom()){
                            guard.printIntroduction();
                        }
                        if(currentRoom == gatekeeper.getCurrentRoom()){
                            if((player.getFriendStatus()) && (!gatekeeper.getBribed())){
                                gatekeeper.setAsleep(false);
                            }
                            gatekeeper.printIntroduction();}
                        break;
                    }
                case "look":
                    currentRoom.printRoomDescription(currentRoom);
                    break;
                case "inv", "inventory":
                    player.printInv();
                    break;
                case "drop":
                    if (thirdWord.equals("")) {
                        player.dropItem(secondWord);
                        currentRoom.printRoomDescription(currentRoom);
                    }
                    break;
                case "take":
                    if (thirdWord.equals("")) {
                        player.takeItem(secondWord);
                        currentRoom.printRoomDescription(currentRoom);
                    }
                    break;
                case "use":
                    if (thirdWord.equals("")) {
                       player.useSingleItem(secondWord,rooms, gatekeeper);
                    }else {
                        player.useTwoItems(secondWord, thirdWord, rooms, guard);
                    }
                    currentRoom.printRoomDescription(currentRoom);
                    break;
                default:
                    System.out.println("Unknown command. Try \"help\" to see a list of commands");
            }



        }

    }

    public boolean quit(boolean running) {
        /** Check if you wanted to quit */


        System.out.println(" Are you sure you want to quit? You will lose all progress.");
        readInput();
        System.out.println(firstWord);

        switch (firstWord) {
            case "yes", "y":
                System.out.println(" Quitting. Hope to see you again. Bye Bye");
                running = false;
                break;
            case "no", "n":
                System.out.println(" Great! Please continue.");
                running = true;
                break;
            default:
                System.out.println(" Sorry I didn't quite catch that. Please try again");
                quit(running);
        }

        return running;
    }


    private void setupGame() {
        /**Setting up the rooms and descriptions, as well as adding exits and items to specific rooms*/

        Room bridge = new Room("BRIDGE", "You are on a cobbled bridge outside the Castle. The deep waters of the moat are below you and to the north there is the Gatehouse.");
        Room gateHouse = new Room("GATEHOUSE", "You find yourself in the Gatehouse with the heavy metal gate in front of you. Behind you is the bridge but north on the other side of the gate is the courtyard.");
        Room courtyard = new Room("COURTYARD", "The courtyard is square dotted with heavy barrels and empty baskets. You keep to the shadows as you look around. To the North there is a large stone staircase and you can see a opening to the East and West.");
        Room topOfStaircase = new Room("STAIRCASE", "You are at the top of the large staircase. In front of you is a large wooden door, but to the west is a smaller door.");
        Room eastCorridor = new Room("EAST CORRIDOR", "You are in a long narrow corridor. It is empty, but to the North and South you can see a spiral staircase leading upwards. An opening leads West.");
        Room westCorridor = new Room("WEST CORRIDOR", "You are in a long narrow corridor. It is empty, but to the North and South you can see a spiral staircase leading upwards. An opening leads East.");
        Room towerOfMight = new Room("TOWER OF MIGHT", "The circular room at the top of the Tower has no notable exits other than retracing your steps.");
        Room towerOfKnowledge = new Room("TOWER OF KNOWLEDGE", "The circular room at the top of the Tower has no notable exits other than retracing your steps.");
        Room towerOfHealing = new Room("TOWER OF HEALING", "The circular room at the top of the Tower has no notable exits other than retracing your steps.");
        Room towerOfAncestry = new Room("TOWER OF ANCESTRY", "The room at the top of the Tower is pitch black. You cannot see anything.");
        Room grandHall = new Room("GRAND HALL", "The room is a wonder, decorated to the brim with tapestries and paintings. There is another exit to the West as well as the one behind you.");
        Room antechamber = new Room("ANTECHAMBER", "This small room is clearly being used as a storage room.There is a small door to the North and an even smaller door to the East.");
        Room kitchen = new Room("KITCHEN", "At the bottom of the steps behind the access door is the kitchen. It's a complete mess, with leftover crockery and food everywhere. To the north is the dungeons.");
        Room dungeon = new Room("DUNGEON", "Finally you are at the dingy dungeon. There is the cell to the North but it is locked, and a smuggler's passageway to the West");
        Room cell = new Room("CELL", "Congratulations. You have successfully reunited with your friend. Now you just need to escape from the castle without being caught.");
        Room passageway = new Room("PASSAGEWAY", "A very long passageway westward leading into the distance. There is a source of light at the end.");
        Room forest = new Room("FOREST", "You are in the forest.");

        rooms.add(kitchen);
        rooms.add(bridge);
        rooms.add(gateHouse);
        rooms.add(cell);
        rooms.add(courtyard);
        rooms.add(dungeon);
        rooms.add(forest);
        rooms.add(topOfStaircase);
        rooms.add(towerOfMight);
        rooms.add(towerOfKnowledge);
        rooms.add(towerOfAncestry);
        rooms.add(towerOfHealing);
        rooms.add(passageway);
        rooms.add(antechamber);
        rooms.add(grandHall);
        rooms.add(westCorridor);
        rooms.add(eastCorridor);


        bridge.addExit("north", gateHouse);
        gateHouse.addExit("north", courtyard);
        gateHouse.addExit("south", bridge);
        courtyard.addExit("north", topOfStaircase);
        courtyard.addExit("south", gateHouse);
        courtyard.addExit("east", eastCorridor);
        courtyard.addExit("west", westCorridor);
        westCorridor.addExit("east", courtyard);
        westCorridor.addExit("north", towerOfKnowledge);
        westCorridor.addExit("south", towerOfAncestry);
        eastCorridor.addExit("west", courtyard);
        eastCorridor.addExit("north", towerOfMight);
        eastCorridor.addExit("south", towerOfHealing);
        towerOfAncestry.addExit("north", westCorridor);
        towerOfHealing.addExit("north", eastCorridor);
        towerOfKnowledge.addExit("south", westCorridor);
        towerOfMight.addExit("south", eastCorridor);
        topOfStaircase.addExit("south", courtyard);
        topOfStaircase.addExit("north", grandHall);
        topOfStaircase.addExit("west", antechamber);
        antechamber.addExit("north", grandHall);
        antechamber.addExit("east", topOfStaircase);
        grandHall.addExit("south", topOfStaircase);
        grandHall.addExit("west", antechamber);
        kitchen.addExit("west", towerOfAncestry);
        kitchen.addExit("north", dungeon);
        dungeon.addExit("south", kitchen);
        dungeon.addExit("west", passageway);
        cell.addExit("south", dungeon);
        passageway.addExit("east", dungeon);
        passageway.addExit("west", forest);


        Item candle = new Item("candle", "A tall, unlit candle.");
        Item flintsteel = new Item("flintsteel", "Flint produces a spark when struck with steel.");
        Item stones = new Item("stones", "Small grey stone, broken off from the ground.");
        Item coins = new Item("coins", "A handful of gold coins.");
        Item tankard = new Item("tankard", " A large, cylindrical, drinking cup with a single handle, made of metal and dented badly. It is full of water. ");
        Item book = new Item("book", "A worn old thick book. A page is dog-eared in the middle.");
        Item sleepingPotion = new Item("potion", "A small vial of a sleeping potion.");
        Item apple = new Item("apple", "Red apple that's not looking the best of days.");
        Item key = new Item("key", "The key to the cell door.");

        towerOfMight.addItem(candle);
        antechamber.addItem(flintsteel);
        courtyard.addItem(stones);
        towerOfHealing.addItem(coins);
        kitchen.addItem(tankard);
        towerOfHealing.addItem(sleepingPotion);
        towerOfKnowledge.addItem(book);
        kitchen.addItem(apple);

        /**setting starting rooms and items*/
        player.setCurrentRoom(bridge);
        guard.setCurrentRoom(dungeon);
        guard.addItem(key);
        gatekeeper.setCurrentRoom(gateHouse);




    }


    /**
     * Prints a help message.
     */
    private void printHelp() {
        System.out.println("Commands Available:");
        System.out.println("help: You don't need this one explaining. =P");
        System.out.println("go <direction>: You travel through the exit in your entered direction.");
        System.out.println("look: get description of current room.");
        System.out.println("take <item> : Store item in your bag.");
        System.out.println("inv or inventory : see the list of items you currently hold in bag.");
        System.out.println("drop <item> : remove item from bag you no longer need or make room for other item.");
        System.out.println("use <obj> : try to use the specific object you have, in the room you are in.");
        System.out.println("use <obj1> <obj2>: try to use the specific object you have, on another object you have.");
        System.out.println("quit : end the game.");
    }

    /**
     * Prints a welcome message.
     */
    private void printWelcome() {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(" WELCOME TO CASTLE ADVENTURES!");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(" Your friend has been captured and is imprisoned in the cells in the castle dungeon. \n Navigate the rooms, collect items to use to find and rescue you friend.\n Type help for a list of commands.");
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private Boolean checkGameStatus(Boolean running){
        if((player.currentRoom.getName(player.getCurrentRoom()).equals("FOREST") || player.currentRoom.getName(player.getCurrentRoom()).equals("BRIDGE")) && player.getFriendStatus()){
            System.out.println("CONGRATULATIONS! YOU HAVE SUCCESSFULLY RESCUED YOUR FRIEND AND COMPLETED THE GAME");
            running = false;
        }else if(player.currentRoom.getName(player.getCurrentRoom()).equals("FOREST") && !player.getFriendStatus()){
            System.out.println("UNFORTUNATELY YOU HAVE LEFT THE CASTLE WITHOUT RESCUING YOUR FRIEND. GAME OVER!");
            System.out.println("TRY PLAY AGAIN ANOTHER DAY.");
            running = true;
        }
        return running;
    }

    /**
     * The method receives a single line from the user. It then breaks it into words.
     * The method can split the input into 3 words which are stored in the instance variables: firstWord,
     * secondWord, thirdWord.
     */
    private void readInput() {

        // Clean up words from previous read
        firstWord = "";
        secondWord = "";
        thirdWord = "";

        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String line = "";
        // Read a non blank line
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if (!line.isBlank())
                break;
        }

        // Break into words
        Scanner tokenizer = new Scanner(line);
        tokenizer.useDelimiter("[ \n\r\t]+");       // Use any spacing character to split words
        tokenizer.tokens();

        if (tokenizer.hasNext()) {
            firstWord = tokenizer.next().toLowerCase();
        }
        if (tokenizer.hasNext()) {
            secondWord = tokenizer.next().toLowerCase();
        }
        if (tokenizer.hasNext()) {
            thirdWord = tokenizer.next().toLowerCase();
        }

    }


}
