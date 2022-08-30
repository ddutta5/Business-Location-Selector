// --== CS400 File Header Information ==--
// Name: Divyangam
// Email: ddutta5@wisc.edu

import java.util.UUID;

//This class creates a new User
public class User {

  protected String name;
  private final String userId;
  private String password;
  
  public User(String Name, String userId, String password) {
    this.name= Name;
    this.userId = userId;
    this.password = password;
  }
  
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  
  public String getUserId() {
    return userId;
  }


  private String GenerateUserId() {
    
    String uniqueId = UUID.randomUUID().toString();
    return uniqueId;
  }
  
}
