import java.util.ArrayList;

public class Player {
    private String name;
    private int currentHealth;
    Room currentRoom;
    int maxSize = 4;
    ArrayList<Item> inventory = new ArrayList<Item>();

    public Player(String inName, int inStartHealth){
        name = inName;
        currentHealth = inStartHealth;
    }

    public void walkOnTrap() {
        currentHealth -= 10;
    }

    public void findHealthPotion() {
        currentHealth += 8;
    }

    public void getCurrentHealth() {
        System.out.println("Your HP: " + currentHealth);
    }

    public int healthNumber(){
        return currentHealth;
    }

    public int addCurrentHealth(int health){
        return currentHealth += health;
    }

    public void playerDeath() {
        System.out.println("Your Health got too low.\nYou don't feel so good...");
    }

    public void listInventory() {
        System.out.println("Your inventory:");
        for (int i = 0; i < inventory.size(); i++){
            System.out.println(inventory.get(i).type + "(" + (i) + ")");
        }
    }

    public void dropItem(int itemIndex){
        if(inventory.isEmpty()) {
            System.out.println("You don't have any items to drop.");
        }
            else if (itemIndex < 0 || itemIndex > 3) {
                System.out.println("your index is out of bounds");
            } else {
                String droppedItem = inventory.get(itemIndex).getType();
                System.out.println("You dropped: " + droppedItem);
                getCurrentRoom().setItem(inventory.get(itemIndex));
                inventory.remove(itemIndex);
            }
    }

    public void pickUpItem(Item item) {
        inventory.add(item);
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }


}
