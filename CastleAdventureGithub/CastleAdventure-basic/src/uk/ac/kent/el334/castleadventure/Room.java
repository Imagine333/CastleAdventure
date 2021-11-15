package uk.ac.kent.el334.castleadventure;

//import java.util.ArrayList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * General advise:
 * <p>
 * Each room should be able to produce a description text that is printed out when the player enters.
 * It should also be able to generate a text to list all the possible exits.
 * <p>
 * Each room can have other rooms linked with them for each direction. These need to be setup when the Game map is constructed.
 * If a Room variable is null, then there is no exit in that direction.
 * <p>
 * If you want to introduce items, each room should have a way of holding item objects, allowing the player to take them
 * (i.e. remove them from the room).
 */

public class Room {
    /* Set of room variables that represent the exits from this room */
    private Room north;
    private Room south;
    private Room east;
    private Room west;

    private String name;
    private String description;
    private ArrayList<Item> roomItems;
    private HashMap<String, Room> exits;



    public Room(String name, String description) {
        exits = new HashMap<String, Room>();
        roomItems = new ArrayList<Item>();
        this.name = name;
        this.description = description;
    }



    public void addExit(String direction, Room nextRoom) {
        switch (direction) {
            case "north":
                north = nextRoom;
                break;
            case "south":
                south = nextRoom;
                break;
            case "east":
                east = nextRoom;
                break;
            case "west":
                west = nextRoom;
                break;
            default:
                System.out.println("Cannot add this exit.");
                break;
        }
        exits.put(direction, nextRoom);

    }

    public void removeExit(String direction, Room nextRoom){
        exits.remove(direction);
    }


    public String getName(Room room) {
        return room.name;
    }


    public void addDescription(String description) {
        this.description = description;
    }

    public void addItem(Item item) {
        roomItems.add(item);
    }

    public void printRoomDescription(Room room) {
        System.out.println(" [" + room.name + "]: " + room.description);

        System.out.print(" Collectable Items: ");


        for (Item item : roomItems) {
            if (item != null) {
                System.out.print(item.itemName + " ");
            }

        }

        System.out.println();
        System.out.print(" Exits: ");

        for (String exit : exits.keySet()) {
            if (exit != null) {
                System.out.print(exit + " ");
            }
        }
        System.out.println("");

    }

    /**
     * Get the exit based on the direction as string
     *
     * @param direction Can be "north", "east", "south", "west"
     * @return Room in that direction
     */

    public Room getExit(String direction) {
        switch (direction) {
            case "north":
                return north;
            case "south":
                return south;
            case "east":
                return east;
            case "west":
                return west;
            default:
                return null;
        }
    }

    public Item getItem(String takenItem) {
        Item ti = null;
        for (Item item : roomItems) {
            if (takenItem.equals(item.itemName)) {
                roomItems.remove(item);
                ti = item;
                break;
            } else {
                ti = null;
            }
        }
        return ti;
    }


}