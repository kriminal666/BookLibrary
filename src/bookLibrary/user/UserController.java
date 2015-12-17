package bookLibrary.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;

import bookLibrary.constants.Constants;
import bookLibrary.entities.User;
import bookLibrary.interfaces.IMessage;
import bookLibrary.services.ModelDAO;
import bookLibrary.utils.Utils;

/**
 * New user registration controller
 * 
 * @author criminal
 *
 */
@ManagedBean (name="userController")
public class UserController {
	
	private User user;
	private final ModelDAO <User> userDAO = new ModelDAO<User>(User.class);
	private String [] msg = new String[2];
	
    /**
     * Set new User entity.
     * 
     * @param newUser
     */
	private void setUser(UserBean newUser){
		
		 
		user = new User(newUser.getUsername(),hashPasswd(newUser.getPassword()), newUser.getEmail());
	
	}
	
	/**
	 * Register new user in database
	 * 
	 * @param newUser
	 */
	public void register(UserBean newUser){
		
		setUser(newUser);
		
		boolean what = false;
		
		//Save to data base
		try{
		   userDAO.insertObject(user);
		   what = true;
		  
		   msg[0] = "User";
		   msg[1] = "New User Saved";
		   Utils.messageMaker(Constants.INSERT,Constants.SUCCESS_TRUE, msg);
		   
		}catch(HibernateException he){
			
			//send message
			Utils.messageMaker(Constants.INSERT,Constants.SUCCESS_FALSE,he.getCause().getMessage());
					
		}
		
		//Every thing has gone right, so clean form
		if(what){
			Utils.reset(Constants.NEW_USER_FORM);
		}
			
		
	}
	
	/**
	 * Generate password hash
	 * @param pass
	 * @return
	 */
	private String hashPasswd(String pass){
		
		String hash = null;
		
		try {
			hash = Utils.hashPassword(pass);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hash;
		
	}
	
	/**
	 * Login user
	 * @param userBean
	 * @return
	 */
	public String loginUser(UserBean userBean){
		
		User auxUser = null;
		
		//Try to get the user from data base
		try{
			auxUser = (User)userDAO.getUserLogin(userBean.getEmail());
		    System.out.println(auxUser);
		}catch(HibernateException he){
			
			 Utils.messageMaker(Constants.RETRIVE_ONE,Constants.SUCCESS_FALSE,he.getCause().getMessage());
			 return null;
		}
		
		//The user not exists in database
		if(auxUser == null){
			
			msg[0] = "User not Found";
			msg[1] = "It doesn't exists user with this email";
			Utils.messageMaker(Constants.RETRIVE_ONE, Constants.SUCCESS_FALSE, msg);
			return null;
			
		}
		
		//Check if password is correct
		if(!validatePassword(auxUser.getPassword(), userBean.getPassword())){
			
			msg[0] = Constants.GENERIC_ERROR;
			msg[1] = "Incorrect password";
			
			Utils.messageMaker(Constants.GENERIC_ERROR, Constants.SUCCESS_FALSE, msg);
			
			return null;
			
		}
				
		msg[0] = Constants.GENERIC_SUCCESS;
		msg[1] = "You are logged in!";
		
		Utils.messageMaker(Constants.GENERIC_SUCCESS, Constants.SUCCESS_TRUE, msg);
		
		return "books/new_book.xhtml";
		
		
	}
	
	/**
	 *Validate the password
	 *
	 * @param fromDatabase
	 * @param fromView
	 * @return
	 */
	private boolean validatePassword(String fromDatabase, String fromView){
		boolean what= false;
		//Validate password
		try {
			what = Utils.validatePassword(fromView, fromDatabase);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return what;
	}

	
}
