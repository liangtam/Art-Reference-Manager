package ui;

import model.*;

import java.util.*;

public class Menu {
    private List<ColourPalette> colourPalettes;
    // private List<ReferenceFolder> referenceFolders; to be added in later phase with GUI

    // EFFECTS: creates a menu with no colour palettes created
    public Menu() {
        colourPalettes = new ArrayList<>();
    }

    // EFFECTS: displays all the menu prompts, such as questions and instructions, and
    // processing user inputs
    public void startMenu() {
        System.out.println("Welcome to Art Reference Manager! Creative application name, amirite?");
        System.out.println("-----------------------------------------------------------------------");
        displayMenuOptions();
    }

    // EFFECTS: displays the most basic options to the user
    public void displayMenuOptions() {
        while (true) {
            System.out.println("-------|| MAIN MENU ||-------");
            System.out.println("Please select from the following options:");
            System.out.println("• Add a new colour palette: type 'addCP'");
            System.out.println("• Add a new colour to a colour palette: type 'addC");
            System.out.println("• Delete an existing colour palette: type 'delCP");
            System.out.println("• Delete an existing colour in an existing colour palette: type 'delC");
            System.out.println("• Rename an existing colour palette: renameCP");
            System.out.println("• View all colour palettes: 'cps'");
            System.out.println("To quit the program: 'quit'");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            if (command.equalsIgnoreCase("quit")) {
                break;
            }
            processCommandMainMenu(command);
        }
    }

    // EFFECTS: responds to user input for main menu
    public void processCommandMainMenu(String command) {
        if (command.equalsIgnoreCase("addCP")) {
            processCreateColourPalette();
        } else if (command.equalsIgnoreCase("delCP")) {
            processDeleteColourPalette();
        } else if (command.equalsIgnoreCase("delC")) {
            processDeleteColour();
        } else if (command.equalsIgnoreCase("renameCP")) {
            processRenameCP();
        } else if (command.equalsIgnoreCase("cps")) {
            displayColourPalettesMenu();
        }
    }

    // MODIFIES: this, ColourPalette
    // EFFECTS: Guides the user on how to create a colour palette
    public void processCreateColourPalette() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name of palette:");
        String name = scanner.next();
        if (colourPaletteAlreadyExists(name)) {
            System.out.println("Colour palette " + name + " already exists.");
            return;
        }
        ColourPalette colourPalette = new ColourPalette(name);
        System.out.println("Would you like to add colours to this palette? Y/N");
        String userInput = scanner.next();
        while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n")) {
            System.out.println("Invalid input. Please enter Y or N.");
            userInput = scanner.next();
        }
        if (userInput.equalsIgnoreCase("Y")) {
            List<Colour> coloursToAdd = processAddColours();

            for (Colour c: coloursToAdd) {
                colourPalette.addColour(c);
            }
        }

        System.out.println("Created colour palette " + name);
        this.colourPalettes.add(colourPalette);
        System.out.println("Number of colour palettes: " + this.colourPalettes.size());
        System.out.println("----------------------------------------------------------");
    }

    // EFFECTS: checks if the colour palette already exists in this menu
    //          (doesn't matter if it exists as a sub colour palette in another
    //          palette)
    public boolean colourPaletteAlreadyExists(String name) {
        for (ColourPalette cp: colourPalettes) {
            if (cp.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
    // MODIFIES: this
    // EFFECTS: creates a colour palette, returns true if successful
    public boolean createColourPalette(String name, String hex) {
        return false; //stub
    }

    // EFFECTS: guides the user on how to delete a colour palette
    public void processDeleteColourPalette() {

    }

    // MODIFIES: this
    // EFFECTS: deletes given colour palette if it exists in collection of colourPalettes and returns true,
    //          else indicate error and return false
    public boolean deleteColourPalette(ColourPalette colourPalette) {
        return false; //stub
    }

    // EFFECTS: guides the user on how to rename a colour palette
    public void processRenameCP() {

    }

    // MODIFIES: this
    // EFFECTS: deletes given colour palette if it exists in collection of colourPalettes and returns true,
    //          else indicate error and return false
    public boolean renameColourPalette(ColourPalette colourPalette) {
        return false; //stub
    }

    // Guides the user on how to add colours to a colour palette
    public List<Colour> processAddColours() {
        List<Colour> colours = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Name of colour:");
            String name = scanner.next();
            if (name.equalsIgnoreCase("done")) {
                break;
            }
            System.out.println("HEX code of colour (include #):");
            String hex = scanner.next();
            if (hex.equalsIgnoreCase("done")) {
                break;
            }
            Colour colour = new Colour(name, hex);
            colours.add(colour);
            System.out.println("Added colour " + name + "!");
            System.out.println("Type 'done' if you're finished adding colours to this palette.");
        }


        return colours;
    }

    // EFFECTS: display all colour palettes' names
    public void displayColourPalettesMenu() {
        if (colourPalettes.isEmpty()) {
            System.out.println("There are no colour palettes.");
        } else {
            for (int i = 0; i < colourPalettes.size(); i++) {
                System.out.println("[" + i + "]" + " " + colourPalettes.get(i).getName());
            }
        }
        System.out.println("--------------------------------------------------------");
    }


    // EFFECTS: deletes colour
    public void processDeleteColour() {

    }


}
