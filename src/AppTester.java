import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestApp {


 //fields to create testers
  User user1 = new User("Taylor", "Taylor_swift", "abcdefgh");
  User user2 = new User("Dylan", "Dylan_123", "retyuiokm");
  Building bldg1 = new Building("628 Grant Ave, VA", "31229", "Taylor_swift", 1500000);
  Building bldg2 = new Building("79 Tallwood Drive Suite 868", "30315", "Dylan_123", 200000);
  Building bldg3 = new Building("134 Pine Street, MA", "23150", "Dylan_123", 250000);
  ArrayList<Building> bldgArray = new ArrayList<Building>();
  AppBackEnd backEnd;

  //Test to check if the minimum heap prints out the buildings in order of their cost
  @Test
  void test1() {

    ArrayList<Building> bldgArray = new ArrayList<Building>();
    bldgArray.add(bldg1);
    bldgArray.add(bldg2);
    bldgArray.add(bldg3);

    HashTableMap<String, User> setOfUsers = new HashTableMap<String, User>(10);
    setOfUsers.put(user1.getUserId(), user1);
    setOfUsers.put(user2.getUserId(), user2);

    HashTableMap<String, Double> areaRatings = new HashTableMap<String, Double>(10);
    areaRatings.put("31229", 7.0);
    areaRatings.put("30315", 8.0);
    areaRatings.put("23150", 9.5);
    backEnd = new AppBackEnd(setOfUsers, areaRatings, bldgArray);

    String expected1 = "200000.0\n" + "250000.0\n" + "1500000.0\n";
    assertEquals(expected1, backEnd.toStringCost(3));

  }

//Test to check if the maximum heap prints out the buildings in order of their ratings
  @Test
  void test2() {
    ArrayList<Building> bldgArray = new ArrayList<Building>();
    bldgArray.add(bldg1);
    bldgArray.add(bldg2);
    bldgArray.add(bldg3);
    HashTableMap<String, User> setOfUsers = new HashTableMap<String, User>(10);
    setOfUsers.put(user1.getUserId(), user1);
    setOfUsers.put(user2.getUserId(), user2);
    
    HashTableMap<String, Double> areaRatings = new HashTableMap<String, Double>(10);
    areaRatings.put("31229", 7.0);
    areaRatings.put("30315", 8.0);
    areaRatings.put("23150", 9.5);
    backEnd = new AppBackEnd(setOfUsers, areaRatings, bldgArray);

    String expected2 = "9.5\n" + "8.0\n" + "7.0\n";

    assertEquals(expected2, backEnd.toStringRating(3));

  }

 //Verifies the correlation between the userId and ownerId(they should be same) and
 //verifies the Owner's information
  @Test
  void test3() {
    ArrayList<Building> bldgArray = new ArrayList<Building>();
    bldgArray.add(bldg1);
    bldgArray.add(bldg2);
    bldgArray.add(bldg3);
    HashTableMap<String, User> setOfUsers = new HashTableMap<String, User>(10);
    setOfUsers.put(user1.getUserId(), user1);
    setOfUsers.put(user2.getUserId(), user2);
    
    HashTableMap<String, Double> areaRatings = new HashTableMap<String, Double>(10);
    areaRatings.put("31229", 7.0);
    areaRatings.put("30315", 8.0);
    areaRatings.put("23150", 9.5);
    backEnd = new AppBackEnd(setOfUsers, areaRatings, bldgArray);


    String expected3 = "Taylor";
    String expected4 = "Dylan";
    String expected5 = "Dylan";
    assertEquals(expected3, backEnd.getBuildingOwnerName(bldg1));
    assertEquals(expected4, backEnd.getBuildingOwnerName(bldg2));
    assertEquals(expected5, backEnd.getBuildingOwnerName(bldg3));
  }

  //This method verifies if the method to delete building is working correctly
  //and if the postings are updated in the backend
  @Test
  void test4() {
    ArrayList<Building> bldgArray = new ArrayList<Building>();
    bldgArray.add(bldg1);
    bldgArray.add(bldg2);
    bldgArray.add(bldg3);
    HashTableMap<String, User> setOfUsers = new HashTableMap<String, User>(10);
    setOfUsers.put(user1.getUserId(), user1);
    setOfUsers.put(user2.getUserId(), user2);
    
    HashTableMap<String, Double> areaRatings = new HashTableMap<String, Double>(10);
    areaRatings.put("31229", 7.0);
    areaRatings.put("30315", 8.0);
    areaRatings.put("23150", 9.5);
    backEnd = new AppBackEnd(setOfUsers, areaRatings, bldgArray);
    backEnd.DeleteBuilding( bldg2.getId(),user2.getUserId());
    assertEquals(backEnd.getSetOfBuildings().containsKey(bldg1.getId()),true);
    assertEquals(backEnd.getSetOfBuildings().containsKey(bldg2.getId()),false);
    assertEquals(backEnd.getSetOfBuildings().containsKey(bldg3.getId()),true);
  }
  
  //verifies whether owner's associated building postings by comparing the building ID's
  //from the backend and inputs
  @Test
  void test5() {
    ArrayList<Building> bldgArray = new ArrayList<Building>();
    bldgArray.add(bldg1);
    bldgArray.add(bldg2);
    bldgArray.add(bldg3);
    HashTableMap<String, User> setOfUsers = new HashTableMap<String, User>(10);
    setOfUsers.put(user1.getUserId(), user1);
    setOfUsers.put(user2.getUserId(), user2);
    
    HashTableMap<String, Double> areaRatings = new HashTableMap<String, Double>(10);
    areaRatings.put("31229", 7.0);
    areaRatings.put("30315", 8.0);
    areaRatings.put("23150", 9.5);
    backEnd = new AppBackEnd(setOfUsers, areaRatings, bldgArray);
    String expected = "Bldg Id: "+bldg2.getId()+ "\nBuilding Address: " + "79 Tallwood Drive Suite 868\n"+
                      "Bldg Id: "+bldg3.getId()+ "\nBuilding Address: " + "134 Pine Street, MA\n";
    boolean result = false;
    for(String Ids : backEnd.getOwnersPostingsIds("Dylan_123")) {
      
      if(!(Ids.equals(bldg2.getId()) || Ids.equals(bldg3.getId()))) {
        result = false;
        break;
      }
      result = true;
    }
    assertEquals(true, result);
    
  }
}
