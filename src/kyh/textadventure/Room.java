package kyh.textadventure;

public class Room {

    private String name;
    private String description;
    private Item item;
    private Monster monster;
    private boolean hasTrap;
    private boolean southDoor;
    private boolean westDoor;
    private boolean northDoor;
    private boolean eastDoor;

    public Room(String inName, String inDescription, boolean inHasTrap, boolean inSouthDoor, boolean inWestDoor, boolean inNorthDoor, boolean inEastDoor) {
        name = inName;
        description = inDescription;
        hasTrap = inHasTrap;
        southDoor = inSouthDoor;
        westDoor = inWestDoor;
        northDoor = inNorthDoor;
        eastDoor = inEastDoor;
    }

    public boolean getTrap () {
        return hasTrap;
    }

    public boolean getSouthDoor() {
        return southDoor;
    }

    public boolean getWestDoor() {
        return westDoor;
    }

    public boolean getNorthDoor() {
        return northDoor;
    }

    public boolean getEastDoor() {
        return eastDoor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setSouthDoor(){
        southDoor = true;
    }

    public String getItemDescription() {
        if(item != null) {
            return item.toString();
        } else {
            return "No item found";
        }
    }

    public Monster getMonster() {
        return monster;
    }
    public void setMonster(Monster inMonster){
        monster = inMonster;
    }

    public Item getItem(){
        return item;
    }

    public void removeItem() {
        item = null;
    }

    public String getItemType() {
        return item.type;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public void setItem(Item inItem) {
        item = inItem;
    }

    @Override
    public String toString() {
        String roomString = "\nCurrent position: " + getName() + "\n\n";                           // roomstring: "A hall"
        roomString = roomString + getDescription() + "\n";              // roomstring: "A hall" + "this is a description of a hall.
        roomString = roomString + "\tItems in here:\n\t---------------\n";             // roomstring: "A hall" + "this is a ..." + "Items..."

        // We need ot make sure that there is an item in the room before we try to call
        // a function in that item.
        if(item != null) {
            roomString = roomString + "\t" +item.getType();
        }

        return roomString;
    }
}