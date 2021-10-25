public class Player {
    private String name;
    private int currentHealth;

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


}
