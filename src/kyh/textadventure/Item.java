package kyh.textadventure;

public class Item {
    protected String type;
    protected String description;
    protected int damage;

    public Item(String inType, String inDescription) {
        type = inType;
        description = inDescription;
    }

    public Item(String inType, String inDescription, int inDamage) {
        type = inType;
        description = inDescription;
        damage = inDamage;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        String itemDescription = type;
        itemDescription = itemDescription + "\n";
        itemDescription = itemDescription + description;
        return itemDescription;
    }
}