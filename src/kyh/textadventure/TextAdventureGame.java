package kyh.textadventure;

import java.util.Scanner;

public class TextAdventureGame {

    int row;
    int col;

    boolean playerAlive = true;
    boolean isWellFull = true;
    boolean isGardenTreeExisting = true;
    boolean isDoorInDirection;

    Room[][] map;
    Room garden;

    Player player;

    Item axe;

    Scanner input = new Scanner(System.in);

    public void updatePlayerPosition(String direction) {
        System.out.println("\n/////////////////////////////////////////////////////////\n");
        isDoorInDirection = true;
        if(direction.equalsIgnoreCase("north")) {
            if(player.getCurrentRoom().getNorthDoor()) {
                row--;
                if (row < 0) {
                    row = 0;
                }
            }
            else{
                isDoorInDirection = false;
                System.out.println("There is no door there!");
            }
        }
        else if(direction.equalsIgnoreCase("south")) {
            if(player.getCurrentRoom().getSouthDoor()) {
                row++;
                if (row >= map.length) {
                    row--;
                }
            }
            else{
                isDoorInDirection = false;
                System.out.println("There is no door there!");
            }
        }
        else if(direction.equalsIgnoreCase("east")) {
            if(player.getCurrentRoom().getEastDoor()) {
                col++;
                if (col >= map[row].length) {
                    col--;
                }
            }
            else{
                isDoorInDirection = false;
                System.out.println("There is no door there!");
            }
        }
        else if(direction.equalsIgnoreCase("west")) {
            if (player.getCurrentRoom().getWestDoor()) {
                col--;
                if (col < 0) {
                    col = 0;
                }
            }
            else{
                isDoorInDirection = false;
                System.out.println("There is no door there!");
            }
        }
    }

    private String[] readUserInput() {
        System.out.print("> ");
        String command = input.nextLine();

        String[] commandParts = command.split(" ");
        return commandParts;
    }

    public void specialRoomEvent(){
        if(isGardenTreeExisting && player.getCurrentRoom().getName().equals("Garden") && player.inventory.contains(axe)){
            System.out.println("Do you want to use your Axe?\nYes or No");
            String command = input.nextLine();
            if(command.equalsIgnoreCase("yes")){
                System.out.println("You chop down the tree in front of you");
                player.getCurrentRoom().setSouthDoor();
                player.inventory.remove(axe);
                garden.setDescription("You walk into the garden.");
                isGardenTreeExisting = false;
            }
            else if(command.equalsIgnoreCase("no")){
                System.out.println("You stand still and stare at the grass");
            }
            else{
                System.out.println("wrong command, try again!");

            }
        }

        if(isWellFull && player.getCurrentRoom().getName().equals("Forest")){
            System.out.println("There is a well with fresh water, do you want a drink?\nYes or no");
            String command = input.nextLine();
            if(command.equalsIgnoreCase("yes")){
                System.out.println("You drink from the well and heal 15 points");
                player.addCurrentHealth(20);
                isWellFull = false;
            }
            else if(command.equalsIgnoreCase("no")){
                System.out.println("You feel the cold breeze from the wind...");
            }
            else{
                System.out.println("wrong command, try again!");

            }
        }
    }

    public void initialization() {

        player = new Player(40);

        Room pinkRoom = new Room("Pink room", "This is a room with pink walls filled with pink furniture", false, true, false, false, true);
        Room aHall = new Room("A hall", "A large hallway with a fancy rug on the floor", false, false, false, true, true);
        Room arena = new Room("Arena", "This is the arena! Fight contestants and earn rewards.", false, false, true, false, false);

        Room theEntrance = new Room("The entrance", "A large entrance to the map.", false, true, false, true, false);
        Room aDarkCave = new Room("A dark cave", "A very dark cave without any lights, and it is close to pitch black.", false, true, false, true, true);
        Room pit = new Room("Pit", "Watch out! You fell into the pit", true, false, true, false, false);

        Room secretRoom = new Room("Secret Room", "There is a big room with a large door, but the door is locked.", false, true, true, false, true);
        garden = new Room("Garden", "You walk out in the garden. There is a tree blocking the way.", false, false, true, true, true);
        Room cellar = new Room("Cellar", "This room is cold, seems like nobody has been here in a while.", false, false, true, false, true);

        Room emptySpace1 = new Room("", "", false,false,false,false,false);
        Room emptySpace2 = new Room("", "", false,false,false,false,false);
        Room forest = new Room("Forest", "You are in a forest. You can hear the wind blowing in the tree tops.", false, false, false, true, false);

        Item dagger = new Item("Dagger", "A small but very deadly dagger.", 2);
        theEntrance.setItem(dagger);

        axe = new Item("Axe", "A hatchet used for cutting wood", 4);
        cellar.setItem(axe);

        Item sword = new Item("Sword", "A very sharp and mighty sword left behind by Conan the Barbarian", 10);
        secretRoom.setItem(sword);

        Monster troll = new Monster("Troll", "The troll looks angry", 8, 30);
        arena.setMonster(troll);
        map = new Room[][]{
                {pinkRoom, secretRoom, arena},
                {theEntrance, aDarkCave, pit},
                {aHall, garden, cellar},
                {emptySpace1, forest, emptySpace2}};
        row = 1;
        col = 0;
    }

    public void enterBattle(){
        System.out.println("You have stumbled upon a " + player.getCurrentRoom().getMonster().getName() + ", it is time to fight!");
        boolean monsterAlive = true;
        boolean isPlayerFighting = true;
        while(playerAlive && monsterAlive && isPlayerFighting){
            System.out.println("What do you do?");
            System.out.println("1. Attack");
            System.out.println("2. Drink potion");
            System.out.println("3. Run!!!");
            String command = input.nextLine();
            if(command.equals("1")){
                if(player.hand.isEmpty()){
                    System.out.println("You tried to attack without an item equipped!!!");
                }
                else if(player.hand.size() > 0) {
                    System.out.println("You attack the " + player.getCurrentRoom().getMonster().getName());
                    System.out.println("You swing your " + player.hand.get(0).type + " to deal " + player.hand.get(0).damage + " damage!");
                    player.getCurrentRoom().getMonster().setHealth(player.getCurrentRoom().getMonster().getHealth() - player.hand.get(0).damage);
                    if (player.getCurrentRoom().getMonster().getHealth() < 1) {
                        monsterAlive = false;
                    }
                }
            }
            else if(command.equals("2")){
                player.usePotion();
            }
            else if(command.equals("3")){
                row = 1;
                col = 0;
                player.setCurrentRoom(map[row][col]);
                System.out.println("You ran back to the entrance and lost 15 health");
                player.setHealthNumber(player.getHealthNumber() - 15);
                System.out.println(player.getCurrentRoom().toString());
                isPlayerFighting = false;
            }
            else{
                System.out.println("Command was unacceptable, try again");
            }
            if(isPlayerFighting) {
                if (monsterAlive) {
                    System.out.println("The " + player.getCurrentRoom().getMonster().getName() + "'s health: " + player.getCurrentRoom().getMonster().getHealth());
                    System.out.println("The " + player.getCurrentRoom().getMonster().getName() + " strikes back and deals " + player.getCurrentRoom().getMonster().getDamage() + " damage to you!");
                    player.setHealthNumber(player.getHealthNumber() - player.getCurrentRoom().getMonster().getDamage());
                } else {
                    System.out.println("Congratulations! You have slain the " + player.getCurrentRoom().getMonster().getName() + "!");
                    player.getCurrentRoom().setMonster(null);
                }
                if (player.getHealthNumber() < 1) {
                    playerAlive = false;
                } else {
                    player.getCurrentHealth();
                }
                if (!monsterAlive) {
                    System.out.println("You cleared the room. Where do you want to go next?");
                }
            }
        }
    }

    public void runGame(){
        System.out.println("\n\n***Welcome to the Text Adventure Game***\n");

        boolean running = true;

        while(running) {
            player.setCurrentRoom(map[row][col]);
            System.out.println(player.getCurrentRoom().toString());

            if(player.getCurrentRoom().getTrap()){
                player.walkOnTrap();
            }
            if(player.getHealthNumber() < 1){
                player.playerDeath();
                playerAlive = false;
                running = false;
            }
            if(playerAlive) {
                player.getCurrentHealth();

                specialRoomEvent();
                if(player.getCurrentRoom().getMonster() != null){
                    enterBattle();
                }
                    if(playerAlive) {
                        String[] commandParts = readUserInput();
                        String command = commandParts[0];
                        if (command.equalsIgnoreCase("go")) {
                            if (commandParts.length == 2) {
                                updatePlayerPosition(commandParts[1]);
                                if (isDoorInDirection) {
                                    System.out.println("Going " + commandParts[1]);
                                }
                            } else {
                                System.out.println("You can't go without any direction");
                            }
                        } else if (command.equalsIgnoreCase("look")) {
                            String itemDescription = player.getCurrentRoom().getItemDescription();
                            System.out.println(itemDescription);
                        }  else if (command.equalsIgnoreCase("quit")) {
                            running = false;
                        } else if (command.equalsIgnoreCase("loot")) {
                            if (player.getCurrentRoom().getItem() == null) {
                                System.out.println("There is no item in here!");
                            } else if (player.inventory.size() < player.getInventoryMaxSize()) {
                                System.out.println("You loot the " + player.getCurrentRoom().getItemType() + "!");
                                player.pickUpItem(player.getCurrentRoom().getItem());
                                player.getCurrentRoom().removeItem();
                            } else {
                                System.out.println("Your inventory is full!");
                            }
                        } else if (command.equalsIgnoreCase("inventory")) {
                            player.listInventory();
                            player.potionCountTostring();
                        } else if (command.equalsIgnoreCase("equip")) {
                            if (commandParts.length == 2) {
                                if (!player.inventory.isEmpty()) {
                                    if (player.hand.isEmpty()) {
                                        System.out.println("You equip the " + player.inventory.get(Integer.parseInt(commandParts[1])).type);
                                        player.equipItem(player.inventory.get(Integer.parseInt(commandParts[1])));
                                        player.inventory.remove(Integer.parseInt(commandParts[1]));
                                    } else {
                                        System.out.println("You already have an item equipped");
                                    }
                                } else if (player.inventory.isEmpty()) {
                                    System.out.println("Your inventory is empty");
                                }
                            } else {
                                System.out.println("You need to input an index to equip an item");
                            }
                        } else if (command.equalsIgnoreCase("unequip")) {
                            if (player.hand.isEmpty()) {
                                System.out.println("You have no item equipped");
                            } else {
                                System.out.println("You unequipped " + player.hand.get(0).type);
                                player.inventory.add(player.hand.get(0));
                                player.hand.remove(0);
                            }
                        } else if (command.equalsIgnoreCase("drop")) {
                            if (player.getCurrentRoom().getItem() == null) {
                                if (commandParts.length == 2) {
                                    player.dropItem(Integer.parseInt(commandParts[1]));
                                } else {
                                    System.out.println("You need to input an index to drop an item");
                                }
                            } else {
                                System.out.println("There is already an item in here.");
                            }
                        }

                    }
            }

        }
    }

    public void quit() {
        System.out.println("***GAME OVER***");
    }
}
