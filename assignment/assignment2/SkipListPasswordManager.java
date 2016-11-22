import java.util.ArrayList;
import java.util.List;


public class SkipListPasswordManager {
	// String keys are the username and User values which are the user objects associated
	public SkipList<String, User> manager;
	// key against which internal password is stored on the User object
	public static final String NAME = "pwdMan"; 
	// construct a PasswordManager with 4000 places
	// hash parameters should be multiplier=1 modulus=4271 secondaryModulus=647 
	public SkipListPasswordManager(){
		manager = new SkipList<String, User>();
	}
	// construct a PasswordManager with size number of places
	// hash parameters should be multiplier=1 modulus=4271 secondaryModulus=647 
	public SkipListPasswordManager(int size){
		manager = new SkipList<String, User>();
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
	public int searchSteps(String username){
		SkipListNode<String, User> node = manager.search(username);
		return manager.searchSteps;
	}
	// userbase methods
	// return an array of the usernames of the users currently stored
	public List<String> listUsers() {
		return manager.keys();
	}
	public int numberUsers(){
		return manager.size();
	}
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
		User user = manager.search(username).getValue();
		
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
		User user = manager.search(username).getValue();
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
		User user = manager.search(username).getValue();

		user.setPassword(NAME, hash(newPassword));
		return username;
	}
	public String resetPassword(String username, String oldPassword, String newPassword, String appName) {
		if(authenticate(username, oldPassword, appName) != username){
			return authenticate(username, oldPassword);
		}
		User user = manager.search(username).getValue();

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
		User user = manager.search(username).getValue();

		user.setPassword(appName, hash(appPassword));
		return username;
	}
	
}
