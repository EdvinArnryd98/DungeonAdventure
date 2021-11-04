package kyh.textadventure;

public interface iPlayer {

    void setHealthNumber(int newHp);
    int getHealthNumber();
    int addCurrentHealth(int health);
    Room getCurrentRoom();
    void playerDeath();
}
