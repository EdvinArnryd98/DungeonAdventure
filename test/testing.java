import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import kyh.textadventure.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class testing {
    @Test
    public void testCreatePlayerHealth(){
        Player player = new Player(20);
        assertEquals(20, player.getHealthNumber());
    }

    @Test
    public void testPlayerAddHealth(){
        Player player = new Player( 20);
        player.addCurrentHealth(10);
        assertEquals(30, player.getHealthNumber());
    }

    @Test
    public void testPlayerWalkedInTrap(){
        Player player = new Player(20);
        player.walkOnTrap();
        assertEquals(10, player.getHealthNumber());
    }

    @Test
    public void testItemType(){
        Item item = new Item("Rock", "This is a small rock");
        assertEquals("Rock", item.getType());
    }

    @Test
    public void testPlayerUsePotion(){
        Player player = new Player(20);
        player.usePotion();
        assertEquals(2, player.getPotionCount());
    }

    @Test
    public void testAddPlayerPotion(){
        Player player = new Player( 20);
        player.addPotionCount();
        assertEquals(4, player.getPotionCount());
    }
}
