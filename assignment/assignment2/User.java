public class User {
	private String username;
	// String keys which are the appName and Long values 
	// which reflect the hashed value of that user's password
	private DoubleHashMap<String, Long> users;
	// construct a new User with given username and empty password store
	// store should have size 20, and use multiplier=1 modulus=23 secondaryModulus=11 
	public User(String username){
		this.username = username;
		users = new DoubleHashMap<String, Long>(20, 1, 23, 11);
	}
	// get methods
	public String getUsername(){
		return this.username;
	}
	public Long getPassword(String appName){
		return users.get(appName);
	}
	// set method
	public void setPassword(String appName, Long passwordHash){
		users.put(appName, passwordHash);
	}
}