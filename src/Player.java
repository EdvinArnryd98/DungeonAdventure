import java.util.ArrayList;

public class Player {
    private String name;
    private int currentHealth;
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

    public void playerDeath() {
        System.out.println("Your Health got too low.\nYou don't feel so good...");
    }

    public void listInventory() {
        System.out.println("Your inventory:");
        for (int i = 0; i < inventory.size(); i++){
            System.out.println(inventory.get(i).type);
        }
    }

    public void pickUpItem(Item item) {
        inventory.add(item);
    }


}
