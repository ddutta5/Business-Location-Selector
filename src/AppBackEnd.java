// --== CS400 File Header Information ==--
// Name: Divyangam
// Email: ddutta5@wisc.edu


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
// import jdk.internal.jshell.tool.resources.l10n;



public class AppBackEnd {

  HashTableMap<String, User> setOfUsers; // hashmap which uses userId as key to store Users
  
  private HashTableMap<String, Building> setOfBuildings; //// hashmap which uses Building Id as key to
                                                         //// store buildings
  
  private HashTableMap<String, Double> setOfAreaRatings; // hashmap which uses zipcode as key to
                                                         // store rating of that areas
  
  private HashTableMap<String, ArrayList<String>> setOfOwnerPostings; // hashmap which uses building
                                                                      // Id as key to store all
                                                                      // postings of the user
  
  PriorityQueue<Building> cheapestBuildings;// stores buildings from lowest to highest cost
  PriorityQueue<Building> bestRatedBuildings;// stores buildings from higher to lower area rating

  // constructor
  public AppBackEnd(HashTableMap<String, User> setOfUsers, HashTableMap<String, Double> AreaRatings,
      ArrayList<Building> listOfBuildings) {

    this.setOfUsers = setOfUsers;
    this.setOfAreaRatings = AreaRatings;
    this.setOfOwnerPostings = new HashTableMap<String, ArrayList<String>>();
    this.setOfBuildings = new HashTableMap<String, Building>();
    this.cheapestBuildings =
        new PriorityQueue<Building>((a, b) -> Double.compare(a.getCost(), b.getCost()));
    this.bestRatedBuildings =
        new PriorityQueue<Building>((a, b) -> Double.compare(b.getAreaRating(), a.getAreaRating()));
    InitiliazeSetofOwnersBuildings(listOfBuildings);
    InitializeBuildingsWithAreaRatings(listOfBuildings);
  }

  // getters and setters
  public HashTableMap<String, User> getSetOfUsers() {
    return setOfUsers;
  }

  public void setSetOfUsers(HashTableMap<String, User> setOfUsers) {
    this.setOfUsers = setOfUsers;
  }

  public HashTableMap<String, Building> getSetOfBuildings() {
    return setOfBuildings;
  }

  public void setSetOfBuildings(HashTableMap<String, Building> setOfBuildings) {
    this.setOfBuildings = setOfBuildings;
  }

  public HashTableMap<String, Double> getSetOfAreaRatings() {
    return setOfAreaRatings;
  }

  public void setSetOfAreaRatings(HashTableMap<String, Double> setOfAreaRatings) {
    this.setOfAreaRatings = setOfAreaRatings;
  }

  public PriorityQueue<Building> getCheapestBuildings() {
    return cheapestBuildings;
  }

  public void setCheapestBuildings(PriorityQueue<Building> cheapestBuildings) {
    this.cheapestBuildings = cheapestBuildings;
  }

  public PriorityQueue<Building> getBestRatedBuildings() {
    return bestRatedBuildings;
  }

  public void setBestRatedBuildings(PriorityQueue<Building> bestRatedBuildings) {
    this.bestRatedBuildings = bestRatedBuildings;
  }

  // add new users who use the signup option
  public void AddUsersToSetOfUsers(User user) {

    if (setOfUsers.containsKey(user.getUserId())) {
      System.out.println("User already exists");
      return;
    }
    setOfUsers.put(user.getUserId(), user);
  }

  // This method will create a mapping between owner ID and building ID's he has posted
  private void InitiliazeSetofOwnersBuildings(ArrayList<Building> listOfBuildings) {
    for (Building bldg : listOfBuildings) {
      String ownerIdString = bldg.getOwnerId();
      if (this.setOfUsers.containsKey(ownerIdString)) {
        if (!this.setOfOwnerPostings.containsKey(ownerIdString)) {
          this.setOfOwnerPostings.put(ownerIdString,
              new ArrayList<String>(Arrays.asList(bldg.getId())));
        } else {

          this.setOfOwnerPostings.get(ownerIdString).add(bldg.getId());
        }
      } else {
        System.out.println("Building's Owner doesn't exist in the Database");
      }
    }
  }

  // Initializes buildings with ratings of that area from ZipCodeRatings.csv file
  private void InitializeBuildingsWithAreaRatings(ArrayList<Building> listOfBuildings) {

    for (Building bldg : listOfBuildings) {

      bldg.setAreaRating(this.setOfAreaRatings.get(bldg.getZipcode()));
      setOfBuildings.put(bldg.getId(), bldg);
      cheapestBuildings.add(bldg);
      bestRatedBuildings.add(bldg);
    }
  }


  // add a new building posted by the user into all list of buildings
  public void PostBuilding(Building bldg) {

    cheapestBuildings.add(bldg);
    bestRatedBuildings.add(bldg);
    setOfBuildings.put(bldg.getId(), bldg);
    setOfOwnerPostings.get(bldg.getOwnerId()).add(bldg.getId());
  }

  // Deletes a building posted by the user from all the list of buildings
  public void DeleteBuilding(String bldgId, String UserId) {
    ArrayList<Building> tempArrayList = new ArrayList<>();
    ArrayList<Building> tempArrayList2 = new ArrayList<>();
    while (!this.bestRatedBuildings.isEmpty()) {
      if (this.bestRatedBuildings.peek().equals(bldgId)) {
        bestRatedBuildings.poll();
        break;

      }
      tempArrayList.add(bestRatedBuildings.peek());
      bestRatedBuildings.poll();
    }
    for (Building b : tempArrayList) {
      bestRatedBuildings.add(b);
    }

    while (!this.cheapestBuildings.isEmpty()) {
      if (this.cheapestBuildings.peek().equals(bldgId)) {
        this.cheapestBuildings.poll();
        break;

      }
      tempArrayList2.add(bestRatedBuildings.peek());
      this.cheapestBuildings.poll();
    }
    for (Building b : tempArrayList2) {
      this.cheapestBuildings.add(b);
    }

    this.setOfOwnerPostings.get(UserId).remove(bldgId);
    this.setOfBuildings.remove(bldgId);


  }

  // Helps to get the buildings in order of cost using priority queue fields
  public ArrayList<Building> getTopNCheapestBuildings(int n) {
    ArrayList<Building> listOfBuildings = new ArrayList<Building>();
    if (this.bestRatedBuildings.size() == 0)
      return listOfBuildings;

    // Get the copy of the cheapest buildings
    PriorityQueue<Building> cBuildings = new PriorityQueue<>(this.cheapestBuildings);
    Building blg;
    for (int i = 0; i < n; i++) {
      if (cBuildings.isEmpty()) {
        System.out.println("Number of buildings requested exceed the buildings stored");
        break;
      }
      blg = cBuildings.poll();
      listOfBuildings.add(blg);

    }
    return listOfBuildings;
  }

  // Helps to get the buildings in order of their ratings using priority queue fields
  public ArrayList<Building> getTopNBestRatedBuildings(int n) {
    ArrayList<Building> listOfBuildings = new ArrayList<Building>();
    if (this.bestRatedBuildings.size() == 0)
      return listOfBuildings;

    // Get the copy of the best rated buildings
    PriorityQueue<Building> rBuildings = new PriorityQueue<>(this.bestRatedBuildings);

    Building blg;

    for (int i = 0; i < n; i++) {
      if (rBuildings.isEmpty()) {
        System.out.println("Number of buildings requested exceed the buildings stored");
        break;
      }
      blg = rBuildings.poll();
      listOfBuildings.add(blg);

    }
    return listOfBuildings;
  }

  // returns the name of the owner of the given building
  public String getBuildingOwnerName(Building b) {

    return this.setOfUsers.get(b.getOwnerId()).getName();
  }


  // returns a string with the address and cost of a buildings
  // in ascending order of their cost. The number of buildings is specified
  // by the user. This method is used for Junit Testing
  public String toStringCost(int n) {
    String costString = "";
    ArrayList<Building> bldg = new ArrayList<Building>();
    bldg = getTopNCheapestBuildings(n);
    for (int i = 0; i < bldg.size(); i++) {
      costString += bldg.get(i).getCost() + "\n";
    }
    return costString;
  }

  // returns a string with the address and rating of a buildings
  // in descending order of their rating. The number of buildings is specified
  // by the user. This method is used for Junit Testing
  public String toStringRating(int n) {
    String areaString = "";
    ArrayList<Building> bldg = new ArrayList<Building>();
    bldg = getTopNBestRatedBuildings(n);
    for (int i = 0; i < bldg.size(); i++) {
      areaString += bldg.get(i).getAreaRating() + "\n";
    }
    return areaString;
  }

  // prints out the bldg Id and Building address
  // and returns false if there is a building associated with the given user id
  // returns true otherwise. This method is used for Junit testing
  public boolean printOwnersPostings(String UserId) {

    if (!this.setOfOwnerPostings.containsKey(UserId)) {
      System.out.println("You don't have any postings");
      return true;
    }
    for (String bldgId : this.setOfOwnerPostings.get(UserId)) {
      System.out.println("Bldg Id: " + bldgId);
      System.out.println("Building Address: " + this.setOfBuildings.get(bldgId).getAddress());
    }
    return false;
  }

  // returns a list of owner IDs of the building
  public ArrayList<String> getOwnersPostingsIds(String UserId) {
    ArrayList<String> resultArrayList = new ArrayList<>();
    if (!this.setOfOwnerPostings.containsKey(UserId)) {

      return resultArrayList;
    }
    for (String bldgId : this.setOfOwnerPostings.get(UserId)) {
      resultArrayList.add(bldgId);

    }
    return resultArrayList;
  }



}
