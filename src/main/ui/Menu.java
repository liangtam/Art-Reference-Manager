package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<ColourPalette> colourPalettes;
    private int numOfColourPalettes;
    // private List<ReferenceFolder> referenceFolders; to be added in later phase with GUI

    // EFFECTS: creates a menu with no colour palettes created
    public Menu() {
        colourPalettes = new ArrayList<>();
        numOfColourPalettes = 0;
    }

    // EFFECTS: displays all the menu prompts, such as questions and instructions, and
    // processing user inputs
    public void startMenu() {
        System.out.println("Welcome to Art Reference Manager! Creative application name, amirite?");
        System.out.println("-----------------------------------------------------------------------");
    }

    // EFFECTS: displays the most basic options to the user
    public void displayMenuOptions() {
        System.out.println("Please select from the following options:");
        System.out.println("Add a new colour palette: type 'addCP'");
        System.out.println("Add a new colour: type 'addC");
        System.out.println("Delete an existing colour palette: type 'delCP");
        System.out.println("Delete an existing colour: type 'delC");
        System.out.println("Rename an existing colour palette: renameCP");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        processCommand(command);
    }

    // EFFECTS: responds to user input
    public void processCommand(String command) {
    }

    // MODIFIES: this
    // EFFECTS: creates a colour palette, returns true if successful
    public boolean createColourPalette() {
        return false; //stub
    }

    // MODIFIES: this
    // EFFECTS: deletes given colour palette if it exists in collection of colourPalettes and returns true,
    //          else indicate error and return false
    public boolean deleteColourPalette(ColourPalette colourPalette) {
        return false; //stub
    }

    // MODIFIES: this
    // EFFECTS: deletes given colour palette if it exists in collection of colourPalettes and returns true,
    //          else indicate error and return false
    public boolean renameColourPalette(ColourPalette colourPalette) {
        return false; //stub
    }


}
