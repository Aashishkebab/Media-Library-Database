//Aashish Bharadwaj
//Dr. Cheatham
//CS1181
//Project 2
package Media_Library_Database;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author aashi
 */
public class MediaLibraryDatabase extends Application{
    
    static Alert wait = new Alert(AlertType.INFORMATION);
    static ButtonType doneWaiting = new ButtonType("Useless button");
    
    static boolean hasSaved = false;
    
    static ArrayList<Item> itemList = new ArrayList<Item>();
    
/*Code for the window*/
    static VBox wholeWindow = new VBox(7);
    static Scene mainWindow = new Scene(wholeWindow, 777, 555);
        
        static VBox padding = new VBox(3);
        static VBox leftMargin = new VBox();
        
        static HBox entireWindow = new HBox(33);
            
            static VBox itemDisplay = new VBox(11);
                static Label numberOfItems = new Label();
                
                static ListView items = new ListView();

                static ArrayList<Item> placeholder = new ArrayList<Item>();
                static ObservableList<Item> listedItems = FXCollections.observableList(placeholder);
                
                static Button generator = new Button("Generate random items");

                static Label status = new Label();
            
            static VBox spacing = new VBox();
            
            static VBox userEntry = new VBox(17);
                static Label alignment = new Label();
            
                static GridPane addBox = new GridPane();

                    static TextField titleText = new TextField();
                    static TextField formatText = new TextField();

                    static Label titleLabel = new Label("Title:   ");
                    static Label formatLabel = new Label("Format:   ");

                    static Button addButton = new Button("Add");

                static Button removeButton = new Button("Remove");
                static Button returnButton = new Button("Return");

                static GridPane loanBox = new GridPane();
                    static TextField loanTo = new TextField();
                    static TextField loanOn = new TextField();

                    static Label loanToLabel = new Label("Loaned To:   ");
                    static Label loanOnLabel = new Label("Loaned On:   ");

                    static Button loan = new Button("Loan");

                static VBox sorter = new VBox(7);

                    static Label sortWord = new Label("Sort");
                    
                    static ToggleGroup sortType = new ToggleGroup();
                    static RadioButton byTitle = new RadioButton("By title");
                    static RadioButton byDate = new RadioButton("By date");
                    
                    static HBox someSpace = new HBox(17);
                    
                    static HBox fileIO = new HBox(13);
                        static Button saveStuff = new Button("Save collection");
                        static Button openStuff = new Button("Open collection");
                
            static VBox cushion = new VBox();
            
    public static void main(String[] args){        
        System.out.println("main started");
        
        Application.launch(args);
        
        System.out.println("main complete");
    }
    
    public static void compareTitles(){
        System.out.println("compareTitles started");
        
        wait.setContentText("Sorting by title");
        wait.show();
        
        for(int i = 0; i < itemList.size(); i++){
            itemList.get(i).setCompareType("title");
        }
        Collections.sort(itemList);
        
        toast("Sorted by title.");
        
        System.out.println("compareTitles complete");
        return;
    }
    
    public static void compareDates(){
        System.out.println("compareDates started");
        
        wait.setContentText("Sorting by date...");
        wait.show();
        
        for(int i = 0; i < itemList.size(); i++){
            itemList.get(i).setCompareType("date");
        }
        Collections.sort(itemList);
        
        toast("Sorted by title.");
        
        System.out.println("compareDates complete");
        return;
    }
    
    public static int loanCount(){
        int loanedItems = 0;
        
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).isLoan() == true){
                loanedItems++;
            }
        }
        
        return loanedItems;
    }
        
    public static void searchItem(){    //Search for an item
        System.out.println("searchItem started");
        
        int option;
        
        ArrayList<Item> results = new ArrayList<Item>();
        
        option = getMenuOption("What to search by", "<Search by title> <Search by format> <Search by loan status> <Search by borrower> <Search by date>");
        
        if(option == 5){
            results = searchDate();
        }else if(option == 4){
            results = searchName();
        }else if(option == 3){
            results = searchLoans();
        }else if(option == 2){
            results = searchFormat();
        }else if(option == 1){
            results = searchTitle();
        }else{
            System.out.println("We are now searching for the meaning of life.");
            System.out.println("Please hold...");
            return;
        }
        
        displayInquiry(results);
        
        System.out.println("searchItem complete");
    }
    
    public static void displayInquiry(ArrayList<Item> results){ //Display search results
        System.out.println("displayInquiry started");
        
        System.out.println("");
        for(int i = 0; i < results.size(); i++){
            System.out.println(results.get(i).toString());
        }
        
        if(results.size() == 0){
            System.out.println("That query returned no results.");
        }else{
            System.out.println("Your search returned " + results.size() + " results.");
        }
        pause();
        
        System.out.println("displayInquiry complete");
    }
    
    public static ArrayList<Item> searchDate(){
        System.out.println("searchDate started");
        
        ArrayList<Item> results = new ArrayList<Item>();
        int option; int query;
        
        option = getMenuOption("What attribute to search for?", "<Search by day> <Search by month> <Search by year>");
        if(option == 1){
            query = verifyInt("Please enter the day: ");
            for(int i = 0; i < itemList.size(); i++){
                if(itemList.get(i).getDay() == query){
                    results.add(itemList.get(i));
                }
            }
        }else if(option == 2){
            query = verifyInt("Please enter the month: ");
            for(int i = 0; i < itemList.size(); i++){
                if(itemList.get(i).getMonth() == query){
                    results.add(itemList.get(i));
                }
            }
        }else if(option == 3){
            query = verifyInt("Please enter the year: ");
            for(int i = 0; i < itemList.size(); i++){
                if(itemList.get(i).getYear() == query){
                    results.add(itemList.get(i));
                }
            }
        }
        
        System.out.println("searchDate complete");
        return results;
    }
    
    public static ArrayList<Item> searchName(){
        System.out.println("searchName started");
        
        ArrayList<Item> results = new ArrayList<Item>();
        String query;
        
        query = verifyString("Please enter the name to search for: ");
        
        for(int i = 0; i < itemList.size(); i++){
            try{
                if(itemList.get(i).getName().equals(query)){
                    results.add(itemList.get(i));
                }
            }catch(Exception exception){}
        }
        
        System.out.println("searchName complete");
        return results;
    }
    
    public static ArrayList<Item> searchLoans(){
        System.out.println("searchLoans started");
        
        ArrayList<Item> results = new ArrayList<Item>();
        int option;
        
        option = getMenuOption("Search for", "<Items on loan> <Items not on loan>");
        
        if(option == 1){
            for(int i = 0; i < itemList.size(); i++){
                if(itemList.get(i).isLoan() == true){
                    results.add(itemList.get(i));
                }
            }
        }else if(option == 2){
            for(int i = 0; i < itemList.size(); i++){
                if(itemList.get(i).isLoan() == false){
                    results.add(itemList.get(i));
                }
            }
        }
        
        System.out.println("searchLoans complete");
        return results;
    }
    
    public static ArrayList<Item> searchFormat(){
        System.out.println("searchFormat started");
        
        ArrayList<Item> results = new ArrayList<Item>();
        String query;
        
        query = verifyString("Please enter the title to search for: ");
        
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).getFormat().equals(query)){
                results.add(itemList.get(i));
            }
        }
        
        System.out.println("searchFormat complete");
        return results;
    }
    
    public static ArrayList<Item> searchTitle(){
        System.out.println("searchTitle started");
        
        ArrayList<Item> results = new ArrayList<Item>();
        String query;
        
        query = verifyString("Please enter the title to search for: ");
        
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).getTitle().equals(query)){
                results.add(itemList.get(i));
            }
        }
        
        System.out.println("searchTitle complete");
        return results;
    }
    
    public static void randomizeCollection(){    //Creates randomized collection of items
        System.out.println("randomizeCollection started");
        
        if(itemList.size() >= 33333){   //If ArrayList is too large
            System.out.println("Your collection is full.");
            System.out.println("You need to remove items to add more.");
            
            showAlert("Error adding items", "Your collection is full", 
                    "You must remove items before you can add any more.", "INFORMATION");
            
            pause();
            return;
        }
        
        System.out.println(""); System.out.println("");
        System.out.println("This will NOT delete any items.");
        System.out.println("It will only append to your current collection.");
        int numItems;
        
        //numItems = verifyInt("Number of items to generate: ");
        
        try{
            String userInput = promptForText("Item Generator", "This will NOT delete any items, only add new ones.", "Number of items to generate: ");
            if(userInput == null){
                return;
            }
            numItems = Integer.parseInt(userInput);
        }catch(NullPointerException noValue){
            return;
        }catch(Exception notNumber){
            showAlert("Cannot generate", "You have not entered a whole number", "Please enter a whole number next time.", "WARNING");
            return;
        }
        
        if((numItems + itemList.size()) <= 33333){
        }else if((numItems + itemList.size()) > 33333){
            System.out.println("");
            System.out.println("There is no room left for " + numItems + " many more items.");
            System.out.println("Please enter a number smaller than " +((33333 - itemList.size()) + 1));
            System.out.println("Thank you very much.");
            showAlert("Not enough room", "There is no room left for " + numItems + " many more items.",
                    "Please enter a number smaller than " +((33333 - itemList.size()) + 1), "WARNING");
            
            return;
        }
        
        wait.setContentText("Generating items...");
        wait.show();
        
        int t;  Item random = new Item(null, null, false, null, null);
        for(t = 0; t < numItems; t++){  //Generate random data
            itemList.add(new Item(random.generateTitle(), random.generateFormat(), random.generateLoan(), random.generateDate(), random.generateName()));
        }
        
        System.out.println("");
        System.out.println(t + " items have been generated.");
        System.out.println("Please list items to view them.");
        
        toast(numItems + " items have been added.");
        
        hasSaved = false;
        System.out.println("randomizeCollection complete");
        
        return;
    }
    
    public static String truncate(String string, int length){   //Shorten strings so they don't flow off screen
        if(string.length() > (length + 1)){
            string = string.substring(0, length) + "...";
        }
        
        return string;
    }
    
    public static void initializeFiles(){    //Check for initial program files
        System.out.println("initializeFiles started");
        
        Scanner indexer;
        ArrayList<String> collectionsArray= new ArrayList<String>();
        String collectionString = "";
        
        try{    //Check if collections index exists
            indexer = new Scanner(new File("collections.bhar"));
        }catch(FileNotFoundException nope){
            System.out.println("DISCLAIMER:");  System.out.println("");
            System.out.println("This program features a random name generator.");
            System.out.println("I am not responsible if an offensive or innappropriate name is generated.");
            System.out.println("I tried my best to remove such possibilities, but it is still randomized.");
            
            showAlertWait("Read This!", "DISCLAIMER:", "This program features a random name generator." +
                    "\nI am not responsible if an offensive or innappropriate name is generated." +
                    "\nI tried my best to remove such possibilities, but it is still randomized.", "INFORMATION");
            
            
            pause();
            
            System.out.println("");
            System.out.println("There is no collections index.");
            System.out.println("We will now create a new collection.");
            createCollection();
            return;
        }
                    
        if(indexer.hasNextLine() == false){ //Check that there are other collections to import
            System.out.println(""); System.out.println(""); System.out.println("");
            System.out.println("There are no previous collections to import.");
            System.out.println("We will now proceed to create one.");
            createCollection();
            return;
        }
        
        int option = getMenuOption("Would you like to import a previous collection or create a new one?", "<Import a previous collection of items> <Create new collection>");
        option = 2;
        
        if(option == 1){
            importCollection();
            return;
        }
        if(option == 2){
            createCollection();
            return;
        }
        
        hasSaved = false;
        System.out.println("initializeFiles complete");
        
        return;
    }
    
    public static void importCollection(){   //Import text file with media items
        System.out.println("importCollection started");
        
        String userFile;    //name of collection to import
        ArrayList<String> collectionsArray= new ArrayList<String>();    //Array of collections
        Scanner input = new Scanner(System.in);
        String collectionString = "";
        int choice;
        int numItems = 0;   //Number of collections
        
        File collections = new File("collections.bhar"); //Initialize collections list file        
        /*try{    //List out collections of items
            Scanner collectCollections = new Scanner(collections);
            
            String eachLine;
            for(numItems = 0; collectCollections.hasNextLine(); numItems++){    //Get all file contents line by line
                eachLine = collectCollections.nextLine();
                if(eachLine.trim().isEmpty() == false){ //If there is something to import
                    collectionsArray.add(eachLine);
                    collectionString += "<" +  collectionsArray.get(numItems) + "> ";   //Create String for getMenuOption to display
                }else{  //If line is blank
                    numItems--; //Don't add to number of items
                }
            }
            
            if(collectionString.trim().isEmpty()){  //If there are no collections to import
                System.out.println(""); System.out.println(""); System.out.println("");
                System.out.println("Looks like there are no collections to import.");
                System.out.println("We will now proceed to create one.");
                System.out.println(""); System.out.println("Press ENTER to continue");
                //input.nextLine();
                createCollection();
                return;
            }
            
            choice = getMenuOption("Please choose collection to import", collectionString);
            choice = 0;
            
        }catch(FileNotFoundException noCollections){
            try {
                sleep(333);
            }catch (InterruptedException ex){}
            System.out.println("");
            System.out.print("H");
            for(int i = 0; i < 13; i++){
                System.out.print("m");
            }
            try {
                sleep(1111);
            }catch (InterruptedException ex)s{}
            //If collections file nonexistent, redirect user to create new one
            System.out.println("");
            System.out.println("There either were no previous collections,");
            System.out.println("or the collections index has been deleted.");
            System.out.println("");
            System.out.println("Either way, you will now have to create a new one.");
            System.out.println("");
            System.out.println("Press ENTER to continue.");
            //input.nextLine();
            createCollection();
            return;
        }*/
        
        //userFile = collectionsArray.get(choice - 1) + ".aash";   //Get the file that user wants to open
        
        //File items = new File(userFIle);
        
        FileChooser chooseFile = new FileChooser();
        chooseFile.setTitle("Select a previous collection");
        chooseFile.getExtensionFilters().add(new ExtensionFilter("Media Collection", "*.aash"));
        
        File importedItems = chooseFile.showOpenDialog(new Stage());
        try{
            importedItems.exists();
        }catch(Exception exception){
            return;
        }
        
        if(itemList.size() > 0){
            Toolkit.getDefaultToolkit().beep();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("How to import items");
            alert.setHeaderText("We can either add these items to the current collection,\nor overwrite the current list");
            alert.setContentText("Would you like to merge the items, or overwrite the current collection?");

            ButtonType merge = new ButtonType("Merge items");
            ButtonType overwrite = new ButtonType("Overwrite current collection");
            ButtonType cancel = new ButtonType("Cancel opening file");

            alert.getButtonTypes().setAll(merge, overwrite, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == merge){
                System.out.println("We will now merge items.");
            }else if(result.get() == overwrite){
                System.out.println("Current collection will be deleted.");
                
                for(int i = (itemList.size() - 1); i >= 0; i--){
                    listedItems.remove(i);
                }
            }else if(result.get() == cancel){
                return;
            }else{
                System.out.println("You have pressed an invalid button");
            }
        }
        
        try{
            Scanner intake = new Scanner(importedItems);    //Check if file exists
            getItems(importedItems);
        }catch(FileNotFoundException noFile){
            System.out.println("");
            System.out.println("");
            showAlert("What on Earth...", "Oh crap", "The file you tried to import was not found.\nTry a different file.", "WARNING");
            System.out.println("The chosen collection is corrupted or was deleted or something.");
            System.out.println("Sincere apologies for this conundrum.");
            System.out.println("");
            System.out.println("We're just going to have you create a new collection now.");
            System.out.println("");
            System.out.println("Press ENTER to continue.");
            System.out.println("");
            //input.nextLine();
            createCollection();
        }catch(NullPointerException ohnoes){}
        
        hasSaved = false;
        System.out.println("importCollection complete");
        
        return;
    }
    
    public static void getItems(File theFile){    //Get all items in a collection file
        System.out.println("getItems started");
        
        Scanner userInput = new Scanner(System.in);
        Scanner itemNames = null;
        try{
            itemNames = new Scanner(theFile);
        }catch(FileNotFoundException noItems){
            System.out.println("");
            System.out.println("Unfortunately collection \"" + truncate(theFile.getName(), 47) + "\" is corrupted.");
            System.out.println("We will now proceed to create a new collection.");
            System.out.println("");
            System.out.println("Press ENTER to continue");
            System.out.println("");
            //userInput.nextLine();
            showAlert("Something bad...", "This is not good at all", "The file you clicked on could not be found.", "INFROMATION");
            createCollection();
            return;
        }
        
        String overallItem;
        String title;   String format;  String name;
        String properties[];    String dateStrings[];
        String date;
        boolean loan;
        int numItems = 0;
        
        ArrayList<Item> backupList = new ArrayList<Item>();
        for(int i = 0; i < itemList.size(); i++){
            backupList.add(itemList.get(i));
        }
        
        int itemsImported = 0;
        while(itemNames.hasNextLine()){
            numItems++;
            overallItem = itemNames.nextLine();
            
            if(itemList.size() > 33333){
                if(backupList.size() > 0){
                    showAlert("File too large", "The file is too large to import.\nThere cannot be more than 33333 items.", 
                            "The file contains more than " + (numItems - 1) + " items.\nThere are already " + 
                                    backupList.size() + " items in the library.", "INFORMATION");
                }else{
                    showAlert("File too large", "The file is too large to import.", "There are more than 33333 items in the file", "INFORMATION");
                }
                
                itemList = backupList;
                return;
            }
            
            try{
                properties = overallItem.split(",");
                
                try{
                    title = properties[0];
                }catch(Exception notExist){
                    title = "Corrupted title";
                }
                
                try{
                    format = properties[1];
                }catch(Exception notExist){
                    format = "Corrupted format";
                }
                
                try{
                    if(properties[2].equals("true")){
                        loan = true;
                    }else if(properties[2].equals("false")){
                        loan = false;
                    }else{  //IIf corrupted loan, automatically set loan to false
                        loan = false;
                    }
                }catch(Exception notExist){
                    loan = false;
                }
                
                try{
                    date = properties[3];
                }catch(Exception notExist){
                    date = "Corrupted date";
                }
                
                try{
                    name = properties[4];
                }catch(Exception notExist){
                    name = "Corrupted name";
                }
                
                try{
                    itemList.add(new Item(title, format, loan, date, name));
                    itemsImported++;
                }catch(Exception ohno){
                    itemList.add(new Item("corrupt", "corrupt", false, "0/0/0", "corrupt"));
                }
                
            }catch(StringIndexOutOfBoundsException noComma){
                itemList.add(new Item("corrupt", "corrupt", false, "0/0/0", "corrupt"));
            }
        }
/*        
        System.out.println("The following items are being imported:"); System.out.println(""); System.out.println(""); System.out.println("");
        try{
            sleep(789);
        }catch(InterruptedException ohnoes){}
        
        for(int o = 0; o < numItems; o++){
            System.out.println("");
            System.out.println("Title: " + truncate(itemList.get(o).getTitle()));
            System.out.println("  Format: " + truncate(itemList.get(o).getFormat()));
            System.out.println("  Loan: " +  itemList.get(o).isLoan());
            try{
                sleep(77);
            }catch(InterruptedException goForIt){}
        }
*/
        toast(itemsImported + " items imported successfully.");
        hasSaved = false;
        System.out.println("getItems complete");
        
        return;
    }
    
    public static ArrayList<Integer> findTitle(String title){ //Find given title in list
        System.out.println("findTitle started");
        
        ArrayList<Integer> occurrences = new ArrayList<Integer>();
        
        for(int i = 0; i < itemList.size(); i++){
            if(title.equals(itemList.get(i).getTitle())){
                occurrences.add(i);
            }
        }
        
        System.out.println("findTitle complete");
        return occurrences;
    }
    
    public static boolean titleExists(String title){  //Check if title exists
        System.out.println("titleExists started");
        
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).getTitle().equals(title)){
                return true;
            }
        }
        
        System.out.println("titleExists complete");
        return false;
    }
    
    public static void createCollection(){   //Create a new collection
        System.out.println("createCollection started");
        
        Scanner input = new Scanner(System.in);
        
        System.out.println(""); System.out.println("");
        
        File database = new File("collections.bhar");   //Create database of collections
        try{
            System.out.println("A new collection has been created!");
        }catch(Exception cantWork){
            System.out.println("Unable to create new collection.");
            System.out.println("Apologies.");
            try{sleep(666);}catch(Exception exception){}
            leaveProgram();
        }
        
        System.out.println(""); System.out.println(""); System.out.print("Press ENTER to continue");    System.out.println("");
        //input.nextLine();
        
        hasSaved = false;
        System.out.println("createCollection complete");
        
        return;
    }
    
    public static void insertItem(){ //Add new item to collection
        System.out.println("insertItem started");
        
        Scanner input = new Scanner(System.in);
        String title;   String format;  boolean loan;
        int option;
        
        if(itemList.size() >= 33333){
            System.out.println("");
            System.out.println("Your collection is full.");
            System.out.println("You need to remove items to add more.");
            System.out.println("");
            
            showAlert("Too much!", "Your collection is full!", "You need to remove items to add more.", "INFORMATION");

            pause();
            return;
        }
        
        option = getMenuOption("How to name your item:", "<Generate random name  <--------Seriously, try this! It's fun!> <Type in custom name>");
        option = 0;
        
        //while(true){
        if(option == 1){
            Item random = new Item(null, null, false, null, null);  //Used to call the generateTitle method.
            title = random.generateTitle();
        }else{
            System.out.println("");
            System.out.print("Please input title: ");
            //title = input.nextLine();
            title = titleText.getText();

            if(title.trim().isEmpty()){
                System.out.println("");
                System.out.println("Title cannot be blank, try again.");

                showAlert("Invalid input", "Title cannot be blank", "Please enter a title and try again.", "WARNING");
            }
        }
        //}
        
        if(titleExists(title)){   //If the item already exists
            
            /*if(getMenuOption("\"" + truncate(title, 39) + "\""+ " already exists.", "<Do not add this item> <Add item anyways>") == 1){
                return;
            }*/
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            Toolkit.getDefaultToolkit().beep();
            alert.setTitle("Duplicate item");
            alert.setContentText(truncate(titleText.getText(), 33) + " already exists." +
                    "\nWould you like to add the item again?");
            Optional<ButtonType> result = alert.showAndWait();	//This blocks further code execution until dialog is closed
            if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
                return;
            }
        }
        
        if(option == 1){
            System.out.println("");
            System.out.print("The title of your item is: " + truncate(title, 39));
            toast("The title of your item is: " + title);
        }
        
        //while(true){
        System.out.println("");
        System.out.print("Please input item format: ");
        //format = input.nextLine();
        format = formatText.getText();

        if(format.trim().isEmpty()){
            System.out.println("");
            System.out.println("Format cannot be blank, try again.");

            showAlert("Invalid input", "Format cannot be blank", "Please enter a format and try again.", "WARNING");
        }
        //}
        
        if(format.trim().isEmpty() || title.trim().isEmpty()){
            return;
        }
        
        loan = false;
        
        itemList.add(new Item(title, format, loan, null, ""));
        System.out.println(truncate(title, 30) + " has been added as " + truncate(format, 30));
        toast(truncate(title, 33) + " has been added as " + truncate(format, 33));
        
        clearAddBox();
        
        hasSaved = false;
        System.out.println("insertItem complete");
        
        return;
    }
    
    public static void loanItem(){
        System.out.println("loanItem started");
        
        int option;
        String title;
        String showDuplicates = "";
        Scanner input = new Scanner(System.in);
        ArrayList<Integer> duplicates = new ArrayList<Integer>();
        ArrayList<Integer> alreadyLoaned = new ArrayList<Integer>();
        boolean loaned = true;
        int loanable = 0;
        int choose = 0;
        String name;
        boolean error = false;
        
        if(loanTo.getText().trim().isEmpty() == true){
            showAlert("Error loaning", "You have not entered a loaner", 
                    "Enter the person you're loaning to.", "WARNING");
            error = true;
        }
        
        if(loanOn.getText().trim().isEmpty() == true){
            showAlert("Error loaning", "You have not entered a date.", 
                    "Enter the date the item is loaned on.", "WARNING");
            error = true;
        }
        
        if(true || getMenuOption("How would you like to loan the item?", "<Type the title> <Choose from list>") == 1){
            System.out.print("Enter the title of media here: ");
            //title = input.nextLine();
            if(true || titleExists(title)){
                //duplicates = findTitle(title);
                /*for(int j = 0; j < duplicates.size(); j++){ //Create arraylist that states which items are on loan
                    if(itemList.get(duplicates.get(j)).isLoan() == false){
                        loaned = false; //If even one item is not on loan, then set this false
                        loanable++;
                        alreadyLoaned.add(duplicates.get(j));
                        choose = j;
                    }
                }*/
                //if(loaned == true){ //If no item with that title can be loaned
                
                int itemIndex = getSelection();
                
                try{
                    if(itemList.get(itemIndex).isLoan() == true){
                        System.out.println("");
                        System.out.println("\"" + truncate(itemList.get(itemIndex).getTitle(), 37) + "\" is already on loan.");

                        showAlert("Error loaning", "This cannot be loaned", 
                                "\"" + truncate(itemList.get(itemIndex).getTitle(), 33) + "\" is already on loan.\n", "INFORMATION");
                        error = true;
                    }else{
                        System.out.println("");
                        if(loanable > 1){
                            System.out.println("There are "+ loanable + " items with that title that can be loaned.");
                            for(int y = 0; y < loanable; y++){  //Create menu of items that can be loaned
                                showDuplicates += "<" + itemList.get(alreadyLoaned.get(y)).getTitle() + " (" + itemList.get(alreadyLoaned.get(y)).getFormat() + ")> ";
                            }
                            choose = (getMenuOption("Which would you like to loan?", showDuplicates) - 1);
                            choose = alreadyLoaned.get(choose); //Set the choice to the index in the original array
                            choose = 0;
                        }

                        if(error == true){
                            return;
                        }

                        System.out.println("");
                        System.out.print("Please enter the name of borrower: ");
                        //name = input.nextLine();
                        name = loanTo.getText();
                        
                        itemList.get(itemIndex).setLoan(true);
                        itemList.get(itemIndex).setCalendar(loanOn.getText());
                        itemList.get(itemIndex).setName(name);

                        System.out.println("\"" + truncate(loanTo.getText(), 30) + "\" (" + truncate(itemList.get(itemIndex).getFormat(), 30) + ") has been loaned out to " + truncate(name, 31));
                        toast("\"" + truncate(itemList.get(itemIndex).getTitle(), 30) + "\" (" + truncate(itemList.get(itemIndex).getFormat(), 30) + ") has been loaned out to " + truncate(name, 31));
                        
                        if(itemList.get(itemIndex).isLoan() == true){
                            clearLoanBox();
                        }
                        hasSaved = false;
                        
                        return;
                    }
                }catch(Exception exception){
                    showAlert("Error loaning", "You have not selected an item", "Select an item to loan", "WARNING");
                    return;
                }
            }else{
                System.out.println("That title does not exist.");
                
                showAlert("Fatal error", "Item does not exist.", "The item you have selected does not exist. This is bad.", "WARNING");
                return;
            }
            
        }else{
            ArrayList<Integer> canLoan = new ArrayList<Integer>();
            ArrayList<Item> loanables = new ArrayList<Item>();
            
            canLoan = notLoaned();
            for(int p = 0; p < canLoan.size(); p++){    //This creates an ArrayList of all Items that can be loaned
                loanables.add(itemList.get(canLoan.get(p)));
            }
            
            option = getMenuOption("Choose item to put on loan", createMenu(loanables));
            
            try{
                if(loanables.get(option - 1).isLoan() == true){  //This statement should NEVER be triggered
                    System.out.println("\"" + truncate(loanables.get(option - 1).getTitle(), 27) + "\" (" + truncate(loanables.get(option - 1).getFormat(), 27) + ")" + " is already on loan to " + truncate(loanables.get(option - 1).getName(), 31) + ".");
                    return;
                }

                System.out.println("");
                System.out.print("Please enter the name of borrower: ");
                name = input.nextLine();

                loanables.get(option - 1).setCalendar(loanOn.getText());
                loanables.get(option - 1).setLoan(true);
                loanables.get(option - 1).setName(name);

                System.out.println("\"" + truncate(loanables.get(option - 1).getTitle(), 27) + "\" (" + truncate(loanables.get(option - 1).getFormat(), 27) + ")" + " has been successfully loaned to " + truncate(loanables.get(option - 1).getName(), 27) + ".");
                hasSaved = false;
                
                return;
            }catch(ArrayIndexOutOfBoundsException noItems){ //If there are no items
                System.out.println("You do not have any items in your possession.");
            }
        }
        
        hasSaved = false;
        System.out.println("loanItem complete");
        
        return;
    }
    
    public static void returnItem(){ //This method is nearly identical to the loanItem method, except the checks are inverted
        System.out.println("returnItem started");
        
        int option;
        String title;
        String showDuplicates = "";
        Scanner input = new Scanner(System.in);
        ArrayList<Integer> duplicates = new ArrayList<Integer>();
        ArrayList<Integer> alreadyLoaned = new ArrayList<Integer>();
        int loaned = 1;
        int loanable = 0;
        int choose = 0;
        String name;
        
        /*if(getMenuOption("How would you like to return the item?", "<Type the title> <Choose from list>") == 1){
            System.out.print("Enter the title of media here: ");
            //title = input.nextLine();
            if(titleExists(title)){
                duplicates = findTitle(title);
                for(int j = 0; j < duplicates.size(); j++){
                    if(itemList.get(duplicates.get(j)).isLoan() == true){
                        loaned = 0;
                        loanable++;
                        alreadyLoaned.add(duplicates.get(j));
                        choose = j;
                    }
                }
                if(loaned == 1){
                    System.out.println("");
                    System.out.println("\"" + truncate(title, 37) + "\" is already on loan.");
                    return;
                }
                if(loaned == 0){
                    System.out.println("");
                    if(loanable > 1){
                        System.out.println("There are "+ loanable + " items with that title that can be loaned.");
                        for(int y = 0; y < loanable; y++){
                            showDuplicates += "<" + itemList.get(alreadyLoaned.get(y)).getTitle() + " (" + itemList.get(alreadyLoaned.get(y)).getFormat() + ")> ";
                        }
                        choose = (getMenuOption("Which would you like to loan?", showDuplicates) - 1);
                        choose = alreadyLoaned.get(choose); //Set the choice to the index in the original array
                    }
                    
                    itemList.get(choose).setLoan(false);
                    
                    System.out.println("\"" + truncate(title, 33) + "\" (" + truncate(itemList.get(choose).getFormat(), 33) + ") has been returned by" + truncate(itemList.get(choose).getName() + ".", 33));
                    return;
                }
            }else{
                System.out.println("That title does not exist.");
                return;
            }
        }else{
            ArrayList<Integer> canReturn = new ArrayList<Integer>();
            ArrayList<Item> loanables = new ArrayList<Item>();
            
            canReturn = alreadyLoaned();
            for(int p = 0; p < canReturn.size(); p++){    //This creates an ArrayList of all Items that can be returned
                loanables.add(itemList.get(canReturn.get(p)));
            }
            
            option = getMenuOption("Choose item to return", createMenu(loanables));
            
            try{
                if(loanables.get(option - 1).isLoan() == false){  //This statement should NEVER be triggered
                    System.out.println("\"" + truncate(loanables.get(option - 1).getTitle(), 30) + "\" (" + truncate(loanables.get(option - 1).getFormat(), 30) + ")" + " is not on loan " + truncate(loanables.get(option - 1).getName(), 25) + ".");
                    return;
                }


                loanables.get(option - 1).setLoan(false);

                System.out.println("\"" + truncate(loanables.get(option - 1).getTitle(), 27) + "\" (" + truncate(loanables.get(option - 1).getFormat(), 37) + ")" + " has been successfully returned by " + truncate(loanables.get(option - 1).getName(), 23) + ".");
                return;
            }catch(ArrayIndexOutOfBoundsException noItems){ //If there are no items to be returned
                System.out.println("There are no items on loan.");
            }
        }*/
        
        int itemIndex = getSelection();
        
        try{
        if(itemList.get(itemIndex).isLoan() == false){
            showAlert("Cannot return item", "This item is not on loan.", "Select a loaned item to return.", "WARNING");
            return;
        }
        }catch(Exception exception){
            showAlert("Error loaning", "You have not selected an item", "Click an item to remove", "WARNING");
            return;
        }
        
        try{
            title = itemList.get(itemIndex).getTitle();
            name = itemList.get(itemIndex).getName();
            
            itemList.get(itemIndex).setLoan(false);
        }catch(Exception exception){
            showAlert("Error loaning", "You have not selected an item", "Click an item to remove", "WARNING");
            return;
        }
        
        toast("\"" + truncate(title, 43) + "\" has been returned by " + name + ".");
        
        hasSaved = false;
        System.out.println("returnItem complete");
        
        return;
    }
    
    public static void listItems(){ //Finally, a simply, easy method
        System.out.println("listItems started");
        
        if(itemList.isEmpty()){
            System.out.println("");
            System.out.println("Nothing here to see, move along.");
            return;
        }
        
        System.out.println("Items:");
        
        for(int o = 0; o < itemList.size(); o++){
            System.out.println("");
            System.out.print(itemList.get(o).toString());
            
            //Dynamically adjust how long to wait between each item before displaying the next. The larger the ArrayList, the shorter the wait between each item, but the longer the overall wait.
            //wait((int)((1000 * Math.sqrt(itemList.size()))/(Math.pow(itemList.size(), 1.11))));
        }
        Scanner wait = new Scanner(System.in);
        System.out.println("");
        pause();
        
        System.out.println("listItems complete");
    }
    
    public static void removeItem(){
        System.out.println("removeItem started");
        
        Scanner input = new Scanner(System.in);
        String title;
        
        /*
        int option;
        option = getMenuOption("Choose method to remove item", "<Type in title manually> <Choose from item list>");
        
        if(option == 1){
            System.out.print("Please type item title: ");
            title = input.nextLine();
            
            if(titleExists(title) == false){
                System.out.println("\"" + title + "\" does not exist.");
                return;
            }
            
            ArrayList<Integer> thoseItems = new ArrayList<Integer>();
            thoseItems = findTitle(title);    //Create arraylist with indexes of all items that can be removed
            
            if(thoseItems.size() == 1){
                itemList.remove(thoseItems.get(0));
                return;
            }
            
            String menu = "<All of them> ";
            for(int d = 0; d < thoseItems.size(); d++){
                menu += "<" + truncate(itemList.get(thoseItems.get(d)).getTitle(), 33) + " (" + truncate(itemList.get(thoseItems.get(d)).getFormat(), 33) + ")" + "> ";
            }
            
            option = getMenuOption("Which item would you like to delete?", menu);
            
            if(option == 1){    //If deleting all items with that title
                int u;
                int numLoans = 0;
                for(u = thoseItems.size(); u > 0; u--){ //You can't start with index 0 because the ArrayList keeps getting smaller
                    if(itemList.get(thoseItems.get(u - 1)).isLoan() == true){
                        numLoans++;
                    }
                    itemList.remove((int)(thoseItems.get(u - 1)));
                }
                System.out.println(thoseItems.size() + " items eradicated.");
                if(numLoans > 0){
                    System.out.println(numLoans + " of them were on loan.");
                }
                return;
            }
            
            System.out.println("\"" + truncate(itemList.get(thoseItems.get(option - 2)).getTitle(), 33) + "\" (" + truncate(itemList.get(thoseItems.get(option - 2)).getFormat(), 33) + ")" + " has been vaporized.");
            itemList.remove((int)(thoseItems.get(option - 2)));
            return;
        }else{
            String menu = "";
            int g;
            for(g = 0; g < itemList.size(); g++){
                menu += itemList.get(g).getMenuString() + " ";
            }
            option = getMenuOption("Which item would you like to delete?", menu);
            
            try{
                System.out.println("\"" + truncate(itemList.get(option - 1).getTitle(), 33) + "\" (" + truncate(itemList.get(option - 1).getFormat(), 33) + ") has been erased.");
                if(itemList.get(option - 1).isLoan() == true){
                    System.out.println("It was on loan, and now belongs to someone else.");
                }
                itemList.remove((option - 1));
            }catch(ArrayIndexOutOfBoundsException noItems){
                System.out.println("You do not have any items to begin with.");
            }
        }*/
        int wasOnLoan = 0;
        String loaner = "";  String itemTitle;
        int index = -1;
        
        try{
            index = getSelection();
            
            if(itemList.get(index).isLoan() == true){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                Toolkit.getDefaultToolkit().beep();
                alert.setTitle("Confirm removal");
                alert.setContentText(truncate(itemList.get(index).getTitle(), 31) + " is on loan." +
                        "\nWould you like to remove the item anyways?");
                Optional<ButtonType> result = alert.showAndWait();	//This blocks further code execution until dialog is closed
                if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
                    return;
                }
                
                loaner = itemList.get(index).getName();
                wasOnLoan = 1;
            }
            
            itemTitle = itemList.get(index).getTitle();
            listedItems.remove(itemList.get(index));
            //itemList.remove(index);   //This doesn't work
            
            if(wasOnLoan == 0){
                toast(truncate(itemTitle, 39)
                        + " has been obliterated.");
            }else{
                toast(truncate(itemTitle, 37)
                        + " is now permanently owned by " + loaner + ".");
            }
        }catch(Exception notSelected){
            showAlert("Cannot remove item.", "You have not selected anything", "Please select an item to remove.", "WARNING");
            return;
        }
        
        hasSaved = false;
        System.out.println("removeItem complete");
        
        return;
    }
    
    public static void exit(){
        System.out.println("exit started");
        
        Scanner input = new Scanner(System.in);
        String collection = "";
        String index = "";  String eachIndex;
        PrintWriter createSave = null;
        PrintWriter createPointer = null;
        
        collection = verifyString("Save As (enter collection title): ");


        File saveFile = new File(collection + ".aash");
        File database = new File("collections.bhar");

        try{    //Store database file to string to append to later
                Scanner indexer = new Scanner(database);
                while(indexer.hasNextLine()){   //Store previous collection names
                    eachIndex = indexer.nextLine();
                    if(eachIndex.trim().isEmpty() == false && !eachIndex.equals(collection)){ //Only if line isn't blank
                        index += eachIndex + "\n";
                    }
                }
            
            createPointer = new PrintWriter(database);
            createPointer.println(index + collection);  //Append previous collection names
            
            createPointer.close();
        }catch(FileNotFoundException somethingHappened){
            System.out.println("");
            System.out.println("This collection database could not be accessed.");
            System.out.println("The database file might be in use by another program.");
            
            if(getMenuOption("How would you like to proceed?", "<Try again> <Quit (and lose all data)>") == 1){
                exit();
                return;
            }else{
                leaveProgram();
            }
        }

        try{
            createSave = new PrintWriter(saveFile);
            for(int l = 0; l < itemList.size(); l++){
                createSave.println(itemList.get(l).saveString());
        }

            createSave.close();
        }catch(FileNotFoundException whatHappened){
            System.out.println("");
            System.out.println("The file could not be saved.");
            System.out.println("The file is not found.");
            System.out.println("Try again.");
            
            showAlert("Can't save", "There was an error saving", "The file to save was not found", "WARNING");
            
            exit();
            return;
        }
        
        leaveProgram();
        
        System.out.println("exit complete");
    }
    
    public static ArrayList<Integer> alreadyLoaned(){
        System.out.println("alreadyLoaned started");
        
        ArrayList<Integer> loaners = new ArrayList<Integer>();
        
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).isLoan() == true){
                loaners.add(i);
            }
        }
        
        System.out.println("alreadyLoaned complete");
        return loaners;
    }
    
    public static ArrayList<Integer> notLoaned(){
        System.out.println("notLoaned started");
        
        ArrayList<Integer> keepers = new ArrayList<Integer>();
        
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).isLoan() == false){
                keepers.add(i);
            }
        }
        
        System.out.println("notLoaned complete");
        return keepers;
    }
    
    public static String createMenu(ArrayList<Item> loanables){
        System.out.println("createMenu started");
        
        String menuString = "";
        
        for(int i = 0; i < loanables.size(); i++){
            menuString += loanables.get(i).getMenuString() + " ";
        }
        
        System.out.println("createMenu complete");
        return menuString;
    }
    
    
    public static int getMenuOption(String title, String options){  //Displays a menu based on menu options given from another method
        System.out.println("getMenuOption started");
        
/*
        NOTICE: This universal menu method was made solely and entirely by Aashish Bharadwaj.
        The method is explained in overly verbose detail with comments on every line.
        
    This method displays a menu based on the strings you send it.
    You must send it a title string that will act as the menu title, followed by the menu options you want.
    For example, call it with 'getMenuOption(titleOfMenu, menuOptionList)', with titleOfMenu being the header and 'menuOptionList' being the items.
    
    import java.awt.Toolkit;
    import java.util.Scanner;
    
    
    The options must have a specific format:
    
    1. Each item is to be separated by a space.
    2. You can have menu item with spaces inside them by wrapping the entire item in '<' and '>'.
        a. For example, "<This is one item>".
        b. Anything after a "<" will be displayed as one item until a ">" is found.
        c. If a "<" is used, but no ending ">" is found, then the "<" will be displayed as a normal character, and whatever is after it will be treated as one option.
    3. You can still have a '<' displayed in your menu, but you must use two.
        a. For example, "<<Menu>" will actually display "<Menu>".
    4. Each menu item will be assigned a value, starting with 1.
        a. The user may input an integer, and that integer will be returned as the return value of this method.
            I. If the user inputs an integer out of range, method will notify user and ask for correction.
            II. If the user inputs a non-integer, the method will notify the user and ask for correction.

*/
        
        if(options.trim().isEmpty() == true){
            return 0;
        }
        if(title.trim().isEmpty() == true){
            title = "Menu";
        }
        
        
        int spaceLocation; //Where all the spaces in the menu list are.
        spaceLocation = -1; //The starting position is -1 because this value is incremented immediately to 0.
        
        int w;  //Loop control variable, that is compared to the number of options.
        w = 0;  //Loop control starts at 0.
        
        boolean insertBracket;  //Variable to know whether menu item contains one '<' denoted by '<<'
        insertBracket = false;  //Default set knowledge of existence of '<<' to false
        
        Scanner choose;    //Scans user input. You must import java.util.Scanner in package for this to work.
        choose = new Scanner(System.in);    //Sets the scanner to scan console input.
        
        String choice;  //What the user input is
        choice = "";    //Start user input as blank
        
        choice = "1";
        
        System.out.println(""); System.out.println(""); //Double space cushion
        System.out.println(title);  //Display title of menu
        
        while(true){  //Until all choices are exhausted, keep looping
            w++;    //To control the loop, keep track of how many choices have been displayed
            
            System.out.println("");
            try{ //In most cases, where there is an option after the current one
                
                if(options.substring(spaceLocation + 1, spaceLocation + 3).equals("<")){   //If the menu options string has '<<', then we treat it as one '<' rather than as a 'start phrase' character.
                    spaceLocation++;    //Add one to the location where the next menu item is started, so that '<<' turns into '<'.
                    insertBracket = true;   //Tell the program that we are inserting a '<', and not treating it as a phrase starter.
                }
                if(insertBracket == false && options.substring(spaceLocation + 1, spaceLocation + 2).equals("<")){   //If the first character of the next option is a '<', then it is an option with a phrase including spaces. However, if there is '<<', then ignore.
                    
                    try{    //Try to find the next '>'. This try exists to circumvent and avoid the other catch with variable noMoreMenu that occurs later on
                        System.out.print(w + ". " + options.substring(spaceLocation + 2, options.indexOf(">", spaceLocation + 2)));   //Print out the menu option with the spaces until a '>' is found.
                        spaceLocation = options.indexOf(">", spaceLocation + 2);    //Set the new space location to immediately after '>'
                    }catch(StringIndexOutOfBoundsException noClosingBracket){   //If no '>' closing bracket exists
                        System.out.print(w + ". "); //Display the item number, because otherwise this step would have been skipped over
                    }
                    while(!options.substring(spaceLocation + 1, spaceLocation + 2).equals(" ")){    //If immediately after the '>' is not a space, keep going until a space is found
                        System.out.print(options.substring(spaceLocation + 1, spaceLocation + 2));  //Print whatever is after the first '>'.
                        spaceLocation++;    //Keep adding one to where we search for a space
                    }
                    
                    if(spaceLocation + 2 > options.length()){   //If the hypothetical next space doesn't exist because it's outside the string's length
                        break;  //Exit this loop checking for new menu options
                    }
                }else{  //If there is not a '<'
                    System.out.print(w + ". " + options.substring(spaceLocation + 1, options.indexOf(" ", spaceLocation + 1)));   //Get a substring between one character after the previous space and the next space
                }
                insertBracket = false;  //Reset the '<<' indicator
            }catch(StringIndexOutOfBoundsException noMoreMenu){  //When there are no options after the current one, this is necessary because there are no spaces to check for
                
                if(options.substring(spaceLocation + 1).length() < 1){  //If there is no menu string remaining
                    break;  //Leave the loop to prevent an extra duplicate blank option being added
                }
                
                System.out.print(w + ". " + options.substring(spaceLocation + 1));    //Get the word between one character after previous space and the end of the string
                break;  //If string is over, exit loop that checks for new options
            }
            
            spaceLocation = options.indexOf(" ", spaceLocation + 1);    //Change the location of the space to the next space in the string
        }
        
        System.out.println(""); System.out.println(""); //Add double space cushion
        System.out.print("My choice is: ");   //Ask for user input
        
        //choice = choose.nextLine(); //Get user input
        
        int userChoice; //User choice in integer form, for return value.
        userChoice = 0; //Sets user input to default 0.
        
        try{    //Try converting user input string to integer
            userChoice = Integer.parseInt(choice);   //See if user input is an integer
            
        }catch(NumberFormatException notInteger){   //Catch error for not an integer
            System.out.println(""); //Space cushion
            
            if(choice.trim().isEmpty() == false){   //If user input string is not empty
                System.out.println("\"" + truncate(choice, 22) + "\"" + " is not an integer."); //Tell the user that what they have input is not input a valid integer
            }
            System.out.println("Please input an integer between 1 and " + w + ".");   //Tells the user to input a value between 1 and upper bound
            
            System.out.println(""); //Adds a blank line before instructions
            Toolkit.getDefaultToolkit().beep(); //Beeps
            
            if(choice.trim().isEmpty() == false){   //If user input string is not empty
                pause();  //Instructs user to press Enter to view menu again
            }
            
            userChoice = getMenuOption(title, options); //Restart method to retry user input, while obtaining the new userChoice in the new instance of the method and passing it on to this old instance
            return userChoice;  //Prevent the old instance continuing because we started a new one in the previous line
        }
        
        if(userChoice < 1 || userChoice > w){ //If user input is not in range
                System.out.println(""); //Spacing
                Toolkit.getDefaultToolkit().beep();   //Beep
                System.out.println(userChoice + " is not a valid option."); //Restate the user input and say that it is invalid
                System.out.print("The value you input is ");    //Starts a sentence

                if(userChoice < 1){ //If input is too low
                    System.out.print("too low");    //Tell the user input is too low
                }else if(userChoice > w){  //If input is too high
                    System.out.print("too high");   //Tell the user input is too high
                }else{  //Ilf user input is neither too high nor too low, which shouldn't happen
                    System.out.println("Error, warning, alert, caution. We shouldn't be here"); //Tell the user an error has occurred.
                    System.out.println("The program thinks that user input is invalid and valid simultaneously.");  //Tell the user what the error is.
                    System.out.println("Time to crash and burn...");    //Tell the user that we are going to exit.

                    System.exit(0);    //Exit the program
                }
                System.out.println(".");    //Add a period to the end of the sentence
                System.out.println("Please input an integer between 1 and " + w + ".");   //Tells the range to input for the user
                
                System.out.println(""); //Adds a blank line before instructions
                pause();  //Instructs user to press Enter to view menu again

                userChoice = getMenuOption(title, options); //Restart the method, but ensure that after the method call is completed, that userChoice gets changed to the new value
                return userChoice;  //Stops the method from continuing after the above method call is completed
            }
        
        System.out.println(""); //Another space cushion
        
        System.out.println("getMenuOption complete");
        return userChoice;  //Return the integer of the user choice
    }
    
    public static int getSelection(){
        try{
            return items.getSelectionModel().getSelectedIndex();
        }catch(Exception nothingSelected){
            showAlert("Selection error", "Something is wrong.", "The program is currently undergoing issues, apparently.", "INFORMATION");
            return -1;
        }
    }
    
    public static String verifyString(String prompt){
        System.out.println("verifyString started");
        
        Scanner input = new Scanner(System.in);
        String entry;
        
        while(true){
            System.out.println("");
            System.out.print(prompt);
            //entry = input.nextLine();
            entry = "";
            
            if(entry.trim().isEmpty()){
                System.out.println("");
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Please type something.");
            }else{
                break;
            }
        }
        
        System.out.println("verifyString complete");
        return entry;
    }
    
    public static int verifyInt(String entry){
        System.out.println("verifyInt started");
        
        Scanner input = new Scanner(System.in);
        int number;
        
        while(true){
            System.out.println("");
            System.out.print("Enter integer: ");
            //entry = input.nextLine();

            
            if(entry.trim().isEmpty()){
                System.out.println("");
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Please enter an integer.");
            }else{
                try{
                    number = Integer.parseInt(entry);
                    break;
                }catch(NumberFormatException notInteger){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("\"" + truncate(entry, 37) + "\" is not an integer. Try again.");
                }
            }
        }
        
        System.out.println("verifyInt complete");
        return number;
    }
    
    public static void leaveProgram(){
        System.out.println("leaveProgram started");
        
        try{
            PrintWriter alreadyRun = new PrintWriter(new File("collections.bhar"));
            alreadyRun.print("1");
            alreadyRun.close();
        }catch(Exception exception){
            System.out.println("Will reshow disclaimer next time.");
        }
        
        System.out.println(""); System.out.println(""); System.out.println("");
        
        System.out.println("BBBBBBB \t  Y         Y \t EEEEEEEEEEEE");
        System.out.println("B      B \t   Y       Y\t E");
        System.out.println("B        B \t    Y     Y\t E");
        System.out.println("B         B \t     Y   Y\t E");
        System.out.println("B          B \t      Y Y \t E");
        System.out.println("B         B \t       Y \t E");
        System.out.println("B        B \t       Y \t E");
        System.out.println("B      B \t       Y \t E");
        Toolkit.getDefaultToolkit().beep();
        System.out.println("BBBBBB \t               Y \t EEEEEEEEEEEE");
        System.out.println("B      B \t       Y \t E");
        System.out.println("B        B \t       Y \t E");
        System.out.println("B         B \t       Y \t E");
        System.out.println("B          B \t       Y \t E");
        System.out.println("B         B \t       Y \t E");
        System.out.println("B        B \t       Y \t E");
        System.out.println("B      B \t       Y \t E");
        System.out.println("BBBBBBBB \t       Y \t EEEEEEEEEEEE");
        
        System.out.println(""); System.out.println(""); System.out.println("");
        System.exit(0);
        
        System.out.println("leaveProgram complete");
    }
    
    public static void saveFile(){
        System.out.println("saveFile started");
        
        FileChooser saveIt = new FileChooser();
        saveIt.setTitle("Chose where to save you file");
        saveIt.getExtensionFilters().add(new ExtensionFilter("Media Collection", "*.aash"));
        
        File savedItems = saveIt.showSaveDialog(new Stage());
        
        PrintWriter createSave;
        try{    //Save the items
            createSave = new PrintWriter(savedItems);
            for(int l = 0; l < itemList.size(); l++){
                createSave.println(itemList.get(l).saveString());
        }
            createSave.close();
            hasSaved = true;
        }catch(FileNotFoundException whatHappened){
            System.out.println("");
            System.out.println("The file could not be saved.");
            System.out.println("The file could not be found.");
            
            showAlert("Can't save", "There was an error saving.", "The file to save was not found", "WARNIING");
            
            return;
        }catch(NullPointerException hitCancel){}
        
        try{
            toast(itemList.size() + 
                    " items have been saved to " + 
                    truncate(savedItems.getCanonicalPath(), 33));
        }catch(Exception exception){}
        
        System.out.println("saveFile complete");
        
        return;
    }
    
    public static void pause(){
        System.out.println("pause started");
        
        Scanner pause = new Scanner(System.in);
        
        System.out.println("");
        System.out.print("Press ENTER to continue...");
        System.out.println("");
        //pause.nextLine();
        
        System.out.println("pause complete");
    }
    
    public static void wait(int time){
        System.out.println("wait started");
        
        try{
            sleep(time);
        }catch(InterruptedException interrupted){}
        
        System.out.println("wait complete");
    }
    
    @Override
    public void start(Stage mainUI){
        System.out.println("start started");
        
        startingCode();
        
        createMainWindow()  ;      
        mainUI.setScene(mainWindow);
        
        mainUI.setMinWidth(597);
        mainUI.setMinHeight(555);
        
        mainUI.setAlwaysOnTop(false);
        mainUI.setFullScreen(false);
        mainUI.setFullScreenExitHint("Press ESC to escape this madness.");
        
        mainUI.setTitle("Aashishkebab's Media Organizer");
        
        mainUI.setOnCloseRequest(maybeSave -> {
            promptSaving();
        });
        
        mainUI.show();
        
        System.out.println("start complete");
    }
    
    public static void createMainWindow(){
        System.out.println("createMainWindow started");
        
        wholeWindow.getChildren().add(padding);
            padding.setMaxHeight(3);
            padding.setMinHeight(3);
            
        wholeWindow.getChildren().add(entireWindow);
        
        entireWindow.getChildren().add(leftMargin);
            
        entireWindow.getChildren().add(itemDisplay);
            itemDisplay.getChildren().add(numberOfItems);
            
            try{
                listedItems = FXCollections.observableList(itemList);
                items.setItems(listedItems);
                itemDisplay.getChildren().add(items);
            }catch(Exception ohnoes){
                showAlert("Yikes!", "There has been a fatal error!", "We were unable to allocate space for the items due to some idjit programmer.", "INFORMATION");
            }
            
            items.setPrefSize(666, 555);
            items.setMinWidth(234);
            
            itemDisplay.getChildren().add(generator);
            
        entireWindow.getChildren().add(userEntry);
        
            userEntry.setMinWidth(246);
            
            userEntry.getChildren().add(alignment);
            
            userEntry.getChildren().add(addBox);
                addBox.setStyle("-fx-border-style:solid inside; -fx-padding: 11px");
                addBox.setMaxWidth(237);

                addBox.add(titleLabel, 1, 1);
                addBox.add(formatLabel, 1, 2);
                addBox.add(addButton, 1, 3);

                addBox.add(titleText, 2, 1);
                addBox.add(formatText, 2, 2);

            userEntry.getChildren().add(removeButton);
            userEntry.getChildren().add(returnButton);


            userEntry.getChildren().add(loanBox);
                loanBox.setStyle("-fx-border-style:solid inside; -fx-padding: 10px");
                loanBox.setMaxWidth(253);

                loanBox.add(loanToLabel, 1, 1);
                loanBox.add(loanOnLabel, 1, 2);

                loanBox.add(loanTo, 2, 1);
                loanBox.add(loanOn, 2, 2);

                loanOn.setPromptText("M/d/Y");

                loanBox.add(loan, 1, 3);

            userEntry.getChildren().add(sorter);

                sorter.getChildren().add(sortWord);

                sorter.getChildren().add(byTitle);
                sorter.getChildren().add(byDate);

                byTitle.setToggleGroup(sortType);
                byDate.setToggleGroup(sortType);

                sortType.selectToggle(byTitle);
                
            userEntry.getChildren().add(someSpace);
                someSpace.setMinHeight(11);
                someSpace.setMaxHeight(17);
                
            userEntry.getChildren().add(fileIO);
                fileIO.getChildren().add(saveStuff);
                fileIO.getChildren().add(openStuff);
            
            entireWindow.getChildren().add(cushion);
                cushion.setMinWidth(17);
                cushion.setPrefWidth(17);
                cushion.setMaxWidth(17);
                
            wholeWindow.getChildren().add(status);
            
        buttonActions();
        
        System.out.println("createMainWindow complete");
    }
    
    public static void buttonActions(){
        System.out.println("buttonActions started");
        
        addButton.setOnAction(add -> {
            insertItem();
            updateList();
        });
        
        byTitle.setOnAction(sortTitle ->{
            compareTitles();
            updateList();
        });
        byDate.setOnAction(sortDate ->{
            compareDates();
            updateList();
        });
        
        loan.setOnAction(loanOut ->{
            loanItem();
            updateList();
        });
        
        returnButton.setOnAction(returnItem ->{
            returnItem();
            updateList();
        });
        removeButton.setOnAction(removeItem ->{
            removeItem();
            updateList();
        });
        
        generator.setOnAction(generateItems ->{
            randomizeCollection();
            updateList();
            
        });
        
        saveStuff.setOnAction(writeFile -> {
            saveFile();
            updateList();
        });
        
        openStuff.setOnAction(fillList -> {
            importCollection();
            updateList();
        });
        
        System.out.println("buttonActions complete");
        return;
    }
    
    public static void clearLoanBox(){
        System.out.println("clearLoanBox started");
        
        loanTo.clear();
        loanOn.clear();
        
        System.out.println("clearLoanBox complete");
    }
    
    public static void clearAddBox(){
        System.out.println("clearAddBox started");
        
        titleText.clear();
        formatText.clear();
        
        System.out.println("clearAddBox complete");
    }
    
    public static void startingCode(){
        System.out.println("startingCode started");
        
        wait.getButtonTypes().setAll(doneWaiting);
        wait.setTitle("Please wait...");
        wait.setHeaderText("Please wait...");
        //wait.setContentText("Please wait...");
        
        Scanner continuing = new Scanner(System.in);
        
        initializeFiles();
        
        int option;
        while(true){
            if(true){
                break;
            }
            
            try{    //This code preventes skipping too far ahead
                System.out.println("");
                System.out.println("Please wait...");
                sleep(555);
            }catch(InterruptedException interprupt){}
            
            try{
                Collections.sort(itemList);
            }catch(Exception sorter){}
                        
            option = getMenuOption("Kebab Media Management", "<Insert a new media item> <Mark an item as on loan> <Mark an item as returned> <List the items currently in the collection> <Remove a media item> <Save and quit> <Generate Collection  <-----This creates a bunch of items for you, useful for testing> <Search for item>");
            if(option == 1){
                insertItem();
            }else if(option == 2){
                loanItem();
            }else if(option == 3){
                returnItem();
            }else if(option == 4){
                listItems();
            }else if(option == 5){
                removeItem();
            }else if(option == 6){
                exit();
            }else if(option == 7){
                randomizeCollection();
            }else if (option == 8){
                searchItem();
            }else{  //This shouldn't happen
                System.out.println("Oh no!");
                System.exit(0);
            }
        }
        updateList();
        
        System.out.println("startingCode complete");
        return;
    }
    
    public static void updateList(){
        System.out.println("updateList started");
        
        numberOfItems.setText("There are " + itemList.size() + " items. " + loanCount() + " of them are on loan.");
        
        if(itemList.size() == 0){
            System.out.println("There are no items to display.");
            wait.close();
            return;
        }
        
        //listItems();
        
        resortList();
        
        try{
            listedItems = FXCollections.observableList(itemList);
            //listedItems.setAll(itemList);
            items.setItems(listedItems);
        }catch(Exception exception){
            showAlert("This is not good", "Error updating content", "There are no items or the items are corrupted or JavaFx is stupid.", "INFORMATION");
        }
        
        /*Button closeDialog = (Button)wait.getDialogPane().lookupButton(doneWaiting);
        closeDialog.fire();*/
        
        wait.close();
        
        System.out.println("updateList complete");
        return;
    }
    
    public static void resortList(){
        System.out.println("resortList started");
        
        if(itemList.size() == 0){
            System.out.println("There are no items to sort.");
            return;
        }
        try{
            String comparator = itemList.get(0).compareType;
            for(int i = 0; i < itemList.size(); i++){
                itemList.get(i).setCompareType(comparator);
            }

            Collections.sort(itemList);
        }catch(Exception exception){
            showAlert("This is not good", "Error sorting media", "There are no items or the items are corrupted or JavaFx stupid.", "INFORMATION");
        }
        
        System.out.println("resortList complete");
        return;
    }
    
    public static String getOS(){        
        return System.getProperty("os.name");
    }
    
    public static void toast(String message){
        System.out.println("toast started");
        
        status.setText(" " + truncate(message, 77));
        
        System.out.println("toast complete");
    }
    
    public static void showAlert(String title, String header, String content, String alertType){
        System.out.println("showAlert started");
        
        Alert alert;
        if(alertType == "INFORMATION"){
            alert = new Alert(AlertType.INFORMATION);
        }else if(alertType == "WARNING"){
            alert = new Alert(AlertType.WARNING);
        }else{
            return;
        }
        
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Toolkit.getDefaultToolkit().beep();
        
        alert.setResizable(true);
        alert.show();
        
        System.out.println("showAlert complete");
    }
    
    public static void showAlertWait(String title, String header, String content, String alertType){
        System.out.println("showAlert started");
        
        Alert alert;
        if(alertType == "INFORMATION"){
            alert = new Alert(AlertType.INFORMATION);
        }else if(alertType == "WARNING"){
            alert = new Alert(AlertType.WARNING);
        }else{
            return;
        }
        
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Toolkit.getDefaultToolkit().beep();
        
        alert.setResizable(true);
        alert.showAndWait();
        
        System.out.println("showAlert complete");
    }
    
    public static String promptForText(String title, String header, String content){
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        //dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }else{
            return null;
        }
    }
    
    public static void promptSaving(){
        if(hasSaved == false && itemList.size() > 0){
            Toolkit.getDefaultToolkit().beep();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Collection not yet saved.");
            alert.setHeaderText("Would you like to save changes?");
            alert.setContentText(null);

            ButtonType saveIt = new ButtonType("Save collection");
            ButtonType discardIt = new ButtonType("Discard changes");

            alert.getButtonTypes().setAll(saveIt, discardIt);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == saveIt){
                saveFile();
                leaveProgram();
            }else if(result.get() == discardIt){
                System.out.println("All changes lost...");
                leaveProgram();
            }else{
                showAlert("Crap", null, "You have pressed an invalid button...", "INFORMATION");
            }
        }else{
            leaveProgram();
        }
    }
}
