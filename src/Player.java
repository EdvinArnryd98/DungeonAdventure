import java.util.ArrayList;

public class Player {
    private String name;
    private int currentHealth;
    private Room currentRoom;
    private int inventoryMaxSize = 4;
    private int potionCount = 3;
    private boolean hasBomb;
    ArrayList<Item> hand = new ArrayList<Item>();
    ArrayList<Item> inventory = new ArrayList<Item>();

    public Player(String inName, int inStartHealth){
        name = inName;
        currentHealth = inStartHealth;
    }

    public void usePotion(){
        if(potionCount > 0){
            System.out.println("You drink a health potion and gain 15 hp");
            currentHealth += 15;
            potionCount--;
        }
        else{
            System.out.println("You have no more potions left");
        }
    }

    public void potionCountTostring(){
        System.out.println("Potions left: " + potionCount);
    }

    public void walkOnTrap() {
        currentHealth -= 10;
    }

    public void getCurrentHealth() {
        System.out.println("Your HP: " + currentHealth);
    }

    public void setHealthNumber(int newHp) {
        currentHealth = newHp;
    }
    public int getHealthNumber(){
        return currentHealth;
    }

    public int addCurrentHealth(int health){
        return currentHealth += health;
    }

    public void playerDeath() {
        System.out.println("Your Health got too low.\nYou don't feel so good...");
    }

    public int getInventoryMaxSize(){
        return inventoryMaxSize;
    }

    public void listInventory() {
        if(!hand.isEmpty()) {
            System.out.println("Wielding: " + hand.get(0).type);
        }
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

    public void equipItem(Item item){
            hand.add(item);
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }


}
