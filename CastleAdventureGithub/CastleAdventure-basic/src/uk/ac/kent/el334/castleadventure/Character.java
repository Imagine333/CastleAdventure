package uk.ac.kent.el334.castleadventure;

import java.util.ArrayList;

public class Character {
    protected Room currentRoom;
    protected String type;


    public Character() {}

    /**
     * Getter for current room - @return Current room of the player
     */

    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Setter for the current room.@param currentRoom Room object where the player has moved into.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    public void dropItem(String item) {
        Item dropItem;
    }

    }



