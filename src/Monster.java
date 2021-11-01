public class Monster {
    private String name;
    private String description;
    private int damage;
    private int health;

    public Monster(String inName, String inDescription, int inDamage, int inHealth){
        name = inName;
        description = inDescription;
        damage = inDamage;
        health = inHealth;
    }

    public String getName(){
        return name;
    }

    public int getDamage(){
        return damage;
    }

    public int getHealth(){
        return health;
    }
}
