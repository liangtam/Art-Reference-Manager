package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Represents the UI the user interacts with to add colour palettes (and later, reference images in phase 3)
public class Menu {
    private List<ColourPalette> colourPalettes;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/colourPalettes.json";
    private List<ReferenceFolder> referenceFolders;

    // EFFECTS: creates a menu with no colour palettes created
    public Menu() {
        colourPalettes = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // ----------------------------------MAIN MENU METHODS--------------------------------------

    // EFFECTS: displays all the menu prompts, such as questions and instructions, and
    //          processing user inputs
    public void startMenu() {
        System.out.println("Welcome to Art Reference Manager! Creative application name, amirite?");
        System.out.println("-----------------------------------------------------------------------");
        displayMenuOptions();
    }

    // EFFECTS: displays the most basic main menu options to the user
    public void displayMenuOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-------|| MAIN MENU ||-------");
            System.out.println("Please select from the following options:");
            System.out.println("• Add a new colour palette: type 'addCP'");
            System.out.println("• Delete an existing colour palette: type 'delCP'");
            System.out.println("• View all colour palettes: 'cps'");
            System.out.println("Save: s");
            System.out.println("Load: l");
            System.out.println("To quit the program: 'quit'");
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
        } else if (command.equalsIgnoreCase("cps")) {
            displayColourPalettesMenu();
        } else if (command.equalsIgnoreCase("s")) {
            saveAllColourPalettes();
        } else if (command.equalsIgnoreCase("l")) {
            loadColourPalettes();
        } else {
            System.out.println("Invalid response :(");
        }
    }

    // MODIFIES: this
    // EFFECTS: Guides the user on how to create a colour palette, and adds that palette to
    //          collection of colour palettes
    public void processCreateColourPalette() {
        ColourPalette newColourPalette = createColourPalette();

        if (newColourPalette != null) {
            System.out.println("Created colour palette " + newColourPalette.getName());
            this.colourPalettes.add(newColourPalette);
            System.out.println("Number of colour palettes: " + this.colourPalettes.size());
            System.out.println("----------------------------------------------------------");
        }
    }

    // EFFECTS: return true if the colour palette we want to add already exists,
    //          else return false
    //          (does not check if it exists as a sub colour palette in another
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
    // EFFECTS: guides the user on how to delete a colour palette and deletes it if it exists
    public void processDeleteColourPalette() {
        boolean anyColors = printAllColourPalettes();
        Scanner scanner = new Scanner(System.in);
        if (!anyColors) {
            return;
        }
        System.out.println("Name of palette to delete:");
        String name = scanner.next();
        if (colourPaletteAlreadyExists(name)) {
            for (ColourPalette cp: this.colourPalettes) {
                if (cp.getName().equals(name)) {
                    this.colourPalettes.remove(cp);
                    break;
                }
            }
        } else {
            System.out.println("This colour palette does not exist.");
        }
    }

    // MODIFIES: ColourPalette
    // EFFECTS: guides the user on how to rename given colour palette, and renames its name to their input
    public void processRenameCP(ColourPalette cp) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Current name: " + cp.getName());
        System.out.println("Please enter the new name for this palette: ");
        String name = scan.nextLine();
        cp.setName(name);
        System.out.println("Successfully renamed palette to " + cp.getName() + "!");
        return;
    }

    // EFFECTS: Guides the user on how to add colours to the given colour palette, and adds the colours to
    //          the given colour palette
    public void processAddColours(ColourPalette colourPalette) {
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
        for (Colour colour: colours) {
            colourPalette.addColour(colour);
        }
    }

    // EFFECTS: display all colour palettes' names
    public void displayColourPalettesMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printAllColourPalettes();
            System.out.println("For further options on any of the colour palettes, enter the name of the palette.");
            System.out.println("Enter 'mm' to return to main menu.");
            String userInput = scanner.next();
            if (userInput.equals("mm")) {
                break;
            } else {
                displayOptionsForOneColourPalette(userInput);
            }
        }

        System.out.println("--------------------------------------------------------");
    }

    // EFFECTS: prints all colour palettes (no sub palettes)
    public boolean printAllColourPalettes() {
        if (colourPalettes.isEmpty()) {
            System.out.println("There are no colour palettes.");
            return false;
        } else {
            System.out.println("--NUMBER OF COLOUR PALETTES: " + colourPalettes.size() + "--");
            for (int i = 0; i < colourPalettes.size(); i++) {
                System.out.println("[" + i + "]" + " " + colourPalettes.get(i).getName());
            }
            return true;
        }
    }

    // ------------------------------SINGULAR COLOUR PALETTE MENU---------------------------------
    @SuppressWarnings("methodlength")
    // EFFECTS: Displays options for user on what they can do to the colour palette
    public void displayOptionsForOneColourPalette(String colourPaletteName) {
        Scanner scanner = new Scanner(System.in);
        for (ColourPalette cp: colourPalettes) {
            if (colourPaletteName.equalsIgnoreCase(cp.getName())) {
                while (true) {
                    System.out.println("---COLOUR PALETTE: " + cp.getName().toUpperCase());
                    displayColourPaletteDetails(cp);
                    System.out.println("---OPTIONS---");
                    System.out.println("• Rename colour palette: 'renameCP'");
                    System.out.println("• To add a colour: 'addC'");
                    System.out.println("• To delete a colour: 'delC'");
                    System.out.println("• To edit a colour: 'editC'");
                    System.out.println("• To view all sub colour palettes within this palette: 'subCps'");
                    System.out.println("• To view all colours within this palette: 'c'");
                    System.out.println("• To return to palettes menu: 'back'");
                    String userInput = scanner.nextLine();
                    if (userInput.equalsIgnoreCase("back")) {
                        break;
                    } else if (isInValidResponse(userInput)) {
                        System.out.println("Invalid response :( Please try again!");
                    } else {
                        processCommandsForOneColourPalette(userInput, cp);
                    }
                }
                return;
            }
        }
    }

    // EFFECTS: returns true if input is an invalid response for the options in the singular colour palette menu
    public boolean isInValidResponse(String input) {
        return (!input.equalsIgnoreCase("delC") && !input.equalsIgnoreCase("renameCP")
            && !input.equalsIgnoreCase("addSubCP") && !input.equalsIgnoreCase("addC")
            && !input.equalsIgnoreCase("subCps") && !input.equalsIgnoreCase("delCP")
            && !input.equalsIgnoreCase("c") && !input.equalsIgnoreCase("editC"));
    }

    // EFFECTS: Does what user input commands to given colour palette
    public void processCommandsForOneColourPalette(String userInput, ColourPalette cp) {
        if (userInput.equalsIgnoreCase("delC")) {
            processDeleteColour(cp);
        } else if (userInput.equalsIgnoreCase("renameCP")) {
            processRenameCP(cp);
        } else if (userInput.equalsIgnoreCase("addC")) {
            processAddColours(cp);
        } else if (userInput.equalsIgnoreCase("editC")) {
            processEditColour(cp);
        } else if (userInput.equalsIgnoreCase("subCps")) {
            manageSubColourPalettes(cp);
        } else if (userInput.equalsIgnoreCase("c")) {
            printAllColours(cp);
        } else {
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: ColourPalette
    // EFFECTS: deletes colour from given colour palette, cp
    public void processDeleteColour(ColourPalette cp) {
        Scanner scanner = new Scanner(System.in);
        printAllColours(cp);
        System.out.println("Please enter the name of the colour you'd like to delete from this palette.");
        String colourName = scanner.nextLine();
        boolean deleteColourSuccess = cp.deleteColour(colourName);
        if (deleteColourSuccess) {
            System.out.println("Deleted " + colourName);
        } else {
            System.out.println("The colour does not exist.");
        }
    }

    // EFFECTS: guides user on how to rename or edit the hex of a colour in given colour palette
    public void processEditColour(ColourPalette cp) {
        Scanner scanner = new Scanner(System.in);
        printAllColours(cp);
        System.out.println("Which colour would you like to edit?");
        String colorName = scanner.nextLine();
        for (Colour colour: cp.getColours()) {
            if (colour.getName().equals(colorName)) {
                System.out.println("Type 'rename' to rename colour, 'editHex' to edit colour's hex");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("rename")) {
                    renameColour(colour);
                } else if (input.equalsIgnoreCase("editHex")) {
                    editColourHex(colour);
                }
                return;
            }
        }
        System.out.println("This colour does not exist.");

    }

    // MODIFIES: Colour
    // EFFECTS: changes given colour's name to new name
    public void renameColour(Colour colour) {
        System.out.println("Current name: " + colour.getName());
        Scanner scanner = new Scanner(System.in);
        System.out.println("New name:");
        String newName = scanner.nextLine();
        colour.setName(newName);
        System.out.println("Successfully changed color name to " + colour.getName() + "!");
    }

    // MODIFIES: Colour
    // EFFECTS: changes given colour's hex to new hex
    public void editColourHex(Colour colour) {
        System.out.println("Current hex: " + colour.getHex());
        Scanner scanner = new Scanner(System.in);
        System.out.println("New hex:");
        String newHex = scanner.nextLine();
        colour.setHex(newHex);
        System.out.println("Successfully changed color name to " + colour.getHex() + "!");
    }

    // MODIFIES: ColourPalette
    // EFFECTS: creates a new sub colour palette in given colour palette, cp
    public void processAddNewSubColourPalette(ColourPalette cp) {
        ColourPalette subColourPalette = createColourPalette();
        if (subColourPalette != null) {
            cp.addSubColourPalette(subColourPalette);
        }
        System.out.println("Successfully added colour palette " + subColourPalette.getName() + " to " + cp.getName());
        System.out.println("-------------------------------------------------------------------------------------");
    }

    // MODIFIES: ColourPalette
    // EFFECTS: guides the user through deleting a sub colour palette from a colour palette, and deletes it
    public void processDeleteSubColourPalette(ColourPalette cp) {
        if (cp.getSubColourPalettes().isEmpty()) {
            System.out.println("There are currently no sub colour palettes.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the sub-colour palette you'd like to delete.");
        String name = scanner.nextLine();
        for (ColourPalette subCP: cp.getSubColourPalettes()) {
            if (subCP.getName().equals(name)) {
                cp.deleteSubColourPalette(subCP);
                System.out.println("Successfully removed " + name + " from " + cp.getName() + " palette.");
                return;
            }
        }
        System.out.println("The sub colour palette you entered does not exist.");


    }

    // EFFECTS: Gives options to the user on what they can do with the sub colour palettes of given palette, cp.
    //          Prints all the sub colour palettes of cp.
    public void manageSubColourPalettes(ColourPalette cp) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            printAllSubColourPalettes(cp);
            System.out.println("• To add a sub colour palette: 'addSubCP'");
            System.out.println("• To add a sub colour palette: 'delSubCP'");
            System.out.println("To go back to main colour palette menu: 'back'");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("back")) {
                break;
            } else if (input.equalsIgnoreCase("addsubCP")) {
                processAddNewSubColourPalette(cp);
            } else if (input.equalsIgnoreCase("delSubCP")) {
                processDeleteSubColourPalette(cp);
            }

        }

    }

    // EFFECTS: Creates a new colour palette using the information provided by user input
    public ColourPalette createColourPalette() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Name of palette:");
        String name = scan.next();
        if (colourPaletteAlreadyExists(name)) {
            System.out.println("Colour palette " + name + " already exists.");
            return null;
        }
        ColourPalette colourPalette = new ColourPalette(name);
        System.out.println("Would you like to add colours to this palette? Y/N");
        String userInput = scan.next();
        while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n")) {
            System.out.println("Invalid input. Please enter Y or N.");
            userInput = scan.next();
        }
        if (userInput.equalsIgnoreCase("Y")) {
            processAddColours(colourPalette);
        }

        return colourPalette;
    }

    // ----------------------------------PRINT METHODS--------------------------------------

    // EFFECTS: prints out the number of sub colour palettes and colours in this colour palette
    public void displayColourPaletteDetails(ColourPalette cp) {
        System.out.println(">> Number of sub colour palettes: " + cp.getNumOfColourPalettes());
        System.out.println(">> Number of colours: " + cp.getNumOfColours());
    }

    // EFFECTS: prints out all the names of the sub colour palettes in this colour palette
    public void printAllSubColourPalettes(ColourPalette cp) {
        System.out.println("---SUB COLOUR PALETTES---");
        if (cp.getSubColourPalettes().isEmpty()) {
            System.out.println("There are no sub colour palettes. :(");
            return;
        }
        for (ColourPalette colourPalette: cp.getSubColourPalettes()) {
            System.out.println("- " + colourPalette.getName());
        }
    }

    // EFFECTS: prints out all the colours in this colour palette in formate - Name:Hex
    public void printAllColours(ColourPalette cp) {
        System.out.println("--COLOURS--");
        for (Colour colour: cp.getColours()) {
            System.out.println("- " + colour.getName() + ":" + colour.getHex());
        }
    }

    // ---------- DATA PERSISTENCE STUFF --------------

    //  EFFECTS: Saves all the current created colour palettes to file
    private void saveAllColourPalettes() {
        try {
            jsonWriter.open();
            jsonWriter.writeListOfColourPalettes(colourPalettes);
            jsonWriter.close();
            System.out.println("Saved all colour palettes to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Could not save to file: " + JSON_STORE);
        }
    }



    // MODIFIES: this
    // EFFECTS: loads list of colour palettes from file
    private void loadColourPalettes() {
        try {
            colourPalettes = jsonReader.read();
            System.out.println("Loaded all the colour palettes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Could not save to file: " + JSON_STORE);
        }
    }


}
