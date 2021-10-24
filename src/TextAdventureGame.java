import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextAdventureGame {

    int row;
    int col;

    int checkForDoors = 1;

    Room[][] map;

    Scanner input;

    Player player = new Player("Player1", 50);

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
        checkForDoors = 1;
        if(direction.equalsIgnoreCase("north")) {
            if(map[row][col].getNorthDoor()) {
                row--;
                // Kontrollera så vi inte hamnar utanför kartan
                if (row < 0) {
                    row = 0;
                }
            }
            else{
                checkForDoors = 2;
                System.out.println("There is no door there!");
            }
        }
        else if(direction.equalsIgnoreCase("south")) {
            if(map[row][col].getSouthDoor()) {
                row++;
                if (row >= map.length) {
                    row--;
                }
            }
            else{
                checkForDoors = 2;
                System.out.println("There is no door there!");
            }
        }
        else if(direction.equalsIgnoreCase("east")) {
            if(map[row][col].getEastDoor()) {
                col++;
                if (col >= map[row].length) {
                    col--;
                }
            }
            else{
                checkForDoors = 2;
                System.out.println("There is no door there!");
            }
        }
        else if(direction.equalsIgnoreCase("west")) {
            if (map[row][col].getWestDoor()) {
                col--;
                if (col < 0) {
                    col = 0;
                }
            }
            else{
                checkForDoors = 2;
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

    public void initialization() {
        // Initialisering
        input = new Scanner(System.in);

        Room pinkRoom = new Room("Pink room", "This is a room with pink walls filled with pink furniture", false, true, false, false, true);
        Room aHall = new Room("A hall", "A large hallway with a fancy rug on the floor", false, true, true, false, true);
        Room arena = new Room("Arena", "This is the arena! Fight contestants and earn rewards.", false, false, true, false, false);

        Room theEntrance = new Room("The entrance", "A large entrance to the map.", false, true, false, true, false);
        Room aDarkCave = new Room("A dark cave", "A very dark cave without any lights, and it is close to pitch black.", false, true, false, true, true);
        Room pit = new Room("Pit", "Watch out! You fell into the pit", true, false, true, false, false);

        Room secretRoom = new Room("Secret Room", "There is a big room with a large door, but the door is locked.", false, false, false, true, true);
        Room garden = new Room("Garden", "You walk out in the garden. There is a lot of flowers here.", false, false, true, true, true);
        Room cellar = new Room("Cellar", "This room is cold, seems like nobody has been here in a while.", false, false, true, true, true);

        // Creating a dagger and adding it to the room theEntrance.
        Item dagger = new Item("Dagger", "A small but very deadly dagger.");
        theEntrance.setItem(dagger);

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
                {pinkRoom, aHall, arena},
                {theEntrance, aDarkCave, pit},
                {secretRoom, garden, cellar}};
        row = 1;
        col = 0;
    }

    public void runGame() {
        System.out.println("\n\n***Welcome to the Text Adventure Game***\n");

        boolean running = true;

        // Här börjar spelloopen
        while(running) {
            // 1. Skriv ut i vilket rum vi är i
            System.out.println(map[row][col].toString());

            // Checks if player walked on a trap
            if(map[row][col].getTrap()){
                player.walkOnTrap();
            }

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
                    if(checkForDoors == 1) {
                        System.out.println("Going " + commandParts[1]);
                    }
                }
                else {
                    System.out.println("You can't go without any direction");
                }
            }

            else if(command.equalsIgnoreCase("look")) {
                String itemDescription = map[row][col].getItemDescription();
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
        System.out.println("***Thanks for playing***");
    }
}
