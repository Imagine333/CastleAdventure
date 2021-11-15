package uk.ac.kent.el334.castleadventure;

import java.util.ArrayList;

/**
 * General advise:
 * <p>
 * The Player object is the main object that interacts with the game. You will need to introduce methods that perform
 * the different actions that the player wants to do. The stub method "moveTo" is an example.
 * <p>
 * The variable currentRoom indicates where the player is. When the player moves, that variable should change.
 * <p>
 * If you want to introduce items, you may need to have a way of holding multiple item objects for the player, as their inventory.
 */
public class Player extends Character{
    private Boolean usedCandle = false;;
    private ArrayList<Item> bag;
    private boolean gotFriend;

    public Player() {
        currentRoom = null;
        bag = new ArrayList<Item>();
        super.type = "player";
        gotFriend = false;

    }

    public Boolean getFriendStatus(){
        return gotFriend;
    }

    public void setFriendStatus(Boolean gotfriend) {this.gotFriend = gotfriend; }


    /** Stating what happens when you try to use specific item, depending on what room you are in. Some items affect other characters.*/
    public void useSingleItem(String item1, ArrayList<Room> rooms, GateKeeper gatekeeper) {
            if (checkBag(item1) != null){
            System.out.println(" Using " + item1);

            switch (item1) {
                case "candle":
                    System.out.println(" You cannot use a candle without using a something to light it.");
                    break;
                case "flintsteel":
                    System.out.println(" You cannot use flintsteel without using something to light.");
                    break;
                case "stones":
                    System.out.println(" You throw the stones into the distance and hear a clatter as they hit the ground. Nothing happens.");
                    dropItem("stones");
                    break;
                case "apple":
                    System.out.println(" You take a bite from the apple but it is sour. You drop it in disgust.");
                    dropItem("apple");
                    break;
                case "tankard":
                    System.out.println(" You take a few sips of water from the cup to ease your thirst.");
                    break;
                case "coins":
                    if ((!gatekeeper.getAsleep()) && (currentRoom.getName(currentRoom) == "GATEHOUSE")){
                        for (Room room : rooms) {
                            if (room.getName(room).equals("BRIDGE")) {
                                currentRoom.addExit("south", room);
                                gatekeeper.setBribed(true);
                                System.out.println(" You bribe the GateKeeper into letting you through to the bridge.");
                                System.out.println(" The GateKeeper turns a blind eye and returns to sleep, allowing you to be able to lift the heavy gate.");
                                System.out.println();
                                bag.remove(checkBag(item1));
                                break;
                            }
                        }

                    }
                    else {
                        System.out.println("You have no-one you can bribe right now, you can save them till later.");
                    }
                    break;
                case "potion":
                    System.out.println(" The potion is too potent to use without dissolving it in water.");
                    break;
                case "book":
                    System.out.println(" You open to book to the dog-eared page. Inside is a riddle:");
                    System.out.println();
                    System.out.println(" One room here, another room there,");
                    System.out.println(" Maybe you are not aware,");
                    System.out.println(" That in a room at the top of a stair,");
                    System.out.println(" Is a hidden door for when items pair.");
                    System.out.println();
                    System.out.println(" Pass the kitchen, the dungeon beware,");
                    System.out.println(" Your friend does not lie in solitaire.");
                    System.out.println(" A single guard blocks the prison lair,");
                    System.out.println(" Another two items will need to pair.");
                    System.out.println();
                    break;
                case "key":
                    if (currentRoom.getName(currentRoom).equals("DUNGEON")){
                    for (Room room : rooms) {
                        if (room.getName(room).equals("CELL")) {
                            currentRoom.addExit("north", room);
                            System.out.println(" Unlocking the cell door");
                            currentRoom.addDescription(" The cell door to the north is now unlocked! The kitchen is to the south and a smuggler's passageway is to the West");
                            break;
                        }
                    }}else{
                        System.out.println(" You cannot use this item here.");
                    }

                    break;
                default:
                    System.out.println(" Invalid item name, try again");
            }
        }else{
                System.out.println(" You do not have this item to use");
            }

        }


    public void useTwoItems(String item1, String item2, ArrayList<Room> rooms, Guard guard) {

        if (checkBag(item1) != null && checkBag(item2) != null){
            while (!usedCandle) {
                if ((item1.equals("candle") && item2.equals("flintsteel") || item2.equals("candle") && item1.equals("flintsteel"))) {
                    if (currentRoom.getName(currentRoom) == "TOWER OF ANCESTRY") {
                        for (Room room : rooms) {
                            if (room.getName(room).equals("KITCHEN")) {
                                currentRoom.addExit("east", room);
                                usedCandle = true;
                                bag.remove(checkBag(item1));
                                bag.remove(checkBag(item2));
                                currentRoom.addDescription("Now there is light you can see you are in a circular room at the top of a tower. To the east you can now see a servant's access door.");
                                break;
                            }
                        }
                    } else {
                        System.out.println(" This room is already bright. You blow it out to reuse later.");
                    }
                } else {
                    System.out.println(" You cannot use these items together");
                }
            }

            while(!guard.getAsleep() && currentRoom.getName(currentRoom) == "DUNGEON"){
            if ((item1.equals("tankard") && item2.equals("potion") || item2.equals("tankard") && item1.equals("potion"))) {
                bag.remove(checkBag(item1));
                bag.remove(checkBag(item2));
                guard.dropItem("key");
                guard.setAsleep(true);
                System.out.println(" You present the guard with the drugged tankard of water. He drinks as you pretend you're leaving, but eventually he falls asleep. \n His keys are now accessible.");
                break;
            }else{System.out.println("You can't use these items together with no-one in the room");
            }
            }

        }else{
            System.out.println(" You do not have one or both of these items to use.");
        }
    }

    /**Takes item and adds to bag array*/

    public void takeItem(String item) {
        Item takenItem = currentRoom.getItem(item);
        if (takenItem != null) {
            bag.add(takenItem);
            System.out.println(" Adding " + takenItem.itemName + " to your bag.");
            System.out.println();
        } else {
            System.out.println(" Cannot take this object.");
        }
    }

    /** Overwrites main dropItem method for player's bag */
    public void dropItem(String item) {

        Item dropItem = checkBag(item);

        if (dropItem != null) {
            currentRoom.addItem(dropItem);
            bag.remove(dropItem);
            for (Item di : bag) {
                if (item.equals(di.itemName)) {
                    currentRoom.addItem(di);
                }
            }
            System.out.println(" Dropping " + dropItem.itemName + " into the room.");
            System.out.println();
        } else {
            System.out.println(" Cannot drop this object.");
        }
    }

    public void printInv() {
        System.out.println();
        System.out.println(" Below are your current items:");
        for (Item item : bag) {
            System.out.print(" * " + item.itemName + " - ");
            item.printItemDescription(item);
        }

    }

    /** Checks if item you want to use or drop is actually in bag */

    public Item checkBag(String item){
        Item itemCheck = null;

        for (Item ci : bag) {
            if (item.equals(ci .itemName)) {
                itemCheck  = ci;
                break;
            } else {
                itemCheck = null;
            }
        }

        return itemCheck;
    }

    public boolean moveTo(String direction) {

        /** returns what room is in a specific exit*/

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom != null) {

            System.out.println(" Exiting to: " + nextRoom.getName(nextRoom));

            /*if you are trying to go to specific room, some variables can get affected*/
            if(nextRoom.getName(nextRoom).equals("CELL")){
                setFriendStatus(true);
            }
            if ((nextRoom.getName(nextRoom).equals("GATEHOUSE")) && (gotFriend == true)){
                nextRoom.removeExit("south", nextRoom);
            }

            System.out.println();
            setCurrentRoom(nextRoom);
        } else {
            System.out.println(" No exit");
            return false;
        }


        return true;
    }

}
