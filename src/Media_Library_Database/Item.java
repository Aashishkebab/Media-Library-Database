package Media_Library_Database;

import static java.lang.Integer.parseInt;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.awt.Toolkit;
import static Media_Library_Database.MediaLibraryDatabase.truncate;
import static Media_Library_Database.MediaLibraryDatabase.verifyInt;
import static Media_Library_Database.MediaLibraryDatabase.showAlert;
import static Media_Library_Database.MediaLibraryDatabase.status;
import static Media_Library_Database.MediaLibraryDatabase.titleLabel;
import static Media_Library_Database.MediaLibraryDatabase.toast;

/**
 *
 * @author aashi
 */
public class Item implements Comparable<Item>{
    
    private String title;
    private String format;
    private boolean loan;
    private String date;
    private String name;
    String compareType = "title";
    
    public Item(String title, String format, boolean loan, String date, String name){
        this.title = title;
        this.format = format;
        this.loan = loan;
        this.date = date;
        this.name = name;
        this.compareType = "title";
    }
    
    public void setCompareType(String comparor){
        this.compareType = comparor;
    }
    
    @Override
    public String toString(){
        String returner;
        
        returner = truncate(this.title, 33) + " - " + truncate(this.format, 13);
        if(this.loan == true){
            returner += " (" + truncate(this.name, 29) + " on " + truncate(this.date, 17) + ")";
        }
        return truncate(returner, 83);
    }
    
    public String saveString(){
        return this.title + "," + this.format + "," + getLoan() + "," + this.date + "," + this.name;
    }
    
    public String getMenuString(){
        String string = "";
        string += "<" + truncate(this.title, 35) + " (" + truncate(this.format, 35) + ")" +">";
        return string;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getFormat(){
        return this.format;
    }
    
    public boolean isLoan(){
        return this.loan;
    }
    
    public void addTitle(String title){
        this.title = title;
    }
    
    public void addFormat(String format){
        
    }
    
    public void setLoan(boolean loan){
        this.loan = loan;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){    //Only returns name if item is on loan
        if(this.loan == true){
            return this.name;
        }else{
            return null;
        }
    }
    
    public void setCalendar(String date){
        int year = 0;   int month = 0;  int day = 0;
        String error = "";
        String[] dateArray;
        
        try{
            dateArray = date.split("/");
            if(dateArray.length < 3){
                showAlert("Incorrect information entered", "Incorrect date format", "You have not entered enough information", "WARNING");
                this.loan = false;
                return;
            }
            if(dateArray.length > 3){
                showAlert("Incorrect information entered", "Incorrect date format", "You have entered too much information", "WARNING");
                this.loan = false;
                return;
            }
        }catch(Exception exception){
            showAlert("Incorrect information entered", "Incorrect date format", "There must be slashes in your date.\n" +
                    "You must enter \"Month/Day/Year\n\n" +
                    "Example: 3/9/2017", "WARNING");
            this.loan = false;
            return;
        }
        
        try{
            month = Integer.parseInt(dateArray[0]);
            
            if(month < 1){
                error += "Month must be greater than 1. \"" + month + "\" is too small.\n";
            }
            if(month > 12){
                error += "Month must be less than 12. \"" + month + "\" is too big.\n";
            }
        }catch(Exception exception){
            error += "Month must be a whole number.\n";
        }
        
        try{
            year = Integer.parseInt(dateArray[2]);
        }catch(Exception exception){
            error += "Year must be a whole number.\n";
        }
        
        try{
            day = Integer.parseInt(dateArray[1]);
            
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                if(day < 1 || day > 31){
                    error += "Day must be between 1 and 31";
                }
            }
            if(month == 4 || month == 6 || month == 9 || month == 11){
                if(day < 1 || day > 30){
                    error += "Day must be between 1 and 30 for that month.";
                }
            }
            if(month == 2){ //February needs to take into account leap years, which repeat every 4 years, get excluded every 100 years, but the exclusion is not in effect every 400 years
                if(year % 4 == 0){
                    if(year % 100 == 0 && year % 400 == 0){
                        if(day < 1 || day > 29){
                            error += "Day must be between 1 and 29 for February " + year + ".";
                        }
                    }else if(year % 100 == 0 && year % 400 != 0){
                        if(day < 1 || day > 28){
                            error += "Day must be between 1 and 28 for February " + year + ".";
                        }
                    }else{
                        if(day < 1 || day > 29){
                            error += "Day must be between 1 and 29 for February " + year + ".";
                        }
                    }
                }else{
                    if(day < 1 || day > 28){
                        error += "Enter a day between 1 and 28 for February " + year + ".";
                    }
                }
            }
        }catch(Exception nope){
            error += "Day must be a whole number.";
        }
        
        if(error.trim().isEmpty() == false){
            showAlert("Wrong date format", "The following is wrong about your date entry:", error, "INFORMATION");
            this.loan = false;
            return;
        }
        
        try{
            this.date = month + "/" + day + "/" + year;
        }catch(Exception exception){
            showAlert("Error", "Something happened", "There was an error setting the date...", "INFORMATION");
            this.loan = false;
        }
    }
    
    /*public void enterCalendar(){
        //Scanner entry = new Scanner(System.in);
        int year;   int month;  int day;
        
        while(false){
            try{
                System.out.println("");
                year = verifyInt("Please enter the year: ");
                break;
            }catch(Exception nope){
                System.out.println("That is not valid try again");
            }
        }
        
        while(false){
            try{
                System.out.println("");
                System.out.print("Please enter the month: ");
                //month = parseInt(entry.nextLine());
                if(month < 1 || month > 12){
                    System.out.println("Please input value between 1 and 12");
                }else{
                    break;
                }
            }catch(Exception nope){
                System.out.println("That is not valid, try again");
            }
        }
        
        while(false){
            try{
                System.out.println("");
                System.out.print("Please enter the day: ");
                //day = parseInt(entry.nextLine());
                
                if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                    if(day < 1 || day > 31){
                        System.out.println("Please input value between 1 and 31");
                    }else{
                        break;
                    }
                }
                if(month == 4 || month == 6 || month == 9 || month == 11){
                    if(day < 1 || day > 30){
                        System.out.println("Please input value between 1 and 30");
                    }else{
                        break;
                    }
                }
                if(month == 2){ //February needs to take into account leap years, which repeat every 4 years, get excluded every 100 years, but the exclusion is not in effect every 400 years
                    if(year % 4 == 0){
                        if(year % 100 == 0 && year % 400 == 0){
                            if(day < 1 || day > 29){
                                System.out.println("Please input value between 1 and 29");
                            }else{
                                break;
                            }
                        }else if(year % 100 == 0 && year % 400 != 0){
                            if(day < 1 || day > 28){
                                System.out.println("Please input value between 1 and 28");
                            }else{
                                break;
                            }
                        }else{
                            if(day < 1 || day > 29){
                                System.out.println("Please input value between 1 and 29");
                            }else{
                                break;
                            }
                        }
                    }else{
                        if(day < 1 || day > 28){
                            System.out.println("Please input value between 1 and 28");
                        }else{
                            break;
                        }
                    }
                }
            }catch(Exception nope){
                System.out.println("That is not valid, try again");
            }
        }
        
        try{
            //this.date = month + "/" + day + "/" + year;
        }catch(Exception exception){
            System.out.println("Error setting date.");
        }
        
    }*/
    
    public String getDate(){
        if(this.loan == true){
            return this.date;
        }else{
            return "never";
        }
    }
    
    public int getMonth(){
        try{
            return parseInt(date.split("/")[0]);
        }catch(Exception noDate){
            return 0;
        }
    }
    
    public int getDay(){
        try{
            return parseInt(date.split("/")[1]);
        }catch(Exception noDate){
            return 0;
        }
    }
    
    public int getYear(){
        try{
            return parseInt(date.split("/")[2]);
        }catch(Exception noDate){
            return 0;
        }
    }
    
    public void resetCalendar(){
        this.date = "0/0/0";
    }
    
    public String getLoan(){
        if(this.loan == true){
            return "true";
        }else if(this.loan == false){
            return "false";
        }else{
            return "false";
        }
    }
    
    public String generateTitle(){   //Patrick and His Pickup Truck Door
        String[] part1 = {"Warriors", "Another Brick", "Steve", "The Godfather", "Lord", "Gone", "Harry Potter", "Internet", "Adventures", "Airplane", "Annie", "Michael", "Free", "Back", "President", "Beauty", "Beverly", "Stuart", "War", "Superman", "Spiderman", "Pride", "Karate", "The Thief", "The Murderer", "Aashishkebab", "Star", "Citizen", "Soldier", "I am", "God", "Nobody", "Somebody", "Everybody", "Hunger", "Vultures", "Tragedy", "Kings", "Queens", "Fury", "Valley", "Heart", "Dreamer", "Jekyll", "Flight"};
        String[] part2 = {"of the", "at the", "outside", "to the", "for the", "for", "of", "at", "with", "without", "driving", "leaving", "arriving at", "staying at", "Going to", "destroying", "creating", "of", "and his", "and her", "and the", "and", "or the", "or", "or his", "or her", "or its", "and its", "with a side of"};
        String[] part3 = {"Jobs", "Wall", "Rings", "Potter", "Beast", "One", "Gatsby", "Clockmaker", "Aashishkebab", "Flight", "Copperfield", "Angels", "Computers", "Heaven", "Hell", "King", "New York", "Wind", "Earth", "Worlds", "Expectations", "Rain", "Life", "Death", "Woman", "Man", "Girl", "Boy", "Thing", "Stuff", "People", "Return", "Theft", "Wars", "New Years", "God", "Dead", "Darkness", "Hyde", "Nightmare", "Grave", "Fire", "Mountains", "Dragon", "Zone", "Heart", "Explorer", "Windows", "Office", "Bumblebee"};
        
        int pre;
        int mid;
        int post;
        
        pre = (int)(Math.random() * part1.length);
        mid = (int)(Math.random() * part2.length);
        post = (int)(Math.random() * part3.length);
        
        if((int)(Math.random() * 23) == 17){
            return "Patrick and His Truck Door";
        }
        
        return part1[pre] + " " + part2[mid] + " " + part3[post];
    }
    
    public String generateFormat(){
        String[] formats = {"CD", "DVD", "Blu-Ray", "MP3", "MP4", "JPG", "PNG", "FLAC", "AIFF", "exe", "Vinyl", "AVI", "MOV", "java", "cpp", "bat", "book", "eBook", "Audio Book", "document", "slideshow", "dll"};
        
        return formats[(int)(Math.random() * formats.length)];
    }
    
    public boolean generateLoan(){
        if((int)(Math.random() * 2) == 1){
            return true;
        }else{
            return false;
        }
    }
    
    public String generateDate(){
        int month, day, year;
        
        year = (int)((Math.random() * 3000) + 1);
        month = (int)((Math.random() * 12) + 1);
        
        while(true){
            day = (int)((Math.random() * 31) + 1);
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                if(day < 1 || day > 31){
                    //System.out.println("Please input value between 1 and 31");
                }else{
                    break;
                }
            }
            if(month == 4 || month == 6 || month == 9 || month == 11){
                if(day < 1 || day > 30){
                    //System.out.println("Please input value between 1 and 30");
                }else{
                    break;
                }
            }
            if(month == 2){ //February needs to take into account leap years, which repeat every 4 years, get excluded every 100 years, but the exclusion is not in effect every 400 years
                if(year % 4 == 0){
                    if(year % 100 == 0 && year % 400 == 0){
                        if(day < 1 || day > 29){
                            //System.out.println("Please input value between 1 and 29");
                        }else{
                            break;
                        }
                    }else if(year % 100 == 0 && year % 400 != 0){
                        if(day < 1 || day > 28){
                            //System.out.println("Please input value between 1 and 28");
                        }else{
                            break;
                        }
                    }else{
                        if(day < 1 || day > 29){
                            //System.out.println("Please input value between 1 and 29");
                        }else{
                            break;
                        }
                    }
                }else{
                    if(day < 1 || day > 28){
                        //System.out.println("Please input value between 1 and 28");
                    }else{
                        break;
                    }
                }
            }
        }
        
        return month + "/" + day + "/" + year;
    }
    
    public static String generateName(){
	String name = Character.toString(Character.forDigit((int) (Math.random() * 27) + 10, 36));
	int vowel = 0;
        int randomLength = 0;
        
        while(randomLength < 3){
            randomLength = (int)(Math.random() * 13);
        }
	
	while(name.length() < randomLength){
            while(true){
                vowel = (int)(Math.random() * 27) + 10;
                if(vowel == 10 || vowel == 14 || vowel == 18 || vowel == 24 || vowel == 30){
                    break;
                }
            }
            name += Character.toString(Character.forDigit(vowel, 36));
            name += Character.toString(Character.forDigit((int)(Math.random() * 27) + 10, 36));
	}
	
	return name;
    }
    
    @Override
    public int compareTo(Item theItem){
        String s1, s2;
        String[] bothStrings = new String[2];
        String sortBy;
        
        if(this.compareType.equals("title")){
            bothStrings = compareNames(theItem);
        }else if(this.compareType.equals("date")){
            bothStrings = compareDates(theItem);
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            Toolkit.getDefaultToolkit().beep();
            alert.setTitle("Oh noes");
            alert.setHeaderText("Error in sorting");
            alert.setContentText("The idjit programmer didn't know what he was doing, and screwed up the sorting algorithm.");
            alert.show();
            
            return 0;
        }
        try{
            return Integer.compare(Integer.parseInt(bothStrings[0]), Integer.parseInt(bothStrings[1]));
        }catch(Exception exception){
            return bothStrings[0].compareTo(bothStrings[1]);
        }
    }
    
    public String[] compareDates(Item theItem){
        String s1, s2;
        String s1year = "", s1month = "", s1day = "";  String s2year = "", s2month = "", s2day = "";
        
        try{
            s1year = this.date.split("/")[2];
            s1month = this.date.split("/")[0];
            s1day = this.date.split("/")[1];
            //s1 = this.date.split("/")[2] + this.date.split("/")[0] + this.date.split("/")[1];
        }catch(Exception noDate){
            s1 = "";
        }
        try{
            s2year = theItem.date.split("/")[2];
            s2month = theItem.date.split("/")[0];
            s2day = theItem.date.split("/")[1];
            //s2 = theItem.getDate().split("/")[2] + theItem.date.split("/")[0] + theItem.date.split("/")[1];
        }catch(Exception noDate){
            s2 = "";
        }
        
        
        s1 = s1year;
        s2 = s2year;
        if(s1.equals(s2)){
            s1 = s1month;
            s2 = s2month;
            
            if(s1.equals(s2)){
                s1 = s1day;
                s2 = s2day;
            }
        }
        
        
        if(s1.equals(s2)){  //If dates are equal, sort by name
            s1 = this.title;
            s2 = theItem.title;
        }
        
        if(this.loan == false){
            s1 = "z";
        }
        if(theItem.loan == false){
            s2 = "z";
        }

        String[] twoStrings = new String[2];
        twoStrings[0] = s1;
        twoStrings[1] = s2;

        return twoStrings;
    }
    
    public String[] compareNames(Item theItem){
        String s1, s2;
        String[] twoStrings = new String[2];
        
        s1 = this.title;
        s2 = theItem.title;
        
        if(s1.equals(s2)){  //If names are equal, compare dates
            //System.out.println("Names same");
            try{
                s1 = this.date.split("/")[2] + this.date.split("/")[0] + this.date.split("/")[1];
            }catch(Exception noDate){
                s1 = "";
            }
            try{
                s2 = theItem.getDate().split("/")[2] + theItem.date.split("/")[0] + theItem.date.split("/")[1];
            }catch(Exception noDate){
                s2 = "";
            }
        }
        
        twoStrings[0] = s1;
        twoStrings[1] = s2;
        
        return twoStrings;
    }
}
