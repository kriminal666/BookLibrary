package bookLibrary.user;

import javax.faces.bean.ManagedBean;



/**
 * User Bean used in PrimeFaces views.
 * 
 * @author criminal
 *
 */
@ManagedBean (name="userBean")
public class UserBean {
	
	private String username;
	
	private String password;
	
	private String email;
	
	//Getters & Setters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	

}
