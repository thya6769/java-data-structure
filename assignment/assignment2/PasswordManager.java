import java.util.ArrayList;
import java.util.List;


public class PasswordManager {
	// String keys are the username and User values which are the user objects associated
	public DoubleHashMap<String, User> manager;
	// key against which internal password is stored on the User object
	public static final String NAME = "pwdMan"; 
	// construct a PasswordManager with 4000 places
	// hash parameters should be multiplier=1 modulus=4271 secondaryModulus=647 
	public PasswordManager(){
		manager = new DoubleHashMap<String, User>(1, 4271, 647);
	}
	// construct a PasswordManager with size number of places
	// hash parameters should be multiplier=1 modulus=4271 secondaryModulus=647 
	public PasswordManager(int size){
		manager = new DoubleHashMap<String, User>(size, 1, 4271, 647);
	}
	// hash representation of password to be stored on the User object
	public Long hash(String password){
		long h = 1125899906842597L; // prime
		  int len = password.length();

		  for (int i = 0; i < len; i++) {
		    h = 31*h + password.charAt(i);
		  }
		return h;
	}

	// userbase methods
	// return an array of the usernames of the users currently stored
	public List<String> listUsers() {
		return manager.keys();
	}
	// return number of users in the manager
	public int numberUsers(){
		return manager.size();
	}
	// adds new user to the manager using put()
	public String addNewUser(String username, String password){
		if(manager.get(username) != null){
			return "User already exists.";
		}
		User newUser = new User(username);
		Long hashedPass = hash(password);
		newUser.setPassword(NAME, hashedPass);
		manager.put(username, newUser);
		return username;
	}
	// delete user from manager using .remove()
	public String deleteUser(String username, String password){
		if(!authenticate(username, password).equals(username)){
			return authenticate(username, password);
		}
		manager.remove(username);
		return username;
	}
	// interface methods
	// when no appName is supplied check against the internal password for the manager
	public String authenticate(String username, String password){
		if(manager.get(username) == null){
			return "No such user exists.";
		}
		User user = null;
		for(int i = 0; i < manager.size();i++){
			if(manager.keys().get(i).equals(username)){
				user = manager.values().get(i);
				break;
			}
		}
		if(user.getPassword(NAME) == null){
			return "No password found.";
		}
		if(!hash(password).equals(user.getPassword(NAME))){
			return "Failed to authenticate user.";
		}
		return username;
	}
	// when appName is supplied
	public String authenticate(String username, String password, String appName){
		if(manager.get(username) == null){
			return "No such user exists.";
		}
		User user = null;
		for(int i = 0; i < manager.size();i++){
			if(manager.keys().get(i).equals(username)){
				user = manager.values().get(i);
				break;
			}
		}
		if(user.getPassword(appName) == null){
			return "No password found.";
		}
		if(!hash(password).equals(user.getPassword(appName))){
			return "Failed to authenticate user.";
		}
		return username;
	}
	public String resetPassword(String username, String oldPassword, String newPassword){
		if(!authenticate(username, oldPassword).equals(username)){
			return authenticate(username, oldPassword);
		}
		User user = null;
		for(int i = 0; i < manager.size();i++){
			if(manager.keys().get(i).equals(username)){
				user = manager.values().get(i);
				break;
			}
		}
		user.setPassword(NAME, hash(newPassword));
		return username;
	}
	// reset the password
	public String resetPassword(String username, String oldPassword, String newPassword, String appName) {
		if(authenticate(username, oldPassword, appName) != username){
			return authenticate(username, oldPassword);
		}
		User user = null;
		for(int i = 0; i < manager.size();i++){
			if(manager.keys().get(i).equals(username)){
				user = manager.values().get(i);
				break;
			}
		}
		user.setPassword(appName, hash(newPassword));
		return username;
	}
	/*
	 * Add a new entry in the given user's password store whose key is appName and whose value
	 * is hash representation of appPassword, after authenticating that thisPassword matches the 
	 * internal password of the manager.
	 */
	public String newAppPassword(String username, String thisPassword, String appPassword, String
			appName){
		if(!authenticate(username, thisPassword).equals(username)){
			return authenticate(username, thisPassword);
		}
		if(authenticate(username, appPassword, appName).equals(username)){
			return "Password already set up.";
		}
		User user = null;
		for(int i = 0; i < manager.size();i++){
			if(manager.keys().get(i).equals(username)){
				user = manager.values().get(i);
				break;
			}
		}
		user.setPassword(appName, hash(appPassword));
		return username;
	}
	public User getUser(String username){
		return manager.get(username);
	}
	
}
