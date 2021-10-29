import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextAdventureGame {

    int row;
    int col;

    boolean isWellFull = true;
    boolean isGardenTreeExisting = true;

    boolean isDoorInDirection;

    Room[][] map;
    Room garden;

    Player player;

    Item axe;

    Scanner input = new Scanner(System.in);

    public static void save(int row, int col) {
        File file = new File("./save/saved_game.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            String position = String.format("%d, %d", row, col);
            fileWriter.write(position);
            fileWriter.close();
            System.out.println("The game is saved");
        } catch (IOException e) {
            System.out.println("Could not save the game");
        }
    }

    public static String load() {
        File file = new File("./save/saved_game.txt");
        try {
            Scanner fileScanner = new Scanner(file);
            String position = fileScanner.nextLine();
            fileScanner.close();
            return position;
        } catch (FileNotFoundException e) {
            System.out.println("Could not load a saved game");
        }
        return null;
    }

    public void updatePlayerPosition(String direction) {
        System.out.println("\n/////////////////////////////////////////////////////////\n");
        // Kolla efter riktning
        isDoorInDirection = true;
        if(direction.equalsIgnoreCase("north")) {
            if(player.getCurrentRoom().getNorthDoor()) {
                row--;
                // Kontrollera så vi inte hamnar utanför kartan
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
        // 2. Läs in kommando från användaren
        System.out.print("> ");
        String command = input.nextLine();

        // 3. Dela upp kommandot i delar, varje ord blir en sträng i en array
        //    Vi delar upp det inmatade värdet vid varje mellanslag
        String[] commandParts = command.split(" ");
        return commandParts;
    }

    public void specialRoomEvent(){
        if(isGardenTreeExisting && player.getCurrentRoom().getName() == "Garden" && player.inventory.contains(axe)){
            System.out.println("Do you want to use your Axe?\nYes or No");
            String[] commandParts = readUserInput();
            String command = commandParts[0];
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

        if(isWellFull && player.getCurrentRoom().getName() == "Forest"){
            System.out.println("There is a well with fresh water, do you want a drink?\nYes or no");
            String[] commandParts = readUserInput();
            String command = commandParts[0];
            if(command.equalsIgnoreCase("yes")){
                System.out.println("You drink from the well and heal 15 points");
                player.addCurrentHealth(15);
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
        // Initialisering

        player = new Player("Player1", 19);

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

        // Creating a dagger and adding it to the room theEntrance.
        Item dagger = new Item("Dagger", "A small but very deadly dagger.");
        theEntrance.setItem(dagger);

        axe = new Item("Axe", "A hatchet used for cutting wood");
        cellar.setItem(axe);

        // Creating a chest with three items and places it in the hall on the map.
        Chest chest = new Chest("Chest", "A large chest containing other items");
        Item shield = new Item("Shield", "A massive shield that works as a wall");
        Item potion = new Item("Health potion", "A potion that restores your health");
        Item sword = new Item("Sword", "A very sharp and mighty sword left behind by Conan the Barbarian");
        chest.addItemsToChest(shield);
        chest.addItemsToChest(potion);
        chest.addItemsToChest(sword);
        aHall.setItem(chest);

        map = new Room[][]{
                {pinkRoom, secretRoom, arena},
                {theEntrance, aDarkCave, pit},
                {aHall, garden, cellar},
                {emptySpace1, forest, emptySpace2}};
        row = 1;
        col = 0;
    }

    public void runGame(){
        System.out.println("\n\n***Welcome to the Text Adventure Game***\n");

        boolean running = true;
        boolean startRoom = true;

        // Här börjar spelloopen
        while(running) {
            // 1. Skriv ut i vilket rum vi är i
            player.setCurrentRoom(map[row][col]);
            System.out.println(player.getCurrentRoom().toString());
            specialRoomEvent();
           // if(startRoom){
           //     System.out.println(player.getCurrentRoom().toString());
           //     startRoom = false;
            //}

            player.getCurrentHealth();

            String[] commandParts = readUserInput();
            String command = commandParts[0];
            // 4. Kollar vilket "huvudkommando" som angivits
            //    Dessa är:
            //      - go
            //      - save
            //      - load
            //      - quit
            if(command.equalsIgnoreCase("go")) {
                // Kontrollera att man har skrivit något efter go, alltså en riktning
                if(commandParts.length == 2) {
                    updatePlayerPosition(commandParts[1]);
                    if(isDoorInDirection) {
                        System.out.println("Going " + commandParts[1]);
                    }
                }
                else {
                    System.out.println("You can't go without any direction");
                }
            }

            else if(command.equalsIgnoreCase("look")) {
                String itemDescription = player.getCurrentRoom().getItemDescription();
                System.out.println(itemDescription);
            }

            else if(command.equalsIgnoreCase("save")) {
                save(row, col);
            }

            else if(command.equalsIgnoreCase("load")) {
                LoadSaveGame();
            }

            else if(command.equalsIgnoreCase("quit")) {
                running = false;
            }

            else if(command.equalsIgnoreCase("loot")){
                if(player.getCurrentRoom().getItem() == null) {
                    System.out.println("There is no item in here!");
                }
                else{
                    System.out.println("You loot the " + player.getCurrentRoom().getItemType() + "!");
                    player.pickUpItem(player.getCurrentRoom().getItem());
                    player.getCurrentRoom().removeItem();
                }
            }

            else if(command.equalsIgnoreCase("inventory")){
                if(player.inventory.isEmpty()){
                    System.out.println("Your inventory is empty.");
                }
                else {
                    player.listInventory();
                }
            }

            if(player.getCurrentRoom().getTrap()){
                player.walkOnTrap();
            }

            if(player.healthNumber() < 1){
                player.playerDeath();
                running = false;
            }
        }
    }

    private void LoadSaveGame() {
        String position = load();
        if(position != null) {
            String[] pos = position.split(", ");
            int oldRow = row;
            int oldCol = col;
            row = Integer.parseInt(pos[0]);
            col = Integer.parseInt(pos[1]);
            if(row >= map.length) {
                System.out.println("Error reading row coordinates from file. Are you cheating?");
                row = oldRow;
                col = oldCol;
            }
            else {
                if(col >= map[row].length) {
                    System.out.println("Error reading row coordinates from file. Are you cheating?");
                    row = oldRow;
                    col = oldCol;
                }
            }
        }
    }

    public void quit() {
        System.out.println("***GAME OVER***");
    }
}
