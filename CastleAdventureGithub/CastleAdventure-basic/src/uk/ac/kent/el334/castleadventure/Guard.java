package uk.ac.kent.el334.castleadventure;

import java.util.ArrayList;

public class Guard extends Character{
    private Boolean asleep;
    private ArrayList<Item> belt;

    public Guard() {
        belt = new ArrayList<Item>();
        asleep = false;
        super.type = "guard";
    }

    public void addItem(Item item){
       belt.add(item);
    }

    public Boolean getAsleep() {
        return asleep;
    }

    public void setAsleep(Boolean asleep) {
        this.asleep = asleep;
    }

    public void printIntroduction(){
        if (getAsleep() == false){
        System.out.println();
        System.out.println(" A guard is standing just through the entrance, on his belt you can see the keys to the cell. \n He notices your entrance and straightens up cautiously.");
        System.out.println(" Guard: \"State your purpose for seeking entrance to the dungeons\"");
        System.out.println(" a: Leave via an exit");
        System.out.println(" b: Try to take key");
        System.out.println(" c: Use item(s) from your bag.");
        //System.out.println(" Type command i.e 'go south'");
        }else{
        System.out.println();
        System.out.println(" The guard is still asleep in the entrance.");
        }
    }

    /** Overwrites main dropItem method for guard's belt */
    public void dropItem(String item) {

        Item itemToDrop = checkBelt(item);

        if (itemToDrop != null) {
            currentRoom.addItem( itemToDrop);
            belt.remove(itemToDrop);
            for (Item di : belt) {
                if (item.equals(di.itemName)) {
                    currentRoom.addItem(di);
                }
            }
            System.out.println(" Allowing " +  itemToDrop.itemName + " to be collected.");
            System.out.println();
        } else {
            System.out.println("Drop failed.");
        }}

    public Item checkBelt(String item){
        Item itemCheck = null;

        for (Item ci : belt) {
            if (item.equals(ci .itemName)) {
                itemCheck  = ci;
                break;
            } else {
                itemCheck = null;
            }
        }

        return itemCheck;
    }


}
