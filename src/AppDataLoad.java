// --== CS400 File Header Information ==--
// Name: Divyangam
// Email: ddutta5@wisc.edu

import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.UUID;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class AppDataLoad  {

 //Loads the users.json file
  public HashTableMap<String , User> loadUsers(String jsonUsersFilePath) throws FileNotFoundException, Exception{
    
    if (!new File(jsonUsersFilePath).exists()) {
      throw new FileNotFoundException("Error: Users File does not exist");
  }
    
    JSONParser parser = new JSONParser();
    HashTableMap<String , User> setOfUsers = new HashTableMap<String , User>();
    try {
      Object obj = parser.parse(new FileReader(jsonUsersFilePath));
      JSONArray userData = (JSONArray) obj;
      
      String name = "", userId ="", password ="";
      for(Object o : userData) {
        JSONObject user = (JSONObject) o;
        name = user.get("name").toString();
        userId = user.get("UserId").toString();
        password = user.get("Password").toString();
        if(name =="" || userId =="" || password =="") {
          throw new Exception("Error: Blank Name or UserId or password found");
        }
        if (userId.length() < 6) {
          throw new Exception("Error: UserId with length less than 6 found.");
        }   
        if (password.length() < 8 ) {
          throw new Exception("Error: Password with length less than 8 found.");
        }
        User u = new User(name,userId,password);
        setOfUsers.put(userId, u);
        
      }     
      
    }
   catch(Exception e) {
    e.printStackTrace();
 }
    return setOfUsers;
    
  }
  
//loads the buildings.json file
  public ArrayList<Building> loadBuildings(String jsonBuildingsFilePath) throws FileNotFoundException, Exception{
    if (!new File(jsonBuildingsFilePath).exists()) {
      throw new FileNotFoundException("Error: Users File does not exist");
  }
    JSONParser parser = new JSONParser();
    ArrayList<Building> listOfBuildings = new ArrayList<>();
    try {
      Object obj = parser.parse(new FileReader(jsonBuildingsFilePath));
      JSONArray buildingData = (JSONArray) obj;
      
      String address ="", zipCode ="", OwnerId = "";
      int usersRated = -1;
      double cost = -1.0;
      
      for(Object o: buildingData) {
        JSONObject building = (JSONObject) o;
        OwnerId = building.get("OwnerId").toString();
        address = building.get("Address").toString();
        zipCode = building.get("ZipCode").toString();
        cost = Double.parseDouble(building.get("Cost").toString());
        
        
        if (OwnerId == "" || address == "" || zipCode == "" || cost == -1.0 ) {
          throw new Exception("Error: Incorrect parameters/format");
        }
        Building b = new Building(address, zipCode, OwnerId, cost);
        listOfBuildings.add(b);
        
      }
    }
    catch(Exception e) {
     e.printStackTrace();
  }
    return listOfBuildings;
  }
  
  //loads  Zipcodes.csv file
  public HashTableMap<String, Double> loadAreaRating(String csvZipCodeFilePath) throws FileNotFoundException, Exception{
    if (!new File(csvZipCodeFilePath).exists()) {
      throw new FileNotFoundException("Error: Users File does not exist");
  }
    HashTableMap<String, Double> zipCodeRatingsHashTableMap = new HashTableMap<String, Double>();
    try {
    Scanner scanner = new Scanner(new File(csvZipCodeFilePath), "UTF-8");
    scanner.nextLine(); 
    int i=0;
    while(scanner.hasNextLine()) {

        String data = scanner.nextLine().toString();
        String [] dataArray = data.split(",");
        String zipCode = dataArray[0];
        double ratings = Double.parseDouble(dataArray[1]);
        zipCodeRatingsHashTableMap.put(zipCode, ratings);
      
    }
   
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return zipCodeRatingsHashTableMap;
  }
}
  