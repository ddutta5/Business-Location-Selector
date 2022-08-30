// --== CS400 File Header Information ==--
// Name: Divyangam
// Email: ddutta5@wisc.edu

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class AppFrontEnd {
  // fields
  Scanner scanner;
  AppDataLoad dataLoad;
  AppBackEnd backEnd;

 //constructor
  public AppFrontEnd() {

    dataLoad = new AppDataLoad();
    scanner = new Scanner(System.in);
  }

  //returns the area ratings from the ZipCodeRatings.csv file using AppDataLoad.loadAreaRating method
  private HashTableMap<String, Double> LoadAreaRatingfile(String csvAreaRatingFilePath)
      throws FileNotFoundException, Exception {

    return dataLoad.loadAreaRating(csvAreaRatingFilePath);

  }

  //returns the set of users from the Usersjson file using AppDataLoad.LoadUsersFile Method
  private HashTableMap<String, User> LoadUsersFile(String jsonUsersFilePath)
      throws FileNotFoundException, Exception {

    return dataLoad.loadUsers(jsonUsersFilePath);

  }
  
  //returns all the buildings in the Buildings.json file using AppDataLoad.LoadBuildingsFile Method
  private ArrayList<Building> LoadBuildingsFile(String jsonBuildingsFilePath)
      throws FileNotFoundException, Exception {

    return dataLoad.loadBuildings(jsonBuildingsFilePath);
  }

//Commands to the Users when they enter the application
  private void printMainMenu() {

    System.out.println("To Login : Press L \n" + "To Signup: Press S \n" + "To Quit: Press Q");

  }

//Commands to the user when they have completed initial login or signup process
  private void UserActions() {
    System.out.println("Enter the number corresponding to the given commands");
    System.out
        .println("1. To Post Building \n" + "2. Filter out buildings from lowest to highest cost \n"
            + "3. Filter out buildings from highest to lowest Area Rating \n"
            + "4. To view your Postings\n" + "5. To Delete Building \n" + "6. Quit");
  }

//method to use scanner to get string on the entire line
  private String getStringFromScanner(Scanner scnr) {
    String result = "";
    while (scnr.hasNext()) {
      result += scnr.next();
    }
    return result;
  }

//checks if the list of buildings provided have an id in the set of users
//Since the owner ID of the building is the user id itselfe
  private boolean ValidateBuildingsWithOwner(ArrayList<Building> bldgsList,
      HashTableMap<String, User> setOfUsers) {
    for (Building building : bldgsList) {
      if (!setOfUsers.containsKey(building.getOwnerId())) {
        return false;
      }
    }
    return true;
  }
//quits the program
  private void quit() {
    System.out.println("Closing this program!");
    scanner.close();
  }

  // runs the program with user commands
  public void runner() {
    boolean run = true;
    String csvAreaRatingFilePath = "", jsonUsersFilePath = "", jsonBuildingsFilePath = "";
    String paths = "";
    HashTableMap<String, Double> AreaRatings = new HashTableMap<>();
    HashTableMap<String, User> setOfUsers = new HashTableMap<>();
    ArrayList<Building> listOfBuildings = new ArrayList<>();



    for (int i = 0; i < 3; i++) {
      boolean cases = true;

    //this asks for the path of ZipCodeRatings.csv file
      // which has zipcodes with associated ratings
      if (i == 0) {
        while (cases) {
          System.out.println("Enter CSV file path of Area Ratings.");
        try {
          paths = scanner.nextLine().trim();
          AreaRatings = LoadAreaRatingfile(paths); //uses the private helper method given in this class
          System.out.println("Area Ratings CSV File Loaded!");
          csvAreaRatingFilePath = paths;
          cases = false;
        } catch (Exception e) {
          System.out.println("Invalid input or File Path.");

        }
      }
      }
      
    //this asks for the path of provided Buildings.json file
    // which has a set of Buildings. The user can uses it or press n
     //start from scratch
      if (i == 1) {


        while (cases) {
          System.out.println("Enter JSON file path of Existing Users. If none press n");
          try {
            paths = scanner.nextLine().trim();
            if (paths.equals("n"))
              break;

            setOfUsers = LoadUsersFile(paths);
            jsonUsersFilePath = paths;

            cases = false;
          } catch (Exception e) {
            System.out.println("Invalid input or File Path.");

          }


        }
      }
      if (paths.equals("n"))
        break;

      //this asks for the path of provided Buildings.json file
      // which has a set of Buildings. The user can uses it or press n
      //start from scratch
      if (i == 2) {
        System.out.println("Enter JSON file path of Existing Buildings. If none press n");

        while (cases) {

          try {
            paths = scanner.nextLine().trim();

            listOfBuildings = LoadBuildingsFile(paths);
            jsonBuildingsFilePath = paths;

            cases = false;
          } catch (Exception e) {
            System.out.println("Invalid input or File Path.");

          }

        }

      }


    }

  //quit the program if building's owner id is not found
    if (!ValidateBuildingsWithOwner(listOfBuildings, setOfUsers)) {
      System.out.println("Building's Owner Id not found");
      quit();
    }

    //Add the updated fields to initialize the backEnd field
    backEnd = new AppBackEnd(setOfUsers, AreaRatings, listOfBuildings);


    String userVal = "", finalUserId = "";
    boolean quitEntered = false;
    while (run) {
      printMainMenu();
      boolean successfulLogin = false;
      boolean succesfulUserCreation = false;
      boolean goToStartMenu = false;
      try {
        userVal = scanner.nextLine().trim();
        String userId = "", loggedInUserIdString = "", signupUserId = "";

        if (userVal.equals("L")) {


          // Checks if the User ID exists in the backend
          boolean userIdAdded = false;
          boolean userBool = false;
          while (!userBool) {
            System.out.println("Enter User Id or Press M to go Start Menu");
            userId = scanner.nextLine().trim();
            if (userId.equals("M")) {
              // userBool = true;
              goToStartMenu = true;
              break;
            }
            if (backEnd.getSetOfUsers().containsKey(userId)) {


              boolean correctPasswordAdded = false;
              boolean passwordBool = false;
              while (!passwordBool) {
                System.out.println("Enter your password or Press M to go Start Menu");
                String password = scanner.nextLine().trim();
                if (password.equals("M")) {
                  // passwordBool = true;
                  // userBool = true;
                  goToStartMenu = true;
                  break;
                }
                if (backEnd.getSetOfUsers().get(userId).getPassword().equals(password)) {
                  // userIdAdded = true;
                  // correctPasswordAdded = true;
                  passwordBool = true;
                  successfulLogin = true;
                  loggedInUserIdString = userId;
                  System.out.println("Successfully Logged in!");
                  break;
                } else {
                  System.out.println("Incorrect Password entered.");
                }

              }
            } else {
              System.out.println("UserId doesn't exist in the database.");

            }
            if (goToStartMenu)
              break;
            if (successfulLogin)
              break;
          }

        } else if (userVal.equals("S"))

        {


          while (!succesfulUserCreation) {
            System.out.println("Enter name or Press M to go Start Menu");
            String name = scanner.nextLine().trim();
            if (userId.equals("M")) {
              goToStartMenu = true;
              break;
            }
            boolean userIdCreated = false;
            boolean signupBool = false;
            while (!signupBool) {
              System.out.println("Enter UserId or Press M to go Start Menu");
              userId = scanner.nextLine().trim();
              if (userId.length() >= 6) {

                if (userId.equals("M")) {
                  goToStartMenu = true;
                  break;
                }
                boolean passwordCreated = false;
                while (!passwordCreated) {
                  System.out.println("Enter Password or Press M to go Start Menu");
                  String password = scanner.nextLine().trim();
                  if (password.equals("M")) {
                    goToStartMenu = true;
                    break;
                  }
                  if (password.length() >= 8) {
                    User user = new User(name, userId, password);
                    backEnd.AddUsersToSetOfUsers(user);

                    passwordCreated = true;
                    userIdCreated = true;
                    succesfulUserCreation = true;
                    signupUserId = userId;
                    System.out.println("Sign Up Successful!");
                    break;
                  } else {
                    System.out.println("password is less than 8 words.");
                    scanner.nextLine();
                  }
                }
              } else {
                System.out.println("userId is less than 6 words.");

              }
              if (goToStartMenu)
                break;
              if (succesfulUserCreation)
                break;
              // System.out.println("Enter userId");

            }
            if (succesfulUserCreation)
              break;
            if (goToStartMenu)
              break;
          }
        }
        if (userVal.equals("Q")) {
          quitEntered = true;
          quit();
          break;
        }

        if (successfulLogin) {
          finalUserId = loggedInUserIdString;
          break;
        }
        if (succesfulUserCreation) {
          finalUserId = signupUserId;
          break;
        }


      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    if (quitEntered)
      return;
    System.out.println("Welcome " + backEnd.getSetOfUsers().get(finalUserId).getName() + "!");
    boolean action = true;
    while (action) {
      UserActions();
      int intuserVal = scanner.nextInt();
      scanner.nextLine();
      if (intuserVal == 1) {
        System.out.println("Enter Building address");
        String address = scanner.nextLine().trim();
        String zipCode = "";
        boolean ZipBool = false;
        while (!ZipBool) {
          System.out.println("Enter Zipcode");
          zipCode = scanner.nextLine().trim();

          if (zipCode.length() != 5)
            System.out.println("Invalid Zip Code");
          else
            ZipBool = true;
        }
        System.out.println("Enter cost");
        Double cost = scanner.nextDouble();
        Building bldg = new Building(address, zipCode, finalUserId, cost);
        backEnd.PostBuilding(bldg);
        System.out.println("Building posted in the App");
      }


      if (intuserVal == 2) {
        System.out.println("Enter the no of rated buildings you want to see");
        int n = scanner.nextInt();
        ArrayList<Building> bldg = new ArrayList<Building>();

        bldg = backEnd.getTopNCheapestBuildings(n);
        if (bldg.size() == 0) {
          System.out.println("No Buildings in database");
          continue;
        }
        System.out.println("Printing top " + bldg.size() + " Cheapest Buildings");
        for (int i = 0; i < bldg.size(); i++) {
          System.out.print("Address: " + bldg.get(i).getAddress() + " " + bldg.get(i).getZipcode());
          System.out.print(" Cost: " + bldg.get(i).getCost());
          System.out.print(" Area Rating: " + bldg.get(i).getAreaRating());
          System.out.print(
              " Owner: " + backEnd.getSetOfUsers().get(bldg.get(i).getOwnerId()).getName() + "\n");
        }

      }

      //if user command == 3 ask for the number of buildings the user want to see
      //then print out he buildings in order of their area ratings (highest to lowest)
      if (intuserVal == 3) {
        System.out.println("Enter the no of rated buildings you want to see");
        int n = scanner.nextInt();
        ArrayList<Building> bldg = new ArrayList<Building>();
        bldg = backEnd.getTopNBestRatedBuildings(n);
        if (bldg.size() == 0) {
          System.out.println("No Buildings in database");
          continue;
        }
        System.out.println("Printing top " + bldg.size() + " Buildings with the best Area Rating");
        for (int i = 0; i < bldg.size(); i++) {

          System.out.print("Address: " + bldg.get(i).getAddress() + " " + bldg.get(i).getZipcode());
          System.out.print(" Cost: " + bldg.get(i).getCost());
          System.out.print(" Area Rating: " + bldg.get(i).getAreaRating());
          System.out.print(
              " Owner: " + backEnd.getSetOfUsers().get(bldg.get(i).getOwnerId()).getName() + "\n");

        }
      }
      //if user command ==4 post all the buildings of the logged in user
      if (intuserVal == 4) {

        System.out.println("Your Building postings are displayed below");
        boolean noPostings = backEnd.printOwnersPostings(finalUserId);
        if (noPostings) {
          System.out.println("You don't have any postings");
          continue;
        }
      }

      //if user command ==5 remove building
      if (intuserVal == 5) {

        System.out.println("Your Building postings are displayed below");
        boolean noPostings = backEnd.printOwnersPostings(finalUserId);
        if (noPostings) {
          System.out.println("You don't have any postings");
          continue;
        }
        System.out.println("Enter Building Id");
        String bldgId = scanner.nextLine().trim();

        backEnd.DeleteBuilding(bldgId,finalUserId);
        System.out.println("Building removed from the App");

      }

      if (intuserVal == 6) {
        System.out.println("Quitting the App. Goodbye!");
        break;
      }

    }

  }



}
