package uk.ac.kent.el334.castleadventure;

public class Item {
    String itemName;
    String itemDescription;

    public Item(String itemName, String description) {
        this.itemName = itemName;
        this.itemDescription = description; }


    public String getItemName() {
        return itemName;
    }


    public void printItemDescription(Item item){
        System.out.println(item.itemDescription);
    }





}
