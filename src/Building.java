// --== CS400 File Header Information ==--
// Name: Divyangam
// Email: ddutta5@wisc.edu

//This class creates a new Building
public class Building {

  private final String Id;
  private String Address;
  private String Zipcode;
  private String OwnerId;
  private double AreaRating = 0;
  private double Cost;



  public Building(String Address, String ZipCode, String OwnerId, double Cost) {
    this.Id = GenerateBuildingId();
    this.Address = Address;
    this.OwnerId = OwnerId;
    this.Cost = Cost;
    this.Zipcode = ZipCode;
  }



  public String getAddress() {
    return Address;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public String getZipcode() {
    return Zipcode;
  }

  public void setZipcode(String zipcode) {
    Zipcode = zipcode;
  }

  public String getOwnerId() {
    return OwnerId;
  }

  public void setOwnerId(String ownerId) {
    OwnerId = ownerId;
  }

  public double getAreaRating() {
    return AreaRating;
  }

  public void setAreaRating(double AreaRating) {

    this.AreaRating = AreaRating;
  }

  public double getCost() {
    return Cost;
  }

  public void setCost(double cost) {
    Cost = cost;
  }

  public String getId() {
    return Id;
  }


  // Creates a random Unique Location ID
  private String GenerateBuildingId() {


    String AlphaNumericString =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(10);

    for (int i = 0; i < 10; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index = (int) (AlphaNumericString.length() * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString.charAt(index));
    }

    return sb.toString();
 
  }

}
